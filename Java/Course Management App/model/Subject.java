package lab3.model;

import java.util.Observer;

public interface Subject {
    public void register(Observer o);
    public void notifyObserver();
}
