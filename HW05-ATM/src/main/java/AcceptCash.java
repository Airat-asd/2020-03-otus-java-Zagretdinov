import java.util.Map;
import java.util.TreeMap;

public class AcceptCash {

    //Принимаем банкноты разных номиналов (на каждый номинал своя ячейка) в экземпляр банкомата
    public static Map<Integer, Integer> acceptCash(ATM atm, BundleOfBanknotes bundle) {
        //Контейнер банкомата с купюрами отсортированными по ящейкам
        Map<Integer, Integer> banknotesContainerBufer = new TreeMap<>((o1, o2) -> o2 - o1);
        int newQuantityBanknote = 0;
        int balanceBufer = atm.getBalance();
        if (!bundle.getBundleOfBanknotes().isEmpty()) {
            banknotesContainerBufer.putAll(atm.getBanknotesContainer());
            for (Integer banknote : bundle.getBundleOfBanknotes()) {
                if (banknotesContainerBufer.containsKey(banknote)) {
                    newQuantityBanknote = banknotesContainerBufer.get(banknote) + 1;
                    banknotesContainerBufer.replace(banknote, newQuantityBanknote);
                } else {
                    banknotesContainerBufer.put(banknote, 1);
                }
                balanceBufer = balanceBufer + banknote;     // Остаток денежных средств (баланс)
            }
            atm.setBalance(balanceBufer);
            atm.setBanknotesContainer(banknotesContainerBufer);
        }
        return banknotesContainerBufer;
    }
}
