package ATM;

import Banknotes.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

//Выдаём запрошенную сумму минимальным количеством банкнот или выдаем ошибку если сумму нельзя выдать
public class GiveCash {
    public static List<Banknotes> giveCash(List<Banknotes> banknotesContainer, int balance, int amount) {
        if (amount <= 0) {
            try {
                throw new ExceptionInadmissibleAmount("Inadmissible amount");
            } catch (ExceptionInadmissibleAmount exceptionInadmissibleAmount) {
                exceptionInadmissibleAmount.printStackTrace();
            } finally {
                return null;
            }
        } else {
            Map<Integer, Integer> banknotesContainerBufer = new TreeMap<>((o1, o2) -> o2 - o1);//ключ - номинал банкноты, значние - количество банкнот
            Map<Integer, Integer> giveBanknotesBundleBufer = new TreeMap<>(); //ключ - номинал банкноты, значние - количество банкнот
            List<Banknotes> giveBanknotesBundle = new ArrayList<>();
            banknotesContainer.forEach(banknote -> banknotesContainerBufer.put(banknote.getNominalBanknote(), banknote.getAmountBanknote()));
            if (amount <= balance) {
                for (Map.Entry<Integer, Integer> banknote : banknotesContainerBufer.entrySet()) {
                    giveBanknotesBundleBufer.put(banknote.getKey(), 0);
                    for (int amountBanknote = 0; amountBanknote < banknote.getValue(); amountBanknote++) {
                        if (amount >= banknote.getKey()) {
                            amount = amount - banknote.getKey();
                            giveBanknotesBundleBufer.put(banknote.getKey(), giveBanknotesBundleBufer.get(banknote.getKey()) + 1);
                        } else {
                            break;
                        }
                    }
                    if (amount == 0) {
                        giveBanknotesBundleBufer.forEach((o1, o2) -> {
                            switch (o1) {
                                case 50:
                                    giveBanknotesBundle.add(new Banknote50(o2));
                                    break;
                                case 100:
                                    giveBanknotesBundle.add(new Banknote100(o2));
                                    break;
                                case 500:
                                    giveBanknotesBundle.add(new Banknote500(o2));
                                    break;
                                case 1000:
                                    giveBanknotesBundle.add(new Banknote1000(o2));
                                    break;
                                case 2000:
                                    giveBanknotesBundle.add(new Banknote2000(o2));
                                    break;
                                case 5000:
                                    giveBanknotesBundle.add(new Banknote5000(o2));
                                    break;
                            }
                        });
                        return giveBanknotesBundle;
                    }
                }
            } else {
                try {
                    throw new ExceptionGiveCash("ATM.ATM cannot issue requested amount of money");
                } catch (ExceptionGiveCash exceptionGiveCash) {
                    exceptionGiveCash.printStackTrace();
                } finally {
                    return null;
                }
            }
        }
        return null;
    }
}
