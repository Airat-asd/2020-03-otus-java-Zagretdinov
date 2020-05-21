import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Ayrat Zagretdinov
 * created on 16.05.2020
 */
public class ATM implements ATMInterface {
    //Контейнер банкомата с купюрами отсортированными по ящейкам
    private Map<Integer, Integer> banknotesContainer = new TreeMap<>((o1, o2) -> o2 - o1);
    // остаток денежных средств (баланс)
    private int balance;

    public ATM(Map<Integer, Integer> banknotesContainer, int balance) {
        this.banknotesContainer.putAll(banknotesContainer);
        this.balance = balance;
    }

    public ATM() {
    }

    //принимаем банкноты разных номиналов (на каждый номинал своя ячейка)
    @Override
    public Map<Integer, Integer> acceptCash(BanknotesAcceptorInterface banknote) {
        Integer newQuantityBanknote = 0;
        if (!banknote.getBillAcceptor().isEmpty()) {
            for (Integer billAccept : banknote.getBillAcceptor()) {
                if (banknotesContainer.containsKey(billAccept)) {
                    newQuantityBanknote = (Integer) banknotesContainer.get(billAccept) + 1;
                    banknotesContainer.replace(billAccept, newQuantityBanknote);
                } else {
                    banknotesContainer.put(billAccept, 1);
                }
                balance = balance + billAccept;
            }
        }
        return banknotesContainer;
    }

    //выдаём запрошенную сумму минимальным количеством банкнот или выдаем ошибку если сумму нельзя выдать
    @Override
    public Map<Integer, Integer> giveCash(int amount) throws ExeptionGiveCash {
        Map<Integer, Integer> giveBanknotesContainer = new HashMap<>();
        if (amount <= balance) {
            for (Map.Entry banknote : banknotesContainer.entrySet()) {
                for (int i = 0; i < (int) banknote.getValue(); i++) {
                    if (amount < (int) banknote.getKey()) {
                        break;
                    } else {
                        amount = amount - (int) banknote.getKey();
                        giveBanknotesContainer.put((Integer) banknote.getKey(), i + 1);
                    }
                }
                if (amount == 0) {
                    break;
                }
            }
        }
        if (amount == 0) {
            return giveBanknotesContainer;
        } else {
            throw new ExeptionGiveCash("ATM cannot issue requested amount of money");
        }
    }

    //выдаём сумму остатка денежных средств
    @Override
    public int cashBalance() {
        return balance;
    }
}