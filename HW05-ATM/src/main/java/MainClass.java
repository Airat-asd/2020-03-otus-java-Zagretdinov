public class MainClass {
    public static void main(String[] args) {
        BundleOfBanknotes bundle1 = new BundleOfBanknotesImpl1();
        BundleOfBanknotes bundle2 = new BundleOfBanknotesImpl2();
        BundleOfBanknotes bundle3 = new BundleOfBanknotesImpl3();
        ATMImpl atm2 = new ATMImpl();
        ATMImpl atm1 = new ATMImpl(bundle1.getBundleOfBanknotes());

        System.out.println("1---------------------------");
        System.out.println("Create ATM1, balance ATM1 = " + atm1.getBalance());
        System.out.println("Create ATM2, balance ATM2 = " + atm2.getBalance());
        System.out.println("2---------------------------");
        atm1.acceptCash(bundle1.getBundleOfBanknotes());
        System.out.println("Accept 43250, balance ATM1 = " + atm1.getBalance());
        System.out.println("3---------------------------");
        atm1.acceptCash(bundle2.getBundleOfBanknotes());
        System.out.println("Accept 750, balance ATM1 = " + atm1.getBalance());
        System.out.println("4---------------------------");
        atm1.acceptCash(bundle3.getBundleOfBanknotes());
        System.out.println("Accept 0, balance ATM1 = " + atm1.getBalance());
        System.out.println("5---------------------------");
        System.out.println("give 92000 = " + atm1.giveCash(92000));
        System.out.println("Balance ATM1 = " + atm1.getBalance());
        System.out.println("6---------------------------");
        atm2.acceptCash(bundle1.getBundleOfBanknotes());
        atm2.acceptCash(bundle2.getBundleOfBanknotes());
        System.out.println("Accept 43250 and 750, balance ATM2 = " + atm2.getBalance());
    }
}
