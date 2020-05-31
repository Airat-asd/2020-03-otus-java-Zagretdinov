import java.util.Map;

//определяем новое состояние контейнера с банкнотами после выдачи денег
public class TakeCashFromTheContainer {
    public static Map<Banknotes, Integer> takeCashFromTheContainer(Map<Banknotes, Integer> banknotesContainer, Map<Banknotes, Integer> giveCash) {
        if (!giveCash.isEmpty()) {
            Map<Banknotes, Integer> newbanknotesContainer = banknotesContainer;
            giveCash.forEach((key, value) -> newbanknotesContainer.put(key, (banknotesContainer.get(key) - value)));
            return newbanknotesContainer;
        } else {
            return banknotesContainer;
        }
    }
}
