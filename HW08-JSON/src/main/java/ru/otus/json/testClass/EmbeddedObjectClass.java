package ru.otus.json.testClass;

/**
 * @author Ayrat Zagretdinov
 * created on 28.06.2020
 */
public class EmbeddedObjectClass {
    private final Object embeddedValue1;


    public EmbeddedObjectClass(Object embeddedValue1) {
        this.embeddedValue1 = embeddedValue1;
    }

    @Override
    public String toString() {
        return "{" +
                "embeddedValue1=" + embeddedValue1.toString() +
                '}';
    }
}
