package at.fhhagenberg.sqelevator.interfaces;

import at.fhhagenberg.sqelevator.model.ApplicationState;

/**
 * Implemented by classes that want to be able to observe an observable and be notified of changes to application state.
 */
public interface IEccObserver {

    /**
     * Method to be called by the observable when its application state changes.
     * @param applicationState The updated application state.
     */
    void applicationStateChanged(ApplicationState applicationState);
}
