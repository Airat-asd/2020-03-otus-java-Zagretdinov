import java.util.Map;

public class MainClass {
    public static void main(String[] args) {
        ATM atm1 = new ATMImpl();
        System.out.println("AcceptCash.acceptCash = " + AcceptCash.acceptCash(atm1, new BundleOfBanknotesImpl1()));
        System.out.println("atm1.getBalance() = " + atm1.getBalance());
        System.out.println("atm1.getBanknotesContainer() = " + atm1.getBanknotesContainer());
        System.out.println("GiveCash.giveCash 6600 = " + GiveCash.giveCash(atm1, 6600));
        System.out.println("atm1.getBalance() = " + atm1.getBalance());
        System.out.println("atm1.getBanknotesContainer() = " + atm1.getBanknotesContainer());
        System.out.println("GiveCash.giveCash -8660 = " + GiveCash.giveCash(atm1, -8660));
        System.out.println("GiveCash.giveCash 8660 = " + GiveCash.giveCash(atm1, 8660));
        System.out.println("GiveCash.giveCash 2200 = " + GiveCash.giveCash(atm1, 2200));
        System.out.println("atm1.getBalance() = " + atm1.getBalance());
    }

}
