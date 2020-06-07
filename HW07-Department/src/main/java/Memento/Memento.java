package Memento;

/**
 * @author Ayrat Zagretdinov
 * created on 07.06.2020
 */
public class Memento {
    private final State state;

    public Memento(State state) {
        this.state = new State(state);
    }

    State getState() {
        return state;
    }
}
