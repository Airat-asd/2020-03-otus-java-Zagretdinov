import Banknotes.Banknotes;

import java.util.List;

//определяем новое состояние контейнера с банкнотами после выдачи денег
public class TakeCashFromTheContainer {
    public static void takeCashFromTheContainer(List<Banknotes> banknotesContainer, List<Banknotes> giveCash) {
        if (!(giveCash.isEmpty() || giveCash == null)) {
            giveCash.forEach(giveBanknote -> {
                banknotesContainer.forEach(containerBanknote -> {
                    if (containerBanknote.getNameBanknote().equals(giveBanknote.getNameBanknote())) {
                        containerBanknote.addBanknotes(-1 * giveBanknote.getAmountBanknote());
                    }
                });
            });
        }
    }
}
