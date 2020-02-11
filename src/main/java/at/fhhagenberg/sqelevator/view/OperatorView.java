package at.fhhagenberg.sqelevator.view;

import at.fhhagenberg.sqelevator.controller.EccController;
import at.fhhagenberg.sqelevator.model.ApplicationState;

import java.awt.*;

/**
 * The OperatorView represents the visualization of the data the model contains.
 * Gives information about current elevator cabin status and pressed Call/Stop buttons.
 */
public class OperatorView extends EccView {

    public OperatorView(EccController controller, int width, int height) {
        super(controller, width, height);
    }

    private Panel infoPanel;

    private Label payload;
    private Label speed;
    private Label doorStatus;
    private Label target;
    private Label directionUp;
    private Label directionDown;
    private Label currentPosition;
    private Label downButtonsPressed;
    private Label UpButtonsPressed;
    private Label elevatorPanelButtonsPressed;

    private Choice floorSelection;
    private Choice elevatorSelection;

    private Checkbox checkBoxAuto;
    private Checkbox checkBoxManual;

    private int elevatorIndex;
    private int floorIndex;

    /**
     * Adds UI elements to the provided frame and initializes them.
     *
     * @param windowFrame The frame object to which components can be added.
     */
    @Override
    public void addComponents(Frame windowFrame) {
        windowFrame.setLayout(new GridLayout(1, 3));
        windowFrame.setBackground(Color.lightGray);

        //******************************************************************************************************
        //Info panel to select elevator and to display relevant information about currently selected elevator
        //******************************************************************************************************
        infoPanel = new Panel();
        infoPanel.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.WEST;
        c.weightx = 1;
        elevatorSelection = new Choice();
        elevatorSelection.setSize(50, 75);
        elevatorSelection.addItemListener(e -> {
            controller.setSelectedElevator(elevatorSelection.getSelectedIndex());
            floorSelection.select(floorIndex);
        });
        infoPanel.add(elevatorSelection, c);
        elevatorSelection.setVisible(false);

        c.gridx = 0;
        c.gridy = 1;
        c.anchor = GridBagConstraints.WEST;
        c.weightx = 1;
        Label labelPayload = new Label("Payload:");
        infoPanel.add(labelPayload, c);

        c.gridy = 2;
        c.anchor = GridBagConstraints.CENTER;
        payload = new Label("____");
        infoPanel.add(payload, c);

        c.gridy = 3;
        c.anchor = GridBagConstraints.WEST;
        Label labelSpeed = new Label("Speed:");
        infoPanel.add(labelSpeed, c);

        c.gridy = 4;
        c.anchor = GridBagConstraints.CENTER;
        speed = new Label("__");
        infoPanel.add(speed, c);

        c.gridy = 5;
        c.anchor = GridBagConstraints.WEST;
        Label labelDoorStatus = new Label("Door Status:");
        infoPanel.add(labelDoorStatus, c);

        c.gridy = 6;
        c.anchor = GridBagConstraints.CENTER;
        doorStatus = new Label("__");
        infoPanel.add(doorStatus, c);

        c.gridy = 7;
        c.anchor = GridBagConstraints.WEST;
        Label labelTarget = new Label("Target:");
        infoPanel.add(labelTarget, c);

        c.gridy = 8;
        c.anchor = GridBagConstraints.CENTER;
        target = new Label("__");
        infoPanel.add(target, c);

        Font myFont = new Font("Helvetica", Font.PLAIN, 30);
        infoPanel.setFont(myFont);

        //******************************************************************************************************
        //Position panel to display current floor and direction which the elevator is going
        //******************************************************************************************************
        Panel positionPanel = new Panel();
        positionPanel.setLayout(new GridBagLayout());

        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.weighty = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        positionPanel.add(new Panel(), c);

        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 1;
        c.anchor = GridBagConstraints.CENTER;
        c.weightx = 1;
        directionUp = new Label("UP");
        directionUp.setFont(new Font("Helvetica", Font.BOLD, 45));
        positionPanel.add(directionUp, c);

        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 2;
        c.weighty = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        positionPanel.add(new Panel(), c);

        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 3;
        c.anchor = GridBagConstraints.CENTER;
        c.weightx = 1;
        currentPosition = new Label("PO");
        currentPosition.setFont(new Font("Helvetica", Font.BOLD, 35));
        positionPanel.add(currentPosition, c);

        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 3;
        c.anchor = GridBagConstraints.CENTER;
        c.weightx = 1;
        floorSelection = new Choice();
        floorSelection.setSize(50, 75);
        floorSelection.addItemListener(e -> controller.setSelectedFloor(elevatorIndex, floorSelection.getSelectedIndex()));
        floorSelection.setFont(new Font("Helvetica", Font.BOLD, 20));
        positionPanel.add(floorSelection, c);

        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 4;
        c.weighty = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        positionPanel.add(new Panel(), c);

        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 5;
        c.anchor = GridBagConstraints.CENTER;
        c.weightx = 1;
        directionDown = new Label("DOWN");
        directionDown.setFont(new Font("Helvetica", Font.BOLD, 45));
        positionPanel.add(directionDown, c);

        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 6;
        c.weighty = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        positionPanel.add(new Panel(), c);


        //******************************************************************************************************
        // Floor panel for mode selection, to display on which floor UP and DOWN buttons are pressed and which
        // are pressed on the panel inside the elevator
        //******************************************************************************************************
        Panel floorPanel = new Panel();
        floorPanel.setLayout(new GridBagLayout());

        //Upper Panel for mode selection
        Panel modePanel = new Panel(new GridLayout());

        CheckboxGroup cbg = new CheckboxGroup();
        checkBoxAuto = new Checkbox("Aut", cbg, true);
        checkBoxAuto.setBounds(100, 100, 50, 50);
        checkBoxManual = new Checkbox("Man", cbg, false);
        checkBoxManual.setBounds(100, 150, 50, 50);

        checkBoxAuto.addItemListener(e -> {
            controller.setAutomaticMode(elevatorIndex, true);
            floorSelection.select(floorIndex);
        });
        checkBoxManual.addItemListener(e -> {
            controller.setAutomaticMode(elevatorIndex, false);
            floorSelection.select(floorIndex);
        });

        checkBoxAuto.setEnabled(false);
        checkBoxManual.setEnabled(false);

        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.weighty = 1;
        c.fill = GridBagConstraints.CENTER;
        Label labelMode = new Label("Mode:");
        modePanel.add(labelMode);

        modePanel.add(checkBoxAuto);
        modePanel.add(checkBoxManual);

        floorPanel.add(modePanel, c);
        floorPanel.setFont(new Font("Helvetica", Font.PLAIN, 20));

        // Second panel for pressed button information
        Panel callStopPanel = new Panel();
        callStopPanel.setLayout(new GridBagLayout());

        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.WEST;
        Label labelCallStop = new Label("Call/Stop pressed on floor: ");
        callStopPanel.add(labelCallStop, c);
        c.gridx = 0;
        c.gridy = 1;
        UpButtonsPressed = new Label("");
        callStopPanel.add(UpButtonsPressed, c);
        c.gridx = 0;
        c.gridy = 2;
        downButtonsPressed = new Label("");
        callStopPanel.add(downButtonsPressed, c);
        c.gridx = 0;
        c.gridy = 3;
        Label labelElevatorPanel = new Label("Pressed on Elevator panel:");
        callStopPanel.add(labelElevatorPanel, c);
        c.gridx = 0;
        c.gridy = 4;
        elevatorPanelButtonsPressed = new Label("");
        callStopPanel.add(elevatorPanelButtonsPressed, c);

        callStopPanel.setFont(new Font("Helvetica", Font.PLAIN, 20));

        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 1;
        c.weighty = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        floorPanel.add(callStopPanel, c);

        //Add all panels to Frame
        windowFrame.add(infoPanel);
        windowFrame.add(positionPanel);
        windowFrame.add(floorPanel);
    }

