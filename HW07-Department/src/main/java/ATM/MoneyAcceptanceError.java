package ATM;

/**
 * @author Ayrat Zagretdinov
 * created on 04.06.2020
 */
public class MoneyAcceptanceError extends RuntimeException {

    public MoneyAcceptanceError() {
    }

    public MoneyAcceptanceError(String s) {
        super(s);
    }
}
