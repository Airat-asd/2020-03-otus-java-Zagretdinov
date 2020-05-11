import annotations.Log;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

class Ioc {

    private Ioc() {
    }

    static KinopoiskInterface createMyClass() throws NoSuchMethodException {
//        var hasAnnotation = createMyClass().getClass().getMethod("runMovie").isAnnotationPresent(Log.class);
//        System.out.println("hasAnnotation:" + hasAnnotation);
        InvocationHandler handler = new DemoInvocationHandler(new KinopoiskImpl());
        System.out.println("test 2");

        return (KinopoiskInterface) Proxy.newProxyInstance(Ioc.class.getClassLoader(), new Class<?>[]{KinopoiskInterface.class}, handler);
    }

    static class DemoInvocationHandler implements InvocationHandler {
        private final KinopoiskInterface myClass;

        DemoInvocationHandler(KinopoiskInterface myClass) {
            System.out.println("test 1");
            this.myClass = myClass;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println("executed method:" + method.getName() + ", param:" + Arrays.toString(args));
//            return method.invoke(myClass, args);
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
