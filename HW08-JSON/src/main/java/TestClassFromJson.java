import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

public class TestClassFromJson {
    private final int value1;
    private final String value2;
    private final Collection<Integer> value3;
    private final int[] value4;
    private StringBuilder value5=null;

    TestClassFromJson(int value1, String value2, Collection value3, int[] value4, StringBuilder value5) {
        this.value1 = value1;
        this.value2 = value2;
        this.value3 = value3;
        this.value4 = value4;
        this.value5 = value5;
    }

    @Override
    public String toString() {
        return "TestClassFromJson{" +
                "value1=" + value1 +
                ", value2='" + value2 + '\'' +
                ", value4=" + value3 +
                ", value3=" + Arrays.toString(value4) +
                ", value2='" + value5.toString() + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TestClassFromJson that = (TestClassFromJson) o;
        return value1 == that.value1 &&
                Objects.equals(value2, that.value2) &&
                Objects.equals(value3.toString(), that.value3.toString()) &&
                Objects.equals(Arrays.toString(value4), Arrays.toString(that.value4)) &&
                Objects.equals(value5.toString(), that.value5.toString());
    }

    @Override
    public int hashCode() {
        return Objects.hash(value1, value2, value3, value4);
    }
}
