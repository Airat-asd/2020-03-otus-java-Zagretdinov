package AOP.proxy;

import AOP.proxy.annotations.Log;
import AOP.mainPackage.KinopoiskImpl;
import AOP.mainPackage.KinopoiskInterface;
import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Ioc {

    private Ioc() {
    }

    public static <T> T createMyClass(T object, Class<T> implInterface) {
        var asd = object.getClass().getInterfaces();
        System.out.println(Arrays.toString(asd));
        T type = (T) object.getClass().getGenericSuperclass();
        Class parameter = (Class)type.;
        //        if (asd[0] instanceof T)
        InvocationHandler handler = new DemoInvocationHandler(object);
        return (T) Proxy.newProxyInstance(Ioc.class.getClassLoader(), new Class<?>[]{asd[0]}, handler);
    }

    static class DemoInvocationHandler implements InvocationHandler {
        private final Object myClass;
        private List<String> joinLogMethod = new ArrayList<>();

        DemoInvocationHandler(Object myClass) {
            this.myClass = myClass;
            Method[] allMethods = myClass.getClass().getDeclaredMethods();
            for (Method method : allMethods) {
                if (method.isAnnotationPresent(Log.class)) {
                    joinLogMethod.add(method.getName());
                }
            }
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws InvocationTargetException, IllegalAccessException {
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
