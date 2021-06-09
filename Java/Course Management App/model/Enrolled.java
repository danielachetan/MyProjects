package lab3.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

public class Enrolled implements Subject {
    List<Observer> observers;

    public Enrolled() {
        observers = new ArrayList<Observer>();
    }

    @Override
    public void register(Observer o) {
        observers.add(o);
    }

    @Override
    public void notifyObserver() {

    }
}
