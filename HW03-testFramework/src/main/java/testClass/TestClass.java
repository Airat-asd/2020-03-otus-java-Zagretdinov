package testClass;

import dnl.utils.text.table.TextTable;
import testClass.annotations.After;
import testClass.annotations.Before;
import testClass.annotations.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class TestClass {
    private Class clazz; //тестируемый класс
    private List<Method> method = new ArrayList<>(); //массив тестируемых методов
    private static int passTest = 0; //счетчик удачно прошедших тестов
    private static int failTest = 0; //счетчик не пройденных тестов
    private int test = 0; //общий счетчик тестов
    private byte countBefore = 0; //счетчик аннотаций Before
    private byte countAfter = 0; //счетчик аннотаций After


    public void incrementPassTest() { //инкрементируем счетчик удачных тестов
        passTest++;
    } // инкрементация счетчик удачно прошедших тестов

    public void incrementFailTest() { //инкрементируем счетчик неудачных тестов
        failTest++;
    } //инкрементация счетчика не пройденных тестов

    public void runTest(String nameClass)
            throws IllegalArgumentException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        String[] columnNames = {"Total tests Passed", "Total Successfully Completed tests", "Total Failed tests"};
        clazz = Class.forName(nameClass);
        methodTestingIsPerformed(foundMethod(clazz));
        String[][] dataTable = {{String.valueOf(test), String.valueOf(passTest), String.valueOf(failTest)}, {}};
        TextTable tt = new TextTable(columnNames, dataTable); // хэлпер для вывода информации в виде таблицы
        tt.printTable();
    }

    private class FoundMethodResult {

    }

    private List<Method> foundMethod(Class clazz) //находим все методы с аннотациями Before, After, Test
            throws NoSuchMethodException {
        Method[] methods = clazz.getDeclaredMethods();
        for (Method allMethods : methods) {
            if (clazz.getMethod(allMethods.getName()).isAnnotationPresent(Before.class)) {
                methodBefore.add(allMethods);
                countBefore++;
                if (methodBefore.size() > 1) {
                    throw new RuntimeException("Аннотация Before может быть только у одного метода!");
                }
            }
        }
        for (Method alldMethods : methods) {
            if (clazz.getMethod(alldMethods.getName()).isAnnotationPresent(After.class)) {
                method.add(alldMethods);
                countAfter++;
                if (countAfter > 1) {
                    throw new RuntimeException("Аннотация After может быть только у одного метода!");
                }

            }
        }
        for (Method allFoundMethods : methods) {
            if (clazz.getMethod(allFoundMethods.getName()).isAnnotationPresent(Test.class)) {
                method.add(allFoundMethods);
            }
        }
        return method;
    }

    private void methodTestingIsPerformed(List<Method> method) // основная логика теста
            throws IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor constructor = clazz.getConstructor();
        test = method.size() - 2;
        Object object[] = new Object[test];
        for (int i = 0; i < test; i++) {
            object[i] = constructor.newInstance(); //создаем экземляры тестируемого класса для каждой тройки методов  с аннотациями Before, Test, After
            method.get(0).setAccessible(true); //метод с аннотацией Before
            method.get(1).setAccessible(true); //метод с аннотацией After
            method.get(i + 2).setAccessible(true); //метод с аннотацией Test
            method.get(0).invoke(object[i]);
            method.get(i + 2).invoke(object[i]);
            method.get(1).invoke(object[i]);
            System.out.println("-------------------------------------------");
        }
    }
}
