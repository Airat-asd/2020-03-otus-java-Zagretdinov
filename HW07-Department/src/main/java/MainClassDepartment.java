import Department.*;
import Memento.Originator;
import Memento.State;


public class MainClassDepartment {
    public static void main(String[] args) {
        System.out.println("-----------------Chain of responsibility-------------------- ");
        Department ufaCityDepartment = new Department("Ufa");
        DepartmentProcessor create = new CreateDepartment();
        DepartmentProcessor initial = new InitialDepartment();

        create.setNext(initial);
        create.process(ufaCityDepartment);
        System.out.println("---------------------------");
        ufaCityDepartment.printDepartment();

        System.out.println("-----------------Visitor-------------------- ");
        DepartmentEvent departmentEvent = new DepartmentEvent();
        departmentEvent.addListener(ufaCityDepartment.getATM());
        departmentEvent.event();
        departmentEvent.removeListener(ufaCityDepartment.getATM());
        departmentEvent.event();

        System.out.println("-----------------Memento-------------------- ");
        Originator originator = new Originator();
        State stateDepartment = new State(ufaCityDepartment);
        System.out.println(stateDepartment);
        originator.saveState(stateDepartment);

        stateDepartment.getDepartment().getATM().clear();
        System.out.println("---Clear---");
        System.out.println(stateDepartment);

        stateDepartment = originator.restoreState();
        System.out.println("---Undo---");
        System.out.println(stateDepartment);
    }
}
