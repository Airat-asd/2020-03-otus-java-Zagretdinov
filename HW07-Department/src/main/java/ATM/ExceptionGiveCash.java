package ATM;

/**
 * @author Ayrat Zagretdinov
 * created on 21.05.2020
 */
public class ExceptionGiveCash extends RuntimeException {
    public ExceptionGiveCash() {
    }

    public ExceptionGiveCash(String s) {
        super(s);
    }
}
