import java.util.*;

public class BundleOfBanknotesImpl2 implements BundleOfBanknotes {
    private Map<Banknotes, Integer> bundleOfBanknotes = Map.of(
            Banknotes.FIFTY_50, 5,
            Banknotes.HUNDRED_100, 5
    );

    @Override
    public Map<Banknotes, Integer> getBundleOfBanknotes() {
        return bundleOfBanknotes;
    }
}