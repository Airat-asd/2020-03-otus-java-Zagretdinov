package ATM;

import Banknotes.Banknotes;

import java.util.List;


class AcceptCash {
    //Принимаем банкноты разных номиналов (на каждый номинал своя ячейка) в экземпляр банкомата
    static void acceptCash(List<Banknotes> bundle, List<Banknotes> banknotesContainer) {
        if (!bundle.isEmpty()) {
            if (!banknotesContainer.isEmpty()) {
                for (Banknotes banknoteBundle : bundle) {
                    for (Banknotes banknoteContainer : banknotesContainer) {
                        if (banknoteContainer.getNameBanknote().equals(banknoteBundle.getNameBanknote())) {
                            if (!banknoteContainer.addBanknotes(banknoteBundle.getAmountBanknote())) {
                                throw new MoneyAcceptanceError("Money acceptance error");
                            }
                        }
                    }
                }
            }
        }
    }
}