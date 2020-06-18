package Banknotes;

/**
 * @author Ayrat Zagretdinov
 * created on 31.05.2020
 */
public interface Banknotes {

    String getNameBanknote();

    int getNominalBanknote();

    boolean addBanknotes(int amount);

    boolean setAmountBanknote(int amount);

    int getAmountBanknote();

}
