import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

//Выдаём запрошенную сумму минимальным количеством банкнот или выдаем ошибку если сумму нельзя выдать
public class GiveCash {
    public static Map<Integer, Integer> giveCash(ATM atm, int theRequestedBanknotes) {
        if (theRequestedBanknotes > 0) {
            Map<Integer, Integer> giveCash = new TreeMap<>((o1, o2) -> o2 - o1);
            Map<Integer, Integer> containerBufer = new TreeMap<>((o1, o2) -> o2 - o1);
            Map<Integer, Integer> banknotesContainer = new TreeMap<>((o1, o2) -> o2 - o1);
            int balance = atm.getBalance();
            banknotesContainer.putAll(atm.getBanknotesContainer());
            containerBufer.putAll(atm.getBanknotesContainer());
            if (theRequestedBanknotes <= balance) {
                for (Map.Entry banknote : banknotesContainer.entrySet()) {
                    int balanceOfBanknotes = (int) banknote.getValue();
                    for (int amountBanknotes = 0; amountBanknotes < (int) banknote.getValue(); amountBanknotes++, balanceOfBanknotes--) {
                        if (theRequestedBanknotes < (int) banknote.getKey()) {
                            break;
                        } else {
                            theRequestedBanknotes = theRequestedBanknotes - (int) banknote.getKey();
                            giveCash.put((Integer) banknote.getKey(), amountBanknotes + 1);
                            balance = balance - (int) banknote.getKey();
                            if ((balanceOfBanknotes - 1) == 0) {
                                containerBufer.remove(banknote.getKey());
                            } else {
                                containerBufer.put((Integer) banknote.getKey(), balanceOfBanknotes - 1);
                            }
                        }
                    }
                    if (theRequestedBanknotes == 0) {
                        break;
                    }
                }
            } else {
                return null;
            }

            if (theRequestedBanknotes == 0) {
                atm.setBanknotesContainer(containerBufer);
                atm.setBalance(balance);
                return giveCash;
            } else {
                try {
                    throw new ExeptionGiveCash("ATM cannot issue requested theRequestedBanknotes of money");
                } catch (ExeptionGiveCash exeptionGiveCash) {
                    exeptionGiveCash.printStackTrace();
                } finally {
                    return null;
                }
            }
        } else {
            try {
                throw new ExeptionInadmissibleAmount("Inadmissible theRequestedBanknotes");
            } catch (ExeptionInadmissibleAmount exeptionInadmissibleAmount) {
                exeptionInadmissibleAmount.printStackTrace();
            } finally {
                return null;
            }
        }
    }
}
