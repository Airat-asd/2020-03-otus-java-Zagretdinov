package JSON.TestClass;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

public class TestClass2FromJson<T> {
    private final Object[] value1;
    private final Collection<Integer> value2;


    public TestClass2FromJson(Object[] value1, Collection<Integer> value2) {
        this.value1 = value1;
        this.value2 = value2;
    }

    @Override
    public String toString() {
        return "TestClass2FromJson{" +
                "value1=" + Arrays.toString(value1) +
                ", value2=" + value2 +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        boolean equals;
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        TestClass2FromJson that = (TestClass2FromJson) obj;
        equals = Objects.equals(Arrays.toString(value1), Arrays.toString(that.value1)) &&
                Objects.equals(value2.toString(), that.value2.toString());
        return equals;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value1, value2);
    }
}