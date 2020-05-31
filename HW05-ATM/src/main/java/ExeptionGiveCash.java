/**
 * @author Ayrat Zagretdinov
 * created on 21.05.2020
 */
public class ExeptionGiveCash extends RuntimeException {
    public ExeptionGiveCash() {
    }

    public ExeptionGiveCash(String s) {
        super(s);
    }
}
