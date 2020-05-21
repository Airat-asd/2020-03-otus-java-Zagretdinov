import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Ayrat Zagretdinov
 * created on 16.05.2020
 */
public class BanknotesAcceptor2 implements BanknotesAcceptorInterface {
    List<Integer> billAcceptor = new ArrayList<>(Arrays.asList(1000,2000,1000,2000));

    @Override
    public List<Integer> getBillAcceptor() {
        return billAcceptor;
    }
}
