package testClass;

public class AssertionsDIY {
    private static TestClass testClass = new TestClass();

    public static void assertEquals(Object expected, Object actual) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        if (expected.toString().equals(actual.toString())) { //сравниваем по toString
            System.out.println("Testing of the method \"" + stackTrace[2].getClassName() + " > " + stackTrace[2].getMethodName() + "\" was SUCCESSFUL."); //выводим метод, прошедший тест
            testClass.incrementPassTest(); //инкрементируем счетчик удавшихся тестов
        } else {
            System.out.println("Testing the method \"" + stackTrace[2].getClassName() + " > " + stackTrace[2].getMethodName() + "\" FAILED."); //выводим метод, непрошедший тест
            testClass.incrementFailTest(); //инкрементируем счетчик неудавшихся тестов
        }

    }

}
