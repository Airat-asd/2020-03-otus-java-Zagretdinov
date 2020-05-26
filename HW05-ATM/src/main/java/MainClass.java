import java.util.Map;

public class MainClass {
    public static void main(String[] args) {
        ATM atm1 = new ATMImpl();
        System.out.println("AcceptCash.acceptCash = " + AcceptCash.acceptCash(atm1, new BundleOfBanknotesImpl1()));
        System.out.println("atm1.getBalance() = " + atm1.getBalance());
        System.out.println("atm1.getBanknotesContainer() = " + atm1.getBanknotesContainer());
        System.out.println("GiveCash.giveCash = " + GiveCash.giveCash(atm1,100));
        System.out.println("atm1.getBalance() = " + atm1.getBalance());
        System.out.println("atm1.getBanknotesContainer() = " + atm1.getBanknotesContainer());

    }

}
