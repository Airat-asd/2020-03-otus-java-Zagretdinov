package testClass;

import testClass.annotations.After;
import testClass.annotations.Before;
import testClass.annotations.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class TestClass {
    private Class clazz;

    public void runTest (String nameClass)
            throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        clazz = Class.forName(nameClass);
        foundMethod(clazz);
    }

    private void foundMethod (Class clazz)
            throws NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        Method[] methods = clazz.getDeclaredMethods();
        for (Method i : methods) {
            if (clazz.getMethod(i.getName()).isAnnotationPresent(Before.class)) {
                System.out.println("1");
                Class[] parameterType = null;
                System.out.println("2");
                var methodBefore = clazz.getDeclaredMethod(i.getName(), parameterType);
                System.out.println("3");
                methodBefore.setAccessible(true);
                System.out.println("4");
                Constructor constructor = clazz.getConstructor();
                var object = constructor.newInstance();
                var result = methodBefore.invoke(object);
                System.out.println("5");

            }
        }
        for (Method i : methods) {
            if (clazz.getMethod(i.getName()).isAnnotationPresent(Test.class)) {
                System.out.println("2");
            }
        }
        for (Method i : methods) {
            if (clazz.getMethod(i.getName()).isAnnotationPresent(After.class)) {
                System.out.println("3");
            }
        }
    }
    private void initMethod (Method method)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        method.invoke(new MessageBuilderImplTest());
//        Object object = constructor.newInstance();
//        object.setUp();
    }
}
