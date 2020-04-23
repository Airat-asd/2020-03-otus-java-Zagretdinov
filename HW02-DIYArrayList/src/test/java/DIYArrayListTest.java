import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class DIYArrayListTest {
    DIYArrayList diyArrayList;
    StringBuilder buffer;

    @BeforeEach
    void setUp() {
       diyArrayList = new DIYArrayList(Arrays.asList("10", "5", "1"));
        int index =0;

    }

    @Test
    void testToString() {
        String actualMessage = diyArrayList.toString();
        assertEquals("[ 10, 5, 1 ]", actualMessage);
    }

    @Test
    void addAll() {
        assertThrows(UnsupportedOperationException.class, () -> diyArrayList.addAll(Arrays.asList("10", "5", "1")));
    }

}