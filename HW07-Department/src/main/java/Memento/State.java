package Memento;

import ATM.*;
import Department.Department;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ayrat Zagretdinov
 * created on 07.06.2020
 */
public class State {
    private Department department;

    public State(Department department) {
        this.department = department;
    }

    public State(State state) {
        this.department = new Department(state.getDepartment().getName(), state.getDepartment().getATM());
    }

    public Department getDepartment() {
        return department;
    }

    @Override
    public String toString() {
        final String[] s = {""};
        department.getATM().forEach(o -> s[0] = s[0] + o);
        return "State{" +
                "atm=" + s[0] +
                '}';
    }
}