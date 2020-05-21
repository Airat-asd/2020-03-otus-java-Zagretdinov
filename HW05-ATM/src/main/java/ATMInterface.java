import java.util.Map;

/**
 * @author Ayrat Zagretdinov
 * created on 16.05.2020
 */
public interface ATMInterface {

    Map<Integer, Integer> acceptCash(BanknotesAcceptorInterface cash);

    Map<Integer, Integer> giveCash(int amount) throws ExeptionGiveCash;

    int cashBalance();
}
