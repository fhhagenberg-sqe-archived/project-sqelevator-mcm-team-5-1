package at.fhhagenberg.sqelevator.model;

import at.fhhagenberg.sqelevator.interfaces.IEccObservable;
import at.fhhagenberg.sqelevator.interfaces.IEccObserver;

import java.util.Vector;

/**
 * Abstract Model class for different possible models within the application. Implements commonly used functionality,
 * such as the addition and removal of observers.
 */
public abstract class EccModel implements IEccObservable {

    protected ApplicationState applicationState;

    protected Vector<IEccObserver> observers;

    /**
     * Constructor that initializes the commonly used necessary objects.
     */
    public EccModel() {
        observers = new Vector<>();
        applicationState = new ApplicationState();
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
