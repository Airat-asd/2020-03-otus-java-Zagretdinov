import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AcceptCashTest {
    public static final int BALANCE = 8800;
    public static final List<Integer> BUNDLEOFBANKNOTES1 = Arrays.asList(Banknotes.FIFTY_50.getBanknote(),
            Banknotes.HUNDRED_100.getBanknote(), Banknotes.FIVEHUNDRED_500.getBanknote(), Banknotes.THOUSAND_1000.getBanknote(),
            Banknotes.TWOTHOUSAND_2000.getBanknote(), Banknotes.FIVETHOUSAND_5000.getBanknote(), Banknotes.FIFTY_50.getBanknote(),
            Banknotes.HUNDRED_100.getBanknote());
    public static final List<Integer> BUNDLEOFBANKNOTES2 = Arrays.asList(Banknotes.FIFTY_50.getBanknote(),
            Banknotes.THOUSAND_1000.getBanknote(), Banknotes.TWOTHOUSAND_2000.getBanknote(), Banknotes.THOUSAND_1000.getBanknote(),
            Banknotes.TWOTHOUSAND_2000.getBanknote());
    Map<Integer, Integer> BANKNOTESCONTAINER1 = Map.of(
            2000, 1,
            50, 2,
            100, 2,
            500, 1,
            1000, 1,
            5000, 1
    );
    Map<Integer, Integer> BANKNOTESCONTAINER2 = Map.of(
            2000, 3,
            50, 3,
            100, 2,
            500, 1,
            1000, 3,
            5000, 1
    );

    Map<Integer, Integer> emptyContainer = new HashMap<>();
    BundleOfBanknotes bundle;
    AcceptCash acceptCash;
    ATM atm;

    @BeforeEach
    void setUp() {
//        acceptCash = new AcceptCash();
        atm = mock(ATM.class);
        bundle = mock(BundleOfBanknotes.class);
    }

    @Test
    void acceptCash1() {
        System.out.println("Test1");
        when(atm.getBalance()).thenReturn(0);
        when(atm.getBanknotesContainer()).thenReturn(emptyContainer);
        when(bundle.getBundleOfBanknotes()).thenReturn(BUNDLEOFBANKNOTES1);
        Map<Integer, Integer> actualBanknotesContainer = AcceptCash.acceptCash(atm, bundle);
        assertEquals(BANKNOTESCONTAINER1, actualBanknotesContainer);
    }

    @Test
    void acceptCash2() {
        System.out.println("Test2");
        when(atm.getBalance()).thenReturn(BALANCE);
        when(atm.getBanknotesContainer()).thenReturn(BANKNOTESCONTAINER1);
        when(bundle.getBundleOfBanknotes()).thenReturn(BUNDLEOFBANKNOTES2);
        Map<Integer, Integer> actualBanknotesContainer = AcceptCash.acceptCash(atm, bundle);
        assertEquals(BANKNOTESCONTAINER2, actualBanknotesContainer);
    }
}