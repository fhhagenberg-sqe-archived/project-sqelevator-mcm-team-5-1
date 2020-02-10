package at.fhhagenberg.sqelevator.interfaces;

import at.fhhagenberg.sqelevator.model.ApplicationState;

public interface IEccObserver {

    void applicationStateChanged(ApplicationState applicationState);
}
