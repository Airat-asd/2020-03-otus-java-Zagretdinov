import testClass.TestClass;

import java.lang.reflect.InvocationTargetException;

public class MainClass {
    public static void main(String[] args) {
        try {
            new TestClass().runTest("testClass.MessageBuilderImplTest");
        } catch (ClassNotFoundException e1) {
            System.out.println("Class \"" + e1.getMessage() + "\" not found");;
        }
        catch (NoSuchMethodException e2) {
            System.out.println("Method \"" + e2.getMessage() + "\" not found");
        }
        catch (IllegalAccessException e3) {
            System.out.println("Method \"" + e3.getMessage() + "\" not found");
        }
        catch (InvocationTargetException e4) {
            System.out.println("Method \"" + e4.getMessage() + "\" not found");
        }
        catch (InstantiationException e5) {
            System.out.println("Method \"" + e5.getMessage() + "\" not found");
        }


    }
}

