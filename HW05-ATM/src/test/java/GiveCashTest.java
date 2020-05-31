import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;

class GiveCashTest {
    public static final int AMOUNT1 = 1150;
    public static final int AMOUNT2 = 50000;
    public static final int AMOUNT3 = -50000;

    public static final int BALANCE1 = 0;
    public static final int BALANCE2 = 43250;

    Map<Banknotes, Integer> BANKNOTESCONTAINER1 = new TreeMap<>();
    Map<Banknotes, Integer> BANKNOTESCONTAINER2 = new TreeMap<>();

    Map<Banknotes, Integer> GIVECASH1 = new TreeMap<>();

    @BeforeEach
    void setUp() {
        BANKNOTESCONTAINER2.put(Banknotes.FIFTY_50, 5);
        BANKNOTESCONTAINER2.put(Banknotes.HUNDRED_100, 5);
        BANKNOTESCONTAINER2.put(Banknotes.FIVEHUNDRED_500, 5);
        BANKNOTESCONTAINER2.put(Banknotes.THOUSAND_1000, 5);
        BANKNOTESCONTAINER2.put(Banknotes.TWOTHOUSAND_2000, 5);
        BANKNOTESCONTAINER2.put(Banknotes.FIVETHOUSAND_5000, 5);

        GIVECASH1.put(Banknotes.THOUSAND_1000,1);
        GIVECASH1.put(Banknotes.HUNDRED_100,1);
        GIVECASH1.put(Banknotes.FIFTY_50,1);

    }

    @Test
    void giveCash1() {
        var actualgiveCash = GiveCash.giveCash(BANKNOTESCONTAINER1, BALANCE1, AMOUNT1);
        assertNull(actualgiveCash);
    }

    @Test
    void giveCash2() {
        var actualgiveCash = GiveCash.giveCash(BANKNOTESCONTAINER2, BALANCE2, AMOUNT1);
        assertEquals(GIVECASH1, actualgiveCash);
    }

    @Test
    void giveCash3() {
        var actualgiveCash = GiveCash.giveCash(BANKNOTESCONTAINER2, BALANCE2, AMOUNT2);
        assertNull(actualgiveCash);
    }

    @Test
    void giveCash4() {
        var actualgiveCash = GiveCash.giveCash(BANKNOTESCONTAINER2, BALANCE2, AMOUNT3);
        assertNull(actualgiveCash);
    }
}