package sptech.school.order_hub.observer;

import java.util.ArrayList;
import java.util.List;

public class Subject {
    private final List<Observer> observers = new ArrayList<>();

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    public void notifyObservers(String message) {
        observers.forEach(observer -> observer.update(message));
    }
}
