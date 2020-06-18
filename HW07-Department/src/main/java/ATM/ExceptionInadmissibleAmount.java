package ATM;

/**
 * @author Ayrat Zagretdinov
 * created on 04.06.2020
 */
public class ExceptionInadmissibleAmount extends RuntimeException {

    public ExceptionInadmissibleAmount() {
    }

    public ExceptionInadmissibleAmount(String s) {
        super(s);
    }

}
