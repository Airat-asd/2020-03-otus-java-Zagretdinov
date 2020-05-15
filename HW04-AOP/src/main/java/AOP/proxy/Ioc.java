package AOP.proxy;

import AOP.proxy.annotations.Log;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Ioc {

    private Ioc() {
    }

    public static <T> T createMyClass(T object) {
        //С помощью рефлексии узнаем интерфейс, который имплементирует класс получаемого
        //в параметрах метода экземпляра
        Class<?>[] actualInterface = (Class<?>[])(object.getClass()).getGenericInterfaces();
        InvocationHandler handler = new DemoInvocationHandler(object);
        return (T) Proxy.newProxyInstance(Ioc.class.getClassLoader(), actualInterface, handler);
    }

    static class DemoInvocationHandler implements InvocationHandler {
        private final Object myClass;
        private List<String> joinLogMethod = new ArrayList<>();

        DemoInvocationHandler(Object myClass) {
            this.myClass = myClass;
            //В контсрукторе находим методы с аннотацией @Log, таким образом поиск всех аннотаций @Log класса
            //с помощью метода isAnnotationPresent происходит единожды
            Method[] allMethods = myClass.getClass().getDeclaredMethods();
            for (Method method : allMethods) {
                if (method.isAnnotationPresent(Log.class)) {
                    joinLogMethod.add(method.getName());
                }
            }
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws InvocationTargetException, IllegalAccessException {
           //Если входящий метод присутствует в списке "логированных" методов, то мы можем вызвать метод логирования и
            //и в зависимости от логики программы вернуть null или method.invoke(myClass, args)
            if (joinLogMethod.contains(method.getName())) {
                System.out.println("executed method:" + method.getName() + ", param:" + Arrays.toString(args));
            }
            return method.invoke(myClass, args);
        }

        @Override
        public String toString() {
            return "DemoInvocationHandler{" +
                    "myClass=" + myClass +
                    '}';
        }
    }
}