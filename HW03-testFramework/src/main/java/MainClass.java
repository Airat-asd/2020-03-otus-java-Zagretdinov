import testClass.TestClass;

import java.lang.reflect.InvocationTargetException;

public class MainClass {
    public static void main(String[] args) {
        try {
            new TestClass().runTest("testClass.TesteeClassTest");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

