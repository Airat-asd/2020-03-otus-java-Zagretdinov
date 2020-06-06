import Banknotes.*;

import java.util.*;

/**
 * @author Ayrat Zagretdinov
 * created on 16.05.2020
 */
public class ATMImpl implements ATM {
    //Контейнер банкомата с купюрами отсортированными по ящейкам
    List<Banknotes> banknotesContainer = new ArrayList<>(Arrays.asList(
            new Banknote50(0),
            new Banknote100(0),
            new Banknote500(0),
            new Banknote1000(0),
            new Banknote2000(0),
            new Banknote5000(0)
    ));
    // Остаток денежных средств (баланс)
    private int balance;

    public ATMImpl(List<Banknotes> newBanknotesContainer) {
        if (!(newBanknotesContainer.isEmpty() || newBanknotesContainer == null)) {
            for (Banknotes newBanknote : newBanknotesContainer) {
                for (Banknotes containerBanknote : this.banknotesContainer) {
                    if (containerBanknote.getNameBanknote().equals(newBanknote.getNameBanknote())) {
                        containerBanknote.setAmountBanknote(newBanknote.getAmountBanknote());
                    }
                }
            }
        }
    }

    public ATMImpl() {
    }

    //Прием банкнот
    @Override
    public void acceptCash(List<Banknotes> bundle) {
        AcceptCash.acceptCash(bundle, banknotesContainer);
    }

    //выдача банкнот
    @Override
    public List<Banknotes> giveCash(int amount) {
        var giveCash = GiveCash.giveCash(banknotesContainer, balance, amount);
        TakeCashFromTheContainer.takeCashFromTheContainer(banknotesContainer, giveCash);
        return giveCash;
    }

    @Override
    public int getBalance() {
        balanceUpdate();
        return this.balance;
    }

    private void balanceUpdate() {
        this.balance = 0;
        banknotesContainer.forEach(banknote -> this.balance = this.balance + banknote.getNominalBanknote() * banknote.getAmountBanknote());
    }
}

//Устанавливаем состояние контейнера с банкнотами
//    private void setTheNewStateOfContainer(List<Banknotes> newBanknotesContainer) {
//        for (Banknotes newBanknote : newBanknotesContainer) {
//            for (Banknotes containerBanknote : this.banknotesContainer) {
//                if (containerBanknote.getNameBanknote().equals(newBanknote.getNameBanknote())) {
//                    containerBanknote.setAmountBanknote(newBanknote.getAmountBanknote());
//                } else {
//                    containerBanknote.setAmountBanknote(0);
//                }
//            }
//        }
//    }