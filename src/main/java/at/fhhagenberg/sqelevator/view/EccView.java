package at.fhhagenberg.sqelevator.view;

import at.fhhagenberg.sqelevator.constants.Constants;
import at.fhhagenberg.sqelevator.controller.EccController;
import at.fhhagenberg.sqelevator.interfaces.IEccObserver;

import java.awt.*;

/**
 * Abstract View class for the ECC application. Implements commonly needed functionality.
 */
public abstract class EccView implements IEccObserver {

    protected EccController controller;

    protected int width, height;

    protected Frame windowFrame;

    protected String title = Constants.DEFAULT_WINDOW_TITLE;

    /**
     * Default constructor with all necessary parameters.
     * @param controller The controller object that handles interactions between View and Model
     * @param width The width of the window.
     * @param height The height of the window.
     */
    public EccView(EccController controller, int width, int height) {
        this.controller = controller;
        this.width = width;
        this.height = height;
    }

    /**
     * Template method that implements all necessary steps for a new window to open. The window layout construction is
     * done in the concrete subclass in the addComponents method.
     */
    public void open() {
        windowFrame = new Frame(title);
        windowFrame.setSize(width, height);
        windowFrame.setResizable(false);
        windowFrame.addWindowListener(controller);
        addComponents(windowFrame);
        windowFrame.setVisible(true);
    }

    /**
     * Closes the window.
     */
    public void close() {
        windowFrame.dispose();
    }

    /**
     * Abstract method for layout construction and adding components to the window.
     * @param windowFrame The frame object to which components can be added.
     */
    public abstract void addComponents(Frame windowFrame);

}
