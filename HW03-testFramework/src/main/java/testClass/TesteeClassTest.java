package testClass;

import testClass.annotations.After;
import testClass.annotations.Before;
import testClass.annotations.Test;

import static testClass.TestClass.*;

import testee.TesteeClass;

public class TesteeClassTest {
    private TesteeClass testee;

    private static final int X = 5;

    @Before
    public void setUp() {
        System.out.println("Run method SetUp");
        testee = new TesteeClass(X);
    }

    @Test
    public void function1() {
        double actualFunction = testee.function1();
        assertEquals((Math.sin(2 * X - Math.PI / 3)), actualFunction); //статичный метод по аналогии с Assert
    }

    @Test
    public void function2() {
        double actualFunction = testee.function2();
        assertEquals((Math.cos(2 * X - Math.PI * 12)), actualFunction);
    }

    @Test
    public void function3() {
        double actualFunction = testee.function3();
        assertEquals((Math.sin(2 * X - Math.exp(X) / 13)), actualFunction);
    }

    @After
    public void setDown() {
        System.out.println("Run method setDown");
    }
}