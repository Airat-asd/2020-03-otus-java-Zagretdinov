import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

//Выдаём запрошенную сумму минимальным количеством банкнот или выдаем ошибку если сумму нельзя выдать
public class GiveCash {
    public static Map<Integer, Integer> giveCash(ATM atm, int amount) {
        if (amount <= 0) {
            try {
                throw new ExeptionInadmissibleAmount("Inadmissible amount");
            } catch (ExeptionInadmissibleAmount exeptionInadmissibleAmount) {
                exeptionInadmissibleAmount.printStackTrace();
            } finally {
                return null;
            }
        } else {
            Map<Integer, Integer> giveBanknotesContainer = new TreeMap<>((o1, o2) -> o2 - o1);
            Map<Integer, Integer> giveBanknotesContainerBufer = new TreeMap<>((o1, o2) -> o2 - o1);
            Map<Integer, Integer> banknotesContainer = new TreeMap<>((o1, o2) -> o2 - o1);
            int balance = atm.getBalance();
            banknotesContainer.putAll(atm.getBanknotesContainer());
            giveBanknotesContainerBufer.putAll(atm.getBanknotesContainer());
            if (amount <= balance) {
                for (Map.Entry banknote : banknotesContainer.entrySet()) {
                    int j = (int) banknote.getValue();
                    for (int i = 0; i < (int) banknote.getValue(); i++, j--) {
                        if (amount < (int) banknote.getKey()) {
                            break;
                        } else {
                            amount = amount - (int) banknote.getKey();
                            giveBanknotesContainer.put((Integer) banknote.getKey(), i + 1);
                            balance = balance - (int) banknote.getKey();
                            if ((j - 1) == 0) {
                                giveBanknotesContainerBufer.remove(banknote.getKey());
                            } else {
                                giveBanknotesContainerBufer.put((Integer) banknote.getKey(), j - 1);
                            }
                        }
                    }
                    if (amount == 0) {
                        break;
                    }
                }
            } else {
                return null;
            }

            if (amount == 0) {
                atm.setBanknotesContainer(giveBanknotesContainerBufer);
                atm.setBalance(balance);
                return giveBanknotesContainer;
            } else {
                try {
                    throw new ExeptionGiveCash("ATM cannot issue requested amount of money");
                } catch (ExeptionGiveCash exeptionGiveCash) {
                    exeptionGiveCash.printStackTrace();
                } finally {
                    return null;
                }
            }
        }
    }
}
