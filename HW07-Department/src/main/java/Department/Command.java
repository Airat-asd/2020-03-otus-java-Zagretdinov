package Department;

import ATM.ATM;

import java.util.List;

@FunctionalInterface
public interface Command {
    int execute(List<ATM> department);
}
