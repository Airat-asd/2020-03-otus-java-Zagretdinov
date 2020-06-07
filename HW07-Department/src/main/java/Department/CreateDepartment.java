package Department;

import ATM.*;
import Banknotes.*;

import java.util.Arrays;

/**
 * @author Ayrat Zagretdinov
 * created on 06.06.2020
 */
public class CreateDepartment extends DepartmentProcessor {
    String[] namesATM = {"Kirovsky", "Oktyabrsky", "Leninsky", "Kalininsky", "Sovetksy"};
    @Override
    protected void processInternal(Department department) {
        for (int i=0; i<5; i++) {
            department.addATM(new ATMImpl(namesATM[i], Arrays.asList(new Banknote50(10),
                    new Banknote100(10), new Banknote500(10), new Banknote1000(10),
                    new Banknote2000(10), new Banknote5000(10))));
        }
    }

    @Override
    public String getProcessName() {

        return "created.";
    }
}
