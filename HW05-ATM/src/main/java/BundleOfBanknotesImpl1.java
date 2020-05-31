import java.util.*;

public class BundleOfBanknotesImpl1 implements BundleOfBanknotes {
    private Map<Banknotes, Integer> bundleOfBanknotes = new TreeMap<>();

    public BundleOfBanknotesImpl1() {
        for (Banknotes banknote : BanknotesImpl.values()) {
            bundleOfBanknotes.put(banknote, 5);
        }
    }

    @Override
    public Map<Banknotes, Integer> getBundleOfBanknotes() {
        return bundleOfBanknotes;
    }
}