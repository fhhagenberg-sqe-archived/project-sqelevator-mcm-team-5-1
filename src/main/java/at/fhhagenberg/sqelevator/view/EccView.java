package at.fhhagenberg.sqelevator.view;

import at.fhhagenberg.sqelevator.constants.Constants;
import at.fhhagenberg.sqelevator.controller.EccController;

import java.awt.*;

public abstract class EccView {

    protected EccController controller;

    protected int width, height;

    protected Frame windowFrame;

    protected String title = Constants.DEFAULT_WINDOW_TITLE;

    public EccView(EccController controller, int width, int height) {
        this.controller = controller;
        this.width = width;
        this.height = height;
    }

    public void open() {
        windowFrame = new Frame(title);
        windowFrame.setSize(width, height);
        windowFrame.setResizable(false);
        windowFrame.addWindowListener(controller);
        addComponents(windowFrame);
        windowFrame.setVisible(true);
    }

    public void close() {
        windowFrame.dispose();
    }

    public abstract void addComponents(Frame windowFrame);
}
