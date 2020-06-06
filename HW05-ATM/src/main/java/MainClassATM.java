import Banknotes.*;

import java.util.ArrayList;
import java.util.Arrays;

public class MainClassATM {
    public static void main(String[] args) {
        System.out.println("1---------------------------");
        ATMImpl atm1 = new ATMImpl();
        System.out.println("Create ATM1, balance ATM1 = " + atm1.getBalance());

        System.out.println("2---------------------------");
        atm1.acceptCash(new ArrayList<>(Arrays.asList(new Banknote50(10), new Banknote5000(10))));
        System.out.println("Accept " + (50 * 10 + 5000 * 10) + ", balance ATM1 = " + atm1.getBalance());
        atm1.acceptCash(new ArrayList<>(Arrays.asList(new Banknote100(10))));
        System.out.println("Accept " + (100 * 10) + ", balance ATM1 = " + atm1.getBalance());

        System.out.println("3---------------------------");
        var giveCash = atm1.giveCash(250);
        System.out.println("give 250: ");
        giveCash.forEach(o -> System.out.println(o.getNominalBanknote() + "= " + o.getAmountBanknote()));
        System.out.println("balance ATM1 = " + atm1.getBalance());

        System.out.println("4---------------------------");
        ATMImpl atm2 = new ATMImpl(Arrays.asList(new Banknote50(10), new Banknote5000(10), new Banknote1000(10)));
        System.out.println("Create ATM2(" + (50 * 10 + 5000 * 10 + 1000 * 10) + ")  balance = " + atm2.getBalance());

        System.out.println("5---------------------------");
        atm2.acceptCash(new ArrayList<>(Arrays.asList(new Banknote50(100))));
        System.out.println("Accept " + (50 * 100) + ", balance ATM2 = " + atm2.getBalance());

        System.out.println("6---------------------------");
        var giveCash2 = atm2.giveCash(350);
        System.out.println("give 350: ");
        giveCash2.forEach(o -> System.out.println(o.getNominalBanknote() + "= " + o.getAmountBanknote()));
        System.out.println("balance ATM2 = " + atm2.getBalance());

    }
}
