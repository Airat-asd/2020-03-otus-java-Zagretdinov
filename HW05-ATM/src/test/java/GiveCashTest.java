import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GiveCashTest {
    public static final int AMOUNT1 = 8800;
    public static final int AMOUNT2 = 8650;
    public static final int AMOUNT3 = -8650;

    public static final int BALANCE1 = 8800;

    Map<Integer, Integer> BANKNOTESCONTAINER1 = Map.of(
            2000, 1,
            50, 2,
            100, 2,
            500, 1,
            1000, 1,
            5000, 1
    );
    Map<Integer, Integer> BANKNOTESCONTAINER2 = Map.of(
            2000, 1,
            50, 1,
            100, 1,
            500, 1,
            1000, 1,
            5000, 1
    );
    ATM atm;

    @BeforeEach
    void setUp() {
        atm = mock(ATM.class);
    }

//    @Test
//    void giveCash1() {
//        when(atm.getBalance()).thenReturn(BALANCE1);
//        when(atm.getBanknotesContainer()).thenReturn(BANKNOTESCONTAINER1);
//        Map<Integer, Integer> actualgiveCash = GiveCash.giveCash(atm, AMOUNT1);
//        assertEquals(BANKNOTESCONTAINER1, actualgiveCash);
//    }
//
//    @Test
//    void giveCash2() {
//        when(atm.getBalance()).thenReturn(BALANCE1);
//        when(atm.getBanknotesContainer()).thenReturn(BANKNOTESCONTAINER1);
//        Map<Integer, Integer> actualgiveCash = GiveCash.giveCash(atm, AMOUNT2);
//        assertEquals(BANKNOTESCONTAINER2, actualgiveCash);
//    }
//
//    @Test(expected = ExeptionInadmissibleAmount.class)
//    void giveCash3() {
//        when(atm.getBalance()).thenReturn(BALANCE1);
//        when(atm.getBanknotesContainer()).thenReturn(BANKNOTESCONTAINER1);
//        assertThrows(ExeptionInadmissibleAmount.class, () -> GiveCash.giveCash(atm, AMOUNT3));
//    }

}