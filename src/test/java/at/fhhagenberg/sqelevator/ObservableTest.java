package at.fhhagenberg.sqelevator;

import at.fhhagenberg.sqelevator.interfaces.IEccObserver;
import at.fhhagenberg.sqelevator.model.ApplicationModel;
import at.fhhagenberg.sqelevator.model.ApplicationState;
import at.fhhagenberg.sqelevator.model.EccModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ObservableTest implements IEccObserver {

    public int callCount = 0;
    public ApplicationState update;

    public void resetCalls() {
        this.callCount = 0;
        this.update = null;
    }

    @Test
    public void testAddObservers() {

        EccModel observable = new ApplicationModel();
        ApplicationState testState = new ApplicationState();

        observable.notifyObservers(testState);

        Assertions.assertEquals(0, callCount);
        Assertions.assertNull(update);

        observable.addObserver(this);

        observable.notifyObservers(testState);

        Assertions.assertEquals(1, callCount);
        Assertions.assertEquals(testState, update);

        resetCalls();
    }

    @Test
    public void testRemoveObservers() {

        EccModel observable = new ApplicationModel();
        ApplicationState testState = new ApplicationState();

        observable.addObserver(this);
        observable.notifyObservers(testState);

        Assertions.assertEquals(1, callCount);
        Assertions.assertEquals(testState, update);

        resetCalls();

        observable.removeObserver(this);

        observable.notifyObservers(testState);

        Assertions.assertEquals(0, callCount);
        Assertions.assertNull(update);

        resetCalls();
    }

    @Test
    public void testNotifyObservers() {

        EccModel observable = new ApplicationModel();
        ApplicationState testState = new ApplicationState();
        ApplicationState testState2 = new ApplicationState();

        observable.addObserver(this);
        observable.notifyObservers(testState);

        Assertions.assertEquals(1, callCount);
        Assertions.assertEquals(testState, update);

        observable.notifyObservers(testState2);

        Assertions.assertEquals(2, callCount);
        Assertions.assertEquals(testState2, update);

        resetCalls();
    }

    @Override
    public void applicationStateChanged(ApplicationState applicationState) {
        callCount++;
        this.update = applicationState;
    }
}
