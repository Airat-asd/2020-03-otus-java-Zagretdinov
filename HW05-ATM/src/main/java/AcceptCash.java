import java.util.Map;

public class AcceptCash {
    //Принимаем банкноты разных номиналов (на каждый номинал своя ячейка) в экземпляр банкомата
    public static Map<Banknotes, Integer> acceptCash(Map<Banknotes, Integer> bundle, Map<Banknotes, Integer> banknotesContainer) {
        if (!bundle.isEmpty()) {
            if (!banknotesContainer.isEmpty()) {
                bundle.forEach((bundleKey, bundleValue) -> banknotesContainer.compute(bundleKey,
                        (containerKey, containerValue) -> (containerValue + bundleValue)));
            } else {
                banknotesContainer.putAll(bundle);
            }
        }
        return banknotesContainer;
    }
}