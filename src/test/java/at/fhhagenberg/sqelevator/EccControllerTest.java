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

    public ApplicationModel model = Mockito.mock(ApplicationModel.class);

    @Test
    public void testSetSelectedFloor() {

        EccController controller = new EccController(model);

        controller.setSelectedFloor(1, 1);

        try {
            Mockito.verify(model, Mockito.times(1)).setManualElevatorTarget(Mockito.eq(1), Mockito.eq(1));
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        Mockito.reset(model);

        controller.setSelectedFloor(0, 3);

        try {
            Mockito.verify(model, Mockito.times(1)).setManualElevatorTarget(Mockito.eq(0), Mockito.eq(3));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
