package Department;

import ATM.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ayrat Zagretdinov
 * created on 06.06.2020
 */
public class Department implements Listener {
    private final List<ATM> department = new ArrayList<>();
    private String name;

    public Department(String name) {
        this.name = name;
    }

    public Department(String name, List<ATM> atm) {
        this.name = name;
        department.addAll(atm);
    }

    public Department(Department department) {
        this.name = department.getName();
        this.department.addAll(department.getATM());
    }

    public String getName() {
        return name;
    }

    void addATM(ATM atm) {
        department.add(atm);
    }

    public void initial() {
        department.forEach(o -> o.initialATM());
    }

    public void printDepartment() {
        department.forEach(o -> System.out.print(o));
    }

    public List<ATM> getATM() {
        return department;
    }

    public void removeATM(ATM atm) {
        department.remove(atm);
    }

    @Override
    public void event(Command command) {
        System.out.println(command.execute(department));
    }
}
