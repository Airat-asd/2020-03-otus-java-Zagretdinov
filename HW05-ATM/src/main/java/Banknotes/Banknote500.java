package Banknotes;

/**
 * @author Ayrat Zagretdinov
 * created on 05.06.2020
 */
public class Banknote500 implements Banknotes {
    private final static int MAX_AMOUNT_BANKNOTES = 1000;
    private String nameBanknote = BanknotesEnum.FIVEHUNDRED_500.name();
    private int nominalBanknote = BanknotesEnum.FIVEHUNDRED_500.getBanknote();
    private int amountBanknote;

    public Banknote500() {
    }

    public Banknote500(int amountBanknote) {
        this.amountBanknote = amountBanknote;
    }

    @Override
    public String getNameBanknote() {
        return nameBanknote;
    }

    @Override
    public int getNominalBanknote() {
        return nominalBanknote;
    }

    @Override
    public boolean addBanknotes(int amount) {
        if ((amount + amountBanknote) <= MAX_AMOUNT_BANKNOTES) {
            amountBanknote = amountBanknote + amount;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean setAmountBanknote(int amount) {
        if ((amount) <= MAX_AMOUNT_BANKNOTES) {
            amountBanknote = amount;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int getAmountBanknote() {
        return this.amountBanknote;
    }
}
