package testClass;

import dnl.utils.text.table.TextTable;
import testClass.annotations.After;
import testClass.annotations.Before;
import testClass.annotations.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class TestClass {
    private final static byte NUMBER_OF_METHODS_BEFORE = 1;
    private final static byte NUMBER_OF_METHODS_AFTER = 1;
    private static Class clazz; //тестируемый класс
    private static int passTest = 0; //счетчик удачно прошедших тестов
    private static int failTest = 0; //счетчик не пройденных тестов
    private int numberTest = 0; //общий счетчик тестов
    private static String methodRun;

    public void runTest(String nameClass)
            throws IllegalArgumentException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        clazz = Class.forName(nameClass);
        methodTestingIsPerformed(foundMethod(clazz));
        printResult();
    }

    private Map<String, List<Method>> foundMethod(Class clazz) //находим все методы с аннотациями Before, After, Test
            throws NoSuchMethodException {
        Method[] allMethods = clazz.getDeclaredMethods();
        Map<String, List<Method>> foundMethod = new HashMap<>();
        List<Method> beforeMethods = new ArrayList<>();
        List<Method> testMethods = new ArrayList<>();
        List<Method> afterMethods = new ArrayList<>();
        for (Method method : allMethods) {
            if (clazz.getMethod(method.getName()).isAnnotationPresent(Before.class)) {
                beforeMethods.add(method);
                if (beforeMethods.size() > NUMBER_OF_METHODS_BEFORE) {
                    throw new RuntimeException("Аннотация Before может быть только у одного метода!");
                }
            }
        }
        foundMethod.put("BeforeAnnotation", beforeMethods);
        for (Method method : allMethods) {
            if (clazz.getMethod(method.getName()).isAnnotationPresent(After.class)) {
                afterMethods.add(method);
                if (afterMethods.size() > NUMBER_OF_METHODS_AFTER) {
                    throw new RuntimeException("Аннотация After может быть только у одного метода!");
                }
            }
        }
        foundMethod.put("AfterAnnotation", afterMethods);
        for (Method method : allMethods) {
            if (clazz.getMethod(method.getName()).isAnnotationPresent(Test.class)) {
                testMethods.add(method);
            }
        }
        foundMethod.put("TestAnnotation", testMethods);
        return foundMethod;
    }

    private void methodTestingIsPerformed(Map<String, List<Method>> allMethodsAnnotation) // основная логика теста
            throws IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor constructor = clazz.getConstructor();
        numberTest = allMethodsAnnotation.get("TestAnnotation").size(); // количество методов с аннотацией test
        List<Method> beforeMethods = allMethodsAnnotation.get("BeforeAnnotation"); //вытаскиваем методы с аннотацией Before
        List<Method> testMethods = allMethodsAnnotation.get("TestAnnotation"); //вытаскиваем методы с аннотацией Test
        List<Method> afterMethods = allMethodsAnnotation.get("AfterAnnotation"); //вытаскиваем методы с аннотацией Before
        Object object[] = new Object[numberTest];
        for (int i = 0; i < numberTest; i++) {
            object[i] = constructor.newInstance(); //создаем экземляры тестируемого класса для каждой группы методов с аннотациями Before, Test, After
            for (int j = 0; j < NUMBER_OF_METHODS_BEFORE; j++) {
                beforeMethods.get(j).setAccessible(true); //методы с аннотацией Before
                beforeMethods.get(j).invoke(object[i]);
            }
            methodRun = testMethods.get(i).getName();
            testMethods.get(i).setAccessible(true); //методы с аннотацией Test
            testMethods.get(i).invoke(object[i]);
            for (int j = 0; j < NUMBER_OF_METHODS_AFTER; j++) {
                afterMethods.get(j).setAccessible(true); //методы с аннотацией After
                afterMethods.get(j).invoke(object[i]);
            }
            System.out.println("-------------------------------------------");
        }
    }

    public static void assertEquals(Object expected, Object actual) {
        if (expected.toString().equals(actual.toString())) { //сравниваем по toString
            System.out.println("Testing of the method \"" + clazz.getName() + " > " + methodRun + "\" was SUCCESSFUL."); //выводим метод, прошедший тест
            passTest++; //инкрементируем счетчик удавшихся тестов
        } else {
            System.out.println("Testing the method \"" + clazz.getName() + " > " + methodRun + "\" FAILED."); //выводим метод, непрошедший тест
            failTest++; //инкрементируем счетчик неудавшихся тестов
        }
    }

    private void printResult() {
        String[] columnNames = {"Total tests Passed", "Total Successfully Completed tests", "Total Failed tests"};
        String[][] dataTable = {{String.valueOf(numberTest), String.valueOf(passTest), String.valueOf(failTest)}, {}};
        TextTable tt = new TextTable(columnNames, dataTable); // хэлпер для вывода информации в виде таблицы
        tt.printTable();
    }

}