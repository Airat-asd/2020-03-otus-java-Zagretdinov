import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Ayrat Zagretdinov
 * created on 19.05.2020
 */
class ATMTest {
    public static final Map<Integer, Integer> BANKNOTESCONTAINER1 = Map.of(
            2000, 1,
            50, 2,
            100, 2,
            500, 1,
            1000, 1,
            5000, 1
    );
    public static final Map<Integer, Integer> BANKNOTESCONTAINER2 = Map.of(
            2000, 2,
            50, 4,
            100, 4,
            500, 2,
            1000, 2,
            5000, 2
    );
    public static final Map<Integer, Integer> ACTUALGIVECASH1 = Map.of(
            5000, 1,
            1000, 1,
            100, 2
    );
    public static final Map<Integer, Integer> ACTUALGIVECASH2 = Map.of(
            5000, 1,
            100, 1,
            50, 1
    );
    public static final List<Integer> BANKNOTESACCEPTOR = Arrays.asList(50, 100, 500, 1000, 2000, 5000, 50, 100);
    public static final int AMOUNT1 = 8800;
    public static final int AMOUNT2 = 6200;
    public static final int AMOUNT3 = 5150;
    public static final int AMOUNT4 = 10000;
    public static final int AMOUNT5 = 7777;
    public static final int BALANCE = 8800;

    ATMInterface atm;
    BanknotesAcceptorInterface banknote;

    @BeforeEach
    void setUp() {
        atm = new ATM(BANKNOTESCONTAINER1, BALANCE);
    }

    @Test
    void acceptCash() {
        banknote = mock(BanknotesAcceptor1.class);
        when(banknote.getBillAcceptor()).thenReturn(BANKNOTESACCEPTOR);
        Map<Integer, Integer> acceptCash = atm.acceptCash(banknote);
        assertEquals(BANKNOTESCONTAINER2, acceptCash);
    }

    @Test
    void giveCash1() throws ExeptionGiveCash {
        Map<Integer, Integer> giveCash = atm.giveCash(AMOUNT1);
        assertEquals(giveCash, BANKNOTESCONTAINER1);
    }

    @Test
    void giveCash2() throws ExeptionGiveCash {
        Map<Integer, Integer> giveCash = atm.giveCash(AMOUNT2);
        assertEquals(giveCash, ACTUALGIVECASH1);
    }

    @Test
    void giveCash3() throws ExeptionGiveCash {
        Map<Integer, Integer> giveCash = atm.giveCash(AMOUNT3);
        assertEquals(giveCash, ACTUALGIVECASH2);
    }

    @Test
    void giveCash4() {
        assertThrows(ExeptionGiveCash.class, () -> atm.giveCash(AMOUNT4));
    }

    @Test
    void giveCash5() {
        assertThrows(ExeptionGiveCash.class, () -> atm.giveCash(AMOUNT5));
    }

    @Test
    void cashBalance() {
        int cashBalance = atm.cashBalance();
        assertEquals(cashBalance, BALANCE);

    }
}