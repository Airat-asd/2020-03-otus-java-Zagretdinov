import java.util.Map;
import java.util.TreeMap;

/**
 * @author Ayrat Zagretdinov
 * created on 16.05.2020
 */
public interface ATM {

    int getBalance();

    void setBalance(int balance);

    Map<Integer, Integer> getBanknotesContainer();

    void setBanknotesContainer(Map<Integer, Integer> banknotesContainer);
}
