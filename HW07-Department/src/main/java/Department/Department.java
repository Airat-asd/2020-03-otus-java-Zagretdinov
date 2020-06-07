package Department;

import ATM.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ayrat Zagretdinov
 * created on 06.06.2020
 */
public class Department {
    private final List<Listener> department = new ArrayList<>();
    private String name;

    public Department(String name) {
        this.name = name;
    }

    public Department(String name, List<Listener> atm) {
        this.name = name;
        department.addAll(atm);
    }

    public String getName() {
        return name;
    }

    void addATM(Listener atm) {
        department.add(atm);
    }

    public void initial() {
        department.forEach(o -> o.initialATM());
    }

    public void printDepartment() {
        department.forEach(o -> System.out.print(o));
    }

    private final List<Listener> listeners = new ArrayList<>();

    public List<Listener> getATM() {
        return department;
    }

}
