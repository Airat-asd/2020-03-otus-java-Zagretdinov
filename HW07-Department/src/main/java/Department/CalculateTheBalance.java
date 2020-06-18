package Department;

import ATM.ATM;

import java.util.List;

public class CalculateTheBalance implements Command {
    @Override
    public int execute(List<ATM> department) {
        final int[] balance = new int[1];
        department.forEach(atm -> balance[0] = balance[0] + atm.getBalance());
        return balance[0];
    }
}
