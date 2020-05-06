package testee;

public class TesteeClass { // тестируемый класс
    private int x;

    public TesteeClass(int x) {
        this.x = x;
    }

    public double function1() {
        double y = Math.sin(2 * x - Math.PI / 3);
        return y;
    }

    public double function2() {
        double y = Math.cos(2 * x - Math.PI * 12);
        return y;
    }

    public double function3() {
        double y = Math.sin(2 * x - Math.exp(x) / 0); // в этом методе создадим ошибочный код
        return y;
    }
}
