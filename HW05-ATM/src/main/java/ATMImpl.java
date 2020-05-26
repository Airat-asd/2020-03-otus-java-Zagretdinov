import java.util.Map;
import java.util.TreeMap;

/**
 * @author Ayrat Zagretdinov
 * created on 16.05.2020
 */
public class ATMImpl implements ATM {
    //Контейнер банкомата с купюрами отсортированными по ящейкам
    private Map<Integer, Integer> banknotesContainer = new TreeMap<>((o1, o2) -> o2 - o1);
    // Остаток денежных средств (баланс)
    private int balance = 0;

    public ATMImpl(Map<Integer, Integer> banknotesContainer, int balance) {
        this.banknotesContainer.putAll(banknotesContainer);
        this.balance = balance;
    }

    public ATMImpl() {
    }

    @Override
    public int getBalance() {
        return balance;
    }

    @Override
    public void setBalance(int balance) {
        this.balance = balance;
    }

    @Override
    public Map<Integer, Integer> getBanknotesContainer() {
        return banknotesContainer;
    }

    @Override
    public void setBanknotesContainer(Map<Integer, Integer> banknotesContainer) {
        this.banknotesContainer.clear();
        this.banknotesContainer.putAll(banknotesContainer);
    }
}