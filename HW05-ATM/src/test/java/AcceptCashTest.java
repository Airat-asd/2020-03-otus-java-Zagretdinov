import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AcceptCashTest {
    Map<Banknotes, Integer> BUNDLEOFBANKNOTES1 = Map.of(
            Banknotes.FIFTY_50, 5,
            Banknotes.HUNDRED_100, 5,
            Banknotes.FIVEHUNDRED_500, 5,
            Banknotes.THOUSAND_1000, 5,
            Banknotes.TWOTHOUSAND_2000, 5,
            Banknotes.FIVETHOUSAND_5000, 5
    );
    Map<Banknotes, Integer> BUNDLEOFBANKNOTES2 = Map.of(
            Banknotes.FIFTY_50, 5,
            Banknotes.HUNDRED_100, 5
    );
    Map<Banknotes, Integer> BUNDLEOFBANKNOTES3 = new TreeMap<>();
    Map<Banknotes, Integer> BANKNOTESCONTAINER1 = new TreeMap<>();
    Map<Banknotes, Integer> BANKNOTESCONTAINER2 = new TreeMap<>();
    Map<Banknotes, Integer> BANKNOTESCONTAINER3 = new TreeMap<>();

    @BeforeEach
    void setUp() {
        BANKNOTESCONTAINER2.put(Banknotes.FIFTY_50, 5);
        BANKNOTESCONTAINER2.put(Banknotes.HUNDRED_100, 5);
        BANKNOTESCONTAINER2.put(Banknotes.FIVEHUNDRED_500, 5);
        BANKNOTESCONTAINER2.put(Banknotes.THOUSAND_1000, 5);
        BANKNOTESCONTAINER2.put(Banknotes.TWOTHOUSAND_2000, 5);
        BANKNOTESCONTAINER2.put(Banknotes.FIVETHOUSAND_5000, 5);
        BANKNOTESCONTAINER3.put(Banknotes.FIFTY_50, 10);
        BANKNOTESCONTAINER3.put(Banknotes.HUNDRED_100, 10);
        BANKNOTESCONTAINER3.put(Banknotes.FIVEHUNDRED_500, 5);
        BANKNOTESCONTAINER3.put(Banknotes.THOUSAND_1000, 5);
        BANKNOTESCONTAINER3.put(Banknotes.TWOTHOUSAND_2000, 5);
        BANKNOTESCONTAINER3.put(Banknotes.FIVETHOUSAND_5000, 5);
    }

    @Test
    void acceptCash1() {
        System.out.println("Test1");
        var actualBanknotesContainer = AcceptCash.acceptCash(BUNDLEOFBANKNOTES1, BANKNOTESCONTAINER1);
        assertEquals(BANKNOTESCONTAINER2, actualBanknotesContainer);
    }

    @Test
    void acceptCash2() {
        System.out.println("Test2");
        var actualBanknotesContainer = AcceptCash.acceptCash(BUNDLEOFBANKNOTES2, BANKNOTESCONTAINER2);
        assertEquals(BANKNOTESCONTAINER3, actualBanknotesContainer);
    }

    @Test
    void acceptCash3() {
        System.out.println("Test3");
        var actualBanknotesContainer = AcceptCash.acceptCash(BUNDLEOFBANKNOTES3, BANKNOTESCONTAINER2);
        assertEquals(BANKNOTESCONTAINER2, actualBanknotesContainer);
    }
}