package ATM;

import Banknotes.*;
import Department.Listener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * @author Ayrat Zagretdinov
 * created on 16.05.2020
 */
public class ATMImpl implements ATM {
    private final int SERIAL_NUMBER = (new Random()).nextInt(1000);
    ;
    private String nameATM;
    private boolean init;
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

    public ATMImpl(String nameATM, List<Banknotes> newBanknotesContainer) {
        this.nameATM = nameATM;
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

    public ATMImpl(String nameATM) {
        this.nameATM = nameATM;
    }

    public ATMImpl() {
        nameATM = "default";
    }

    //Прием банкнот
    @Override
    public void acceptCash(List<Banknotes> bundle) {
        if (init) {
            AcceptCash.acceptCash(bundle, banknotesContainer);
        }
    }

    //выдача банкнот
    @Override
    public List<Banknotes> giveCash(int amount) {
        if (init) {
            var giveCash = GiveCash.giveCash(banknotesContainer, balance, amount);
            TakeCashFromTheContainer.takeCashFromTheContainer(banknotesContainer, giveCash);
            return giveCash;
        }
        return null;
    }

    @Override
    public int getBalance() {
        if (init) {
            balanceUpdate();
            return this.balance;
        }
        return 0;
    }

    public void balanceUpdate() {
        this.balance = 0;
        banknotesContainer.forEach(banknote ->
                this.balance = this.balance + banknote.getNominalBanknote() * banknote.getAmountBanknote());
    }

    @Override
    public String toString() {
        final String[] info = {""};
        if (init) {
            banknotesContainer.forEach(o -> info[0] = info[0] + o.getNameBanknote() + " = " + o.getAmountBanknote() + " pcs" + "\n");
            return "ATM{" + nameATM + "}, Serial number = " + SERIAL_NUMBER + "\n" + info[0] + "\n";
        }
        return "ATM{" + nameATM + "}, Serial number = " + SERIAL_NUMBER + "\n" + "The ATM is not initialized" + "\n";
    }

    @Override
    public void initialATM() {
        init = true;
    }
}