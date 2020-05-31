import java.util.*;

public class BundleOfBanknotesImpl3 implements BundleOfBanknotes {
    private Map<Banknotes, Integer> bundleOfBanknotes = Map.of(
            Banknotes.FIFTY_50, 0,
            Banknotes.FIVETHOUSAND_5000, 0
    );

    @Override
    public Map<Banknotes, Integer> getBundleOfBanknotes() {
        return bundleOfBanknotes;
    }
}