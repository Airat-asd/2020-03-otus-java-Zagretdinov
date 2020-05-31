import java.util.Map;
import java.util.TreeMap;

/**
 * @author Ayrat Zagretdinov
 * created on 16.05.2020
 */
public class ATMImpl implements ATM {
    //Контейнер банкомата с купюрами отсортированными по ящейкам
    private Map<Banknotes, Integer> banknotesContainer = new TreeMap<>();
    // Остаток денежных средств (баланс)
    private int balance;

    public ATMImpl(Map<Banknotes, Integer> banknotesContainer) {
        setBanknotesContainer(banknotesContainer);
    }

    public ATMImpl() {
        for (Banknotes banknote : BanknotesImpl.values()) {
            banknotesContainer.put(banknote, 0);
        }
        this.balance = 0;
    }

    //Прием банкнот
    @Override
    public void acceptCash(Map<Banknotes, Integer> bundle) {
        if (!bundle.isEmpty()) {
            setBanknotesContainer(AcceptCash.acceptCash(bundle, banknotesContainer));
        }
    }

    //выдача банкнот
    @Override
    public Map<Banknotes, Integer> giveCash(int amount) {
        Map<Banknotes, Integer> giveCash = GiveCash.giveCash(banknotesContainer, balance, amount);
        if (!(giveCash == null)) {
            setBanknotesContainer(TakeCashFromTheContainer.takeCashFromTheContainer(banknotesContainer, giveCash));
        }
        return giveCash;
    }

    @Override
    public int getBalance() {
        return balance;
    }

    //Устанавливаем состояние контейнера с банкнотами
    private void setBanknotesContainer(Map<Banknotes, Integer> newBanknotesContainer) {
        if (!newBanknotesContainer.isEmpty()) {
            balance = 0;
            banknotesContainer.putAll(newBanknotesContainer);
            banknotesContainer.forEach((key, value) -> balance = balance + value * key.getBanknote());
        }
    }
}