package at.fhhagenberg.sqelevator.view;

import at.fhhagenberg.sqelevator.controller.EccController;
import at.fhhagenberg.sqelevator.model.ApplicationState;

import java.awt.*;

public class OperatorView extends EccView {

    public OperatorView(EccController controller, int width, int height) {
        super(controller, width, height);
    }

    @Override
    public void addComponents(Frame windowFrame) {

    }

    @Override
    public void applicationStateChanged(ApplicationState applicationState) {
        System.out.println(applicationState.numberOfElevators);

        if (applicationState.numberOfElevators > 0) {
            System.out.println(applicationState.elevators.get(0).toString());
        }
    }
}
