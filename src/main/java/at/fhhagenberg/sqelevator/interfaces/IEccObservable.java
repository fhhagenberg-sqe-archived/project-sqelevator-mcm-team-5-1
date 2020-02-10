package at.fhhagenberg.sqelevator.interfaces;

import at.fhhagenberg.sqelevator.model.ApplicationState;

public interface IEccObservable {

    void addObserver(IEccObserver observer);

    void removeObserver(IEccObserver observer);

    void notifyObservers(ApplicationState applicationState);
}
