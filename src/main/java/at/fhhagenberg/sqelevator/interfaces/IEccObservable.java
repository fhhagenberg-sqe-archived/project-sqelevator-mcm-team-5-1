package at.fhhagenberg.sqelevator.interfaces;

import at.fhhagenberg.sqelevator.model.ApplicationState;

/**
 * Observable interface. Allows a class to be observed by others and to notify them when the status changes.
 */
public interface IEccObservable {

    /**
     * Adds an observer to the observed class.
     * @param observer The observer to be added.
     */
    void addObserver(IEccObserver observer);

    /**
     * Removes the specified observer from the list of observers of a class.
     * @param observer The observer to remove.
     */
    void removeObserver(IEccObserver observer);

    /**
     * Notifies all registered observers of changes to the application state.
     * @param applicationState The new, updated state of the application.
     */
    void notifyObservers(ApplicationState applicationState);
}
