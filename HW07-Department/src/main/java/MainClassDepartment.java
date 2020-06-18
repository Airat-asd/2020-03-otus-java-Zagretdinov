import Department.*;
import Memento.Originator;
import Memento.State;


public class MainClassDepartment {
    public static void main(String[] args) {
        System.out.println("-----------------Chain of responsibility-------------------- ");
        Department ufaCityDepartment = new Department("Ufa");
        DepartmentProcessor createUfaCityDepartment = new CreateDepartment();
        DepartmentProcessor initialUfaCityDepartment = new InitialDepartment();

        createUfaCityDepartment.setNext(initialUfaCityDepartment);
        createUfaCityDepartment.process(ufaCityDepartment);
        System.out.println("Print ufaCityDepartment:");
        ufaCityDepartment.printDepartment();

        Department moscowDepartment = new Department("Moscow");
        DepartmentProcessor createMoscowDepartment = new CreateDepartment();
        DepartmentProcessor initialMoscowDepartment = new InitialDepartment();

        createMoscowDepartment.setNext(initialMoscowDepartment);
        createMoscowDepartment.process(moscowDepartment);
        System.out.println("Print moscowDepartment:");
        moscowDepartment.printDepartment();

        System.out.println("-----------------Observer-------------------- ");
        System.out.print("Balance ufaCityDepartment = ");
        ufaCityDepartment.event(new CalculateTheBalance());
        ufaCityDepartment.removeATM(ufaCityDepartment.getATM().get(0));
        System.out.println("Removed ATM Kirovsky");
        System.out.print("Balance ufaCityDepartment = ");
        ufaCityDepartment.event(new CalculateTheBalance());

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
