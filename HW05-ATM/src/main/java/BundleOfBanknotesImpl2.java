import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BundleOfBanknotesImpl2 implements BundleOfBanknotes {
    List<Integer> billAcceptor = new ArrayList<>(Arrays.asList(Banknotes.FIFTY_50.getBanknote(),
            Banknotes.THOUSAND_1000.getBanknote(), Banknotes.TWOTHOUSAND_2000.getBanknote(), Banknotes.THOUSAND_1000.getBanknote(),
            Banknotes.TWOTHOUSAND_2000.getBanknote()));

    @Override
    public List<Integer> getBundleOfBanknotes() {
        return billAcceptor;
    }
}
