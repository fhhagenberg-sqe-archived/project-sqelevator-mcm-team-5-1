package at.fhhagenberg.sqelevator;

import at.fhhagenberg.sqelevator.constants.Constants;
import at.fhhagenberg.sqelevator.controller.EccController;
import at.fhhagenberg.sqelevator.model.ApplicationModel;
import at.fhhagenberg.sqelevator.view.EccView;
import at.fhhagenberg.sqelevator.view.OperatorView;

public class ElevatorControlCenter {

    public static void main(String[] args) {

        ApplicationModel model = new ApplicationModel();
        EccController controller = new EccController(model);
        EccView view = new OperatorView(controller, Constants.DEFAULT_WINDOW_WIDTH, Constants.DEFAULT_WINDOW_HEIGHT);

        model.addObserver(view);
        view.open();
        controller.initApplication();
    }

}
