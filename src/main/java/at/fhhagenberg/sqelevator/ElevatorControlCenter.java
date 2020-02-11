package at.fhhagenberg.sqelevator;

import at.fhhagenberg.sqelevator.constants.Constants;
import at.fhhagenberg.sqelevator.controller.EccController;
import at.fhhagenberg.sqelevator.model.ApplicationModel;
import at.fhhagenberg.sqelevator.view.EccView;
import at.fhhagenberg.sqelevator.view.OperatorView;

/**
 * Main class of the program, contains the launcher method
 */
public class ElevatorControlCenter {

    /**
     * Main method of the program. Sets up the MVC structure and launches the application.
     * @param args Possible command line arguments. Not in use.
     */
    public static void main(String[] args) {

        ApplicationModel model = new ApplicationModel();

        EccController controller = new EccController(model);
        EccView view = new OperatorView(controller, Constants.DEFAULT_WINDOW_WIDTH, Constants.DEFAULT_WINDOW_HEIGHT);

        model.addObserver(view);
        controller.initApplication();
        view.open();
    }

}
