import testClass.TestClass;

import java.lang.reflect.InvocationTargetException;

public class MainClass {
    public static void main(String[] args) {
        try {
            new TestClass().runTest("testClass.TesteeClassTest");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }
}

