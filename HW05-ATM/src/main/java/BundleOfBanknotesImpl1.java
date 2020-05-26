import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BundleOfBanknotesImpl1 implements BundleOfBanknotes {
    List<Integer> bundleOfBanknotes = new ArrayList<>(Arrays.asList(Banknotes.FIFTY_50.getBanknote(),
            Banknotes.HUNDRED_100.getBanknote(), Banknotes.FIVEHUNDRED_500.getBanknote(), Banknotes.THOUSAND_1000.getBanknote(),
            Banknotes.TWOTHOUSAND_2000.getBanknote(), Banknotes.FIVETHOUSAND_5000.getBanknote(), Banknotes.FIFTY_50.getBanknote(),
            Banknotes.HUNDRED_100.getBanknote()));

    @Override
    public List<Integer> getBundleOfBanknotes() {
        return bundleOfBanknotes;
    }
}