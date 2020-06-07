package Department;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ayrat Zagretdinov
 * created on 07.06.2020
 */
public class DepartmentEvent {
    private final List<Listener> listeners = new ArrayList<>();

    public void addListener(List<Listener> listener) {
        listener.forEach(o->listeners.add(o));
    }

    public void removeListener(List<Listener> listener) {
        listener.forEach(o->listeners.remove(o));
    }

    public void event() {
        if (listeners.size() != 0) {
            listeners.forEach(listener -> listener.balanceCollection());
        } else System.out.println("No subscribers");
    }

}
