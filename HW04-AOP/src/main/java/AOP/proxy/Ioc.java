package AOP.proxy;

import AOP.proxy.annotations.Log;
import AOP.mainPackage.KinopoiskImpl;
import AOP.mainPackage.KinopoiskInterface;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

public class Ioc {

    private Ioc() {
    }

    public static KinopoiskInterface createMyClass() {
        InvocationHandler handler = new DemoInvocationHandler(new KinopoiskImpl());
        System.out.println("test 2");
        return (KinopoiskInterface) Proxy.newProxyInstance(Ioc.class.getClassLoader(), new Class<?>[]{KinopoiskInterface.class}, handler);
    }

    static class DemoInvocationHandler implements InvocationHandler {
        private final KinopoiskInterface myClass;

        DemoInvocationHandler(KinopoiskInterface myClass) {
            System.out.println("test 1");
            this.myClass = myClass;
            Method[] allMethods = myClass.getClass().getDeclaredMethods();
            for (Method method : allMethods) {
                if (method.isAnnotationPresent(Log.class)) {
                    beforeMethods.add(method);

        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws InvocationTargetException, IllegalAccessException {
            if (method.isAnnotationPresent(Log.class)) {
                System.out.println("executed method:" + method.getName() + ", param:" + Arrays.toString(args));
                return method.invoke(myClass, args);
            }
            return null;
        }

        @Override
        public String toString() {
            return "DemoInvocationHandler{" +
                    "myClass=" + myClass +
                    '}';
        }
    }
}
