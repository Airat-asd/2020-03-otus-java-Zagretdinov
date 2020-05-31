import java.util.Map;

/**
 * @author Ayrat Zagretdinov
 * created on 16.05.2020
 */
public interface ATM {

    void acceptCash(Map<Banknotes, Integer> bundle);

    Map<Banknotes, Integer> giveCash(int amount);

    int getBalance();
}
