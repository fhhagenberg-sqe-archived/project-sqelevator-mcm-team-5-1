package at.fhhagenberg.sqelevator;

import at.fhhagenberg.sqelevator.controller.EccController;
import at.fhhagenberg.sqelevator.model.ApplicationModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.rmi.RemoteException;

@ExtendWith(MockitoExtension.class)
public class EccControllerTest {

    @Mock
    public ApplicationModel model = new ApplicationModel();

    @Test
    public void testSetSelectedFloor() {

        EccController controller = new EccController(model);

        controller.setSelectedFloor(1, 1);

        try {
            Mockito.verify(model, Mockito.times(1)).setManualElevatorTarget(Mockito.eq(1), Mockito.eq(1));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
