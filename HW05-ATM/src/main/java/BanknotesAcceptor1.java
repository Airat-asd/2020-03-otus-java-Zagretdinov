import java.util.ArrayList;
import java.util.List;

/**
 * @author Ayrat Zagretdinov
 * created on 16.05.2020
 */
public class BanknotesAcceptor1 implements BanknotesAcceptorInterface {
    List<Integer> billAcceptor = new ArrayList<>();

    @Override
    public List<Integer> getBillAcceptor() {
        billAcceptor = List.of(50,100,500,1000,2000,5000,50,100);
        return billAcceptor;
    }
}
