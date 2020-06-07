package Department;

import ATM.ATM;

/**
 * @author Ayrat Zagretdinov
 * created on 06.06.2020
 */
public abstract class DepartmentProcessor {
    private DepartmentProcessor next;

    private DepartmentProcessor getNext() {
        return next;
    }

    public void setNext(DepartmentProcessor next) {
        this.next = next;
    }

    public void process(Department department) {
        processInternal(department);
        after(department);
        if (getNext() != null) {
            getNext().process(department);
        }
    }

    protected abstract void processInternal(Department department);

    public abstract String getProcessName();


    private void after(Department department) {
        System.out.println("Department " + department.getName() + ": " + getProcessName());
    }
}
