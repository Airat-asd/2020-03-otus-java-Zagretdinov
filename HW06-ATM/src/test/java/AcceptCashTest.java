import Banknotes.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AcceptCashTest {
    Banknotes FIFTY_50 = new Banknote50(10);
    List<Banknotes> BANKNOTESCONTAINER1 = new ArrayList<>();
    List<Banknotes> BANKNOTESCONTAINER2 = new ArrayList<>(Arrays.asList(FIFTY_50));
    List<Banknotes> BUNDLEOFBANKNOTES1 = new ArrayList<>(Arrays.asList(FIFTY_50));
//    Map<Banknotes, Integer> BUNDLEOFBANKNOTES3 = new TreeMap<>();
//    Map<Banknotes, Integer> BANKNOTESCONTAINER1 = new TreeMap<>();
//    Map<Banknotes, Integer> BANKNOTESCONTAINER2 = new TreeMap<>();
//    Map<Banknotes, Integer> BANKNOTESCONTAINER3 = new TreeMap<>();

    @BeforeEach
    void setUp() {
//        BANKNOTESCONTAINER2.put(BanknotesImpl.FIFTY_50, 5);
//        BANKNOTESCONTAINER2.put(BanknotesImpl.HUNDRED_100, 5);
//        BANKNOTESCONTAINER2.put(BanknotesImpl.FIVEHUNDRED_500, 5);
//        BANKNOTESCONTAINER2.put(BanknotesImpl.THOUSAND_1000, 5);
//        BANKNOTESCONTAINER2.put(BanknotesImpl.TWOTHOUSAND_2000, 5);
//        BANKNOTESCONTAINER2.put(BanknotesImpl.FIVETHOUSAND_5000, 5);
//        BANKNOTESCONTAINER3.put(BanknotesImpl.FIFTY_50, 10);
//        BANKNOTESCONTAINER3.put(BanknotesImpl.HUNDRED_100, 10);
//        BANKNOTESCONTAINER3.put(BanknotesImpl.FIVEHUNDRED_500, 5);
//        BANKNOTESCONTAINER3.put(BanknotesImpl.THOUSAND_1000, 5);
//        BANKNOTESCONTAINER3.put(BanknotesImpl.TWOTHOUSAND_2000, 5);
//        BANKNOTESCONTAINER3.put(BanknotesImpl.FIVETHOUSAND_5000, 5);
    }

    @Test
    void acceptCash1() {
        System.out.println("Test1");
        var actualBanknotesContainer = AcceptCash.acceptCash(BUNDLEOFBANKNOTES1, BANKNOTESCONTAINER1);
        System.out.println();
        assertEquals(BANKNOTESCONTAINER2, actualBanknotesContainer);
    }

//    @Test
//    void acceptCash2() {
//        System.out.println("Test2");
//        var actualBanknotesContainer = AcceptCash.acceptCash(BUNDLEOFBANKNOTES2, BANKNOTESCONTAINER2);
//        assertEquals(BANKNOTESCONTAINER3, actualBanknotesContainer);
//    }
//
//    @Test
//    void acceptCash3() {
//        System.out.println("Test3");
//        var actualBanknotesContainer = AcceptCash.acceptCash(BUNDLEOFBANKNOTES3, BANKNOTESCONTAINER2);
//        assertEquals(BANKNOTESCONTAINER2, actualBanknotesContainer);
//    }
}