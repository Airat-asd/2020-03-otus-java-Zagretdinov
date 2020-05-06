package testClass;

import dnl.utils.text.table.TextTable;
import testClass.annotations.After;
import testClass.annotations.Before;
import testClass.annotations.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class TestClass {
    private Class clazz; //тестируемый класс
    private ArrayList<Method> method = new ArrayList<>(); //массив тестируемых методов
    private static int passTest = 0; //счетчик удачно прошедших тестов
    private static int failTest = 0; //счетчик не пройденных тестов
    private int test; //общий счетчик тестов

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
        initMethod(foundMethod(clazz));
        String[][] dataTable = {{String.valueOf(test), String.valueOf(passTest), String.valueOf(failTest)}, {}};
        TextTable tt = new TextTable(columnNames, dataTable); // хэлпер для вывода информации в виде таблицы
        tt.printTable();
    }

    private ArrayList<Method> foundMethod(Class clazz) //находим все методы с аннотациями Before, After, Test
            throws NoSuchMethodException {
        Method[] methods = clazz.getDeclaredMethods();
        for (Method i : methods) {
            if (clazz.getMethod(i.getName()).isAnnotationPresent(Before.class)) {
                method.add(i);
                if (method.size() > 1) {
                    throw new RuntimeException("Аннотация Before может быть только у одного метода!");
                }
            }
        }
        for (Method i : methods) {
            if (clazz.getMethod(i.getName()).isAnnotationPresent(After.class)) {
                method.add(i);
                if (method.size() > 2) {
                    throw new RuntimeException("Аннотация After может быть только у одного метода!");
                }

            }
        }
        for (Method i : methods) {
            if (clazz.getMethod(i.getName()).isAnnotationPresent(Test.class)) {
                method.add(i);
            }
        }
        return method;
    }

    private void initMethod(ArrayList<Method> method) // основная логика теста
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
