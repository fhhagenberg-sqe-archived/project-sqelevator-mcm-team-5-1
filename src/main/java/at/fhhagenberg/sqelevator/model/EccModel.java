package at.fhhagenberg.sqelevator.model;

import at.fhhagenberg.sqelevator.interfaces.IEccObservable;
import at.fhhagenberg.sqelevator.interfaces.IEccObserver;

import java.util.Vector;

public abstract class EccModel implements IEccObservable {

    protected ApplicationState applicationState;

    protected Vector<IEccObserver> observers;

    public EccModel() {
        observers = new Vector<>();
    }

    @Override
    public void addObserver(IEccObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(IEccObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(ApplicationState applicationState) {
        for (IEccObserver observer : observers) {
            observer.applicationStateChanged(applicationState);
        }
    }
}
