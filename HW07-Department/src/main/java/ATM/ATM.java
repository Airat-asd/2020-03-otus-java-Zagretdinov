package ATM;

import Banknotes.Banknotes;

import java.util.List;

/**
 * @author Ayrat Zagretdinov
 * created on 16.05.2020
 */
public interface ATM {

    void acceptCash(List<Banknotes> bundle);

    List<Banknotes> giveCash(int amount);

    int getBalance();

    void initialATM();

}
