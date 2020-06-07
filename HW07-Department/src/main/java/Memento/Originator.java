package Memento;

import ATM.*;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @author Ayrat Zagretdinov
 * created on 07.06.2020
 */
public class Originator {
    private final Deque<Memento> stack = new ArrayDeque<>();

    public void saveState(State state) {
        stack.push(new Memento(state));
    }

    public State restoreState() {
        return stack.pop().getState();
    }
}