    /**
     * applicationStateChanged method is called periodically to update the current information to display
     *
     * @param applicationState The updated application state.
     */
    @Override
    public void applicationStateChanged(ApplicationState applicationState) {
        elevatorIndex = applicationState.getSelectedElevator();
        floorIndex = applicationState.getElevators().get(elevatorIndex).getCurrentFloor();

        if (applicationState.getNumberOfElevators() > 0) {
            if (infoPanel != null) { // wait until UI elements are available
                floorSelection.setSize(50, 75);
                checkBoxAuto.setEnabled(true);
                checkBoxManual.setEnabled(true);
                if (applicationState.getElevators().get(elevatorIndex).isAutomatic()) {
                    checkBoxAuto.setState(true);
                    checkBoxManual.setState(false);
                    floorSelection.setVisible(false);
                    currentPosition.setVisible(true);
                } else {
                    checkBoxAuto.setState(false);
                    checkBoxManual.setState(true);
                    floorSelection.setVisible(true);
                    currentPosition.setVisible(false);
                }
                if (applicationState.getNumberOfElevators() == 1) {
                    elevatorSelection.setVisible(false);
                } else {
                    elevatorSelection.setVisible(true);
                    if (elevatorSelection.getItemCount() == 0) {
                        for (int i = 0; i < applicationState.getNumberOfElevators(); i++) {
                            elevatorSelection.add(String.valueOf(i));
                        }
                    }
                }
                if (floorSelection.getItemCount() == 0) {
                    for (int i = 0; i < applicationState.getNumberOfFloors(); i++) {
                        floorSelection.add(String.valueOf(i));
                    }

                }

                //set all information
                doorStatus.setText(String.valueOf(applicationState.getElevators().get(elevatorIndex).getDoorStatus()));
                speed.setText(String.valueOf(applicationState.getElevators().get(elevatorIndex).getCurrentSpeed()));
                payload.setText(String.valueOf(applicationState.getElevators().get(elevatorIndex).getCurrentPassengerWeight()));
                target.setText(String.valueOf(applicationState.getElevators().get(elevatorIndex).getCurrentTarget()));
                currentPosition.setText(String.valueOf(applicationState.getElevators().get(elevatorIndex).getCurrentFloor()));
                UpButtonsPressed.setText("UP: " + String.valueOf(applicationState.getButtonUpPressed()));
                downButtonsPressed.setText("DOWN: " + String.valueOf(applicationState.getButtonDownPressed()));
                elevatorPanelButtonsPressed.setText("Floor: " + String.valueOf(applicationState.getElevators().get(elevatorIndex).getActiveFloorButtons()));

                //set sizes of elements
                elevatorSelection.setSize(50, 50);
                payload.setSize(100, 35);
                target.setSize(100, 35);
                currentPosition.setSize(100, 35);
                UpButtonsPressed.setSize(500, 35);
                downButtonsPressed.setSize(500, 35);
                elevatorPanelButtonsPressed.setSize(500, 35);

                //display elevator direction
                switch (applicationState.getElevators().get(elevatorIndex).getCommittedDirection()) {
                    case 0:
                        directionDown.setBackground(Color.lightGray);
                        directionUp.setBackground(Color.GREEN);
                        break;
                    case 1:
                        directionDown.setBackground(Color.GREEN);
                        directionUp.setBackground(Color.lightGray);
                        break;
                    case 2:
                        directionDown.setBackground(Color.lightGray);
                        directionUp.setBackground(Color.LIGHT_GRAY);
                        break;
                    default:
                }
            }
        }
    }
}
