public class MainClass {
    public static void main(String[] args) {
        BundleOfBanknotes bundle1 = new BundleOfBanknotesImpl1();
        BundleOfBanknotes bundle2 = new BundleOfBanknotesImpl2();
        BundleOfBanknotes bundle3 = new BundleOfBanknotesImpl3();
        ATMImpl atm1 = new ATMImpl(bundle1.getBundleOfBanknotes());
        ATMImpl atm2 = new ATMImpl();
//        System.out.println(atm1.getBalance());
//        System.out.println("1---------------------------");
//        atm1.acceptCash(bundle1.getBundleOfBanknotes());
//        System.out.println(atm1.getBalance());
//        System.out.println("2---------------------------");
//        atm1.acceptCash(bundle2.getBundleOfBanknotes());
//        System.out.println(atm1.getBalance());
//        System.out.println("3---------------------------");
//        atm1.acceptCash(bundle3.getBundleOfBanknotes());
//        System.out.println(atm1.getBalance());
//        System.out.println("4---------------------------");
//        System.out.println("give 92000 = " + atm1.giveCash(92000));
//        System.out.println(atm1.getBalance());
        System.out.println("5---------------------------");
        atm2.acceptCash(bundle1.getBundleOfBanknotes());
        atm2.acceptCash(bundle2.getBundleOfBanknotes());

        System.out.println(atm2.getBalance());
    }

}
