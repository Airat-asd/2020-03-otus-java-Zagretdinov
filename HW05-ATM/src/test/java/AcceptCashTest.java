import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AcceptCashTest {
    public static final int BALANCE = 8800;
    public static final List<Integer> BUNDLEOFBANKNOTES = Arrays.asList(50, 100, 500, 1000, 2000, 5000, 50, 100);
    Map<Integer, Integer> BANKNOTESCONTAINER = Map.of(
            2000, 1,
            50, 2,
            100, 2,
            500, 1,
            1000, 1,
            5000, 1
    );

    Map<Integer, Integer> emptyContainer = new HashMap<>();
     BundleOfBanknotes bundle;
    AcceptCash acceptCash;
    ATM atm;

    @BeforeEach
    void setUp() {
        acceptCash = new AcceptCash();
        atm = mock(ATM.class);
        bundle = mock(BundleOfBanknotes.class);
    }

    @Test
    void acceptCash1() { //ATM atm, BundleOfBanknotes bundle
        when(atm.getBalance()).thenReturn(BALANCE);
        when(atm.getBanknotesContainer()).thenReturn(emptyContainer);
        when(bundle.getBundleOfBanknotes()).thenReturn(BUNDLEOFBANKNOTES);
        Map<Integer, Integer> actualBanknotesContainer = acceptCash.acceptCash(atm, bundle);
        assertEquals(BANKNOTESCONTAINER, actualBanknotesContainer);
    }

}