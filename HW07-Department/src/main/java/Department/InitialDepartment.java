package Department;

/**
 * @author Ayrat Zagretdinov
 * created on 06.06.2020
 */
public class InitialDepartment extends DepartmentProcessor{
    @Override
    protected void processInternal(Department department) {
        department.initial();
    }

    @Override
    public String getProcessName() {
        return "initialized.";
    }
}
