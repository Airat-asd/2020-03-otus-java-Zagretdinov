import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

//Выдаём запрошенную сумму минимальным количеством банкнот или выдаем ошибку если сумму нельзя выдать
public class GiveCash {
    //    public static Map<Integer, Integer> giveCash(ATM atm, int amount) {
    public static Map<Banknotes, Integer> giveCash(Map<Banknotes, Integer> banknotesContainer, int balance, int amount) {
        if (amount <= 0) {
            try {
                throw new ExeptionInadmissibleAmount("Inadmissible amount");
            } catch (ExeptionInadmissibleAmount exeptionInadmissibleAmount) {
                exeptionInadmissibleAmount.printStackTrace();
            } finally {
                return null;
            }
        } else {
            Map<Banknotes, Integer> giveBanknotesContainer = new TreeMap<>((o1, o2) -> o2.getBanknote() - o1.getBanknote());
            Map<Banknotes, Integer> banknotesContainerBufer = new TreeMap<>((o1, o2) -> o2.getBanknote() - o1.getBanknote());
            banknotesContainerBufer.putAll(banknotesContainer);
            if (amount <= balance) {
                for (Map.Entry<Banknotes, Integer> banknote : banknotesContainerBufer.entrySet()) {
                    for (int i = 0; i < banknote.getValue(); i++) {
                        if (amount >= banknote.getKey().getBanknote()) {
                            amount = amount - banknote.getKey().getBanknote();
                            giveBanknotesContainer.put(banknote.getKey(), i + 1);
                        } else {
                            break;
                        }
                    }
                    if (amount == 0) {
                        break;
                    }
                }
            } else {
                try {
                    throw new ExeptionGiveCash("ATM cannot issue requested amount of money");
                } catch (ExeptionGiveCash exeptionGiveCash) {
                    exeptionGiveCash.printStackTrace();
                } finally {
                    return null;
                }
            }

            if (amount == 0) {
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
