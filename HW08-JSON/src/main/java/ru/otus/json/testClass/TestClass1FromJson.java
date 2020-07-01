package ru.otus.json.testClass;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class TestClass1FromJson<T> {
    private final int value1;
    private final String value2;
    private final List<T> value3;
    private final String[] value4;
    private StringBuilder value5 = null;

    public TestClass1FromJson(int value1, String value2, List<T> value3, String[] value4, StringBuilder value5) {
        this.value1 = value1;
        this.value2 = value2;
        this.value3 = value3;
        this.value4 = value4;
        this.value5 = value5;
    }

    @Override
    public String toString() {
        return "TestClass1FromJson{" +
                "value1=" + value1 +
                ", value2='" + value2 + '\'' +
                ", value3=" + value3 +
                ", value4=" + Arrays.toString(value4) +
                ", value5='" + value5.toString() + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TestClass1FromJson that = (TestClass1FromJson) o;
        return value1 == that.value1 &&
                Objects.equals(value2, that.value2) &&
                Objects.equals(value3.toString(), that.value3.toString()) &&
                Objects.equals(Arrays.toString(value4), Arrays.toString(that.value4)) &&
                Objects.equals(value5.toString(), that.value5.toString());
    }

    @Override
    public int hashCode() {
        return Objects.hash(value1, value2, value3, value4, value5);
    }
}