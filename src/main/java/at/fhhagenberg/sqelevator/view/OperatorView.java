package at.fhhagenberg.sqelevator.view;

import at.fhhagenberg.sqelevator.controller.EccController;
import at.fhhagenberg.sqelevator.model.ApplicationState;

import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class OperatorView extends EccView {

    public OperatorView(EccController controller, int width, int height) {
        super(controller, width, height);
    }

    private Label payload;
    private Label speed;
    private Label doorStatus;
    private Label target;
    private Label up;
    private Label down;
    private Label position;
    private Choice floorSelect;
    private Choice selectedElevator;
    private Label buttonDownPressed;
    private Label buttonUpPressed;
    private Label elevatorPanelButtonsPressed;
    private Panel infoPanel;
    private Checkbox checkBox1;
    private Checkbox checkBox2;
    private int elevatorIndex;
    private int floorIndex;

    @Override
    public void addComponents(Frame windowFrame) {
        windowFrame.setLayout(new GridLayout(1, 3));
        windowFrame.setBackground(Color.lightGray);

        infoPanel = new Panel();
        infoPanel.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.WEST;
        c.weightx = 1;
        selectedElevator = new Choice();
        selectedElevator.setSize(50, 75);
        selectedElevator.addItemListener(e -> {
            controller.setSelectedElevator(selectedElevator.getSelectedIndex());
            floorSelect.select(floorIndex);
        });
        infoPanel.add(selectedElevator, c);
        selectedElevator.setVisible(false);

        c.gridx = 0;
        c.gridy = 1;
        c.anchor = GridBagConstraints.WEST;
        c.weightx = 1;
        Label labelPayload = new Label("Payload:");
        infoPanel.add(labelPayload, c);

        c.gridy = 2;
        c.anchor = GridBagConstraints.CENTER;
        payload = new Label("____");
//        payload.setSize(100, 50);
        infoPanel.add(payload, c);

        c.gridy = 3;
        c.anchor = GridBagConstraints.WEST;
        Label labelSpeed = new Label("Speed:");
        infoPanel.add(labelSpeed, c);

        c.gridy = 4;
        c.anchor = GridBagConstraints.CENTER;
        speed = new Label("__");
//        speed.setSize(100, 50);
        infoPanel.add(speed, c);

        c.gridy = 5;
        c.anchor = GridBagConstraints.WEST;
        Label labelDoorStatus = new Label("Door Status:");
        infoPanel.add(labelDoorStatus, c);

        c.gridy = 6;
        c.anchor = GridBagConstraints.CENTER;
        doorStatus = new Label("__");
//        doorStatus.setSize(100, 50);
        infoPanel.add(doorStatus, c);

        c.gridy = 7;
        c.anchor = GridBagConstraints.WEST;
        Label labelTarget = new Label("Target:");
        infoPanel.add(labelTarget, c);

        c.gridy = 8;
        c.anchor = GridBagConstraints.CENTER;
        target = new Label("__");
//        target.setSize(100, 50);
        infoPanel.add(target, c);

        Font myFont = new Font("Helvetica", Font.PLAIN, 30);
        infoPanel.setFont(myFont);

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
        up = new Label("UP");
        up.setFont(new Font("Helvetica", Font.BOLD, 45));
        positionPanel.add(up, c);

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
        position = new Label("PO");
        position.setFont(new Font("Helvetica", Font.BOLD, 35));
        positionPanel.add(position, c);

        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 3;
        c.anchor = GridBagConstraints.CENTER;
        c.weightx = 1;
        floorSelect = new Choice();
        floorSelect.setSize(50, 75);
        floorSelect.addItemListener(e -> controller.setSelectedFloor(elevatorIndex, floorSelect.getSelectedIndex()));
        floorSelect.setFont(new Font("Helvetica", Font.BOLD, 20));
        positionPanel.add(floorSelect, c);

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
        down = new Label("DOWN");
        down.setFont(new Font("Helvetica", Font.BOLD, 45));
        positionPanel.add(down, c);

        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 6;
        c.weighty = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        positionPanel.add(new Panel(), c);

        Panel floorPanel = new Panel();
        floorPanel.setLayout(new GridBagLayout());

        Panel modePanel = new Panel(new GridLayout());

        CheckboxGroup cbg = new CheckboxGroup();
        checkBox1 = new Checkbox("Aut", cbg, true);
        checkBox1.setBounds(100, 100, 50, 50);
        checkBox2 = new Checkbox("Man", cbg, false);
        checkBox2.setBounds(100, 150, 50, 50);

        checkBox1.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                controller.setAutomaticMode(elevatorIndex, true);
                floorSelect.select(floorIndex);
            }
        });
        checkBox2.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                controller.setAutomaticMode(elevatorIndex, false);
                floorSelect.select(floorIndex);
            }
        });

        checkBox1.setEnabled(false);
        checkBox2.setEnabled(false);

        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.weighty = 1;
        c.fill = GridBagConstraints.CENTER;
        Label labelMode = new Label("Mode:");
        modePanel.add(labelMode);

        modePanel.add(checkBox1);
        modePanel.add(checkBox2);

        floorPanel.add(modePanel, c);
        floorPanel.setFont(new Font("Helvetica", Font.PLAIN, 20));

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
        buttonUpPressed = new Label("");
        callStopPanel.add(buttonUpPressed, c);
        c.gridx = 0;
        c.gridy = 2;
        buttonDownPressed = new Label("");
        callStopPanel.add(buttonDownPressed, c);
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

        windowFrame.add(infoPanel);
        windowFrame.add(positionPanel);
        windowFrame.add(floorPanel);
    }

    @Override
    public void applicationStateChanged(ApplicationState applicationState) {
//        System.out.println(applicationState.numberOfElevators);
        elevatorIndex = applicationState.selectedElevator;
        floorIndex = applicationState.elevators.get(elevatorIndex).currentFloor;


        if (applicationState.numberOfElevators > 0) {
            if (infoPanel != null) {
                floorSelect.setSize(50, 75);
                checkBox1.setEnabled(true);
                checkBox2.setEnabled(true);
                if (applicationState.elevators.get(elevatorIndex).automatic) {
                    checkBox1.setState(true);
                    checkBox2.setState(false);
                    floorSelect.setVisible(false);
                    position.setVisible(true);
                } else {
                    checkBox1.setState(false);
                    checkBox2.setState(true);
                    floorSelect.setVisible(true);
                    position.setVisible(false);
                }
                if (applicationState.numberOfElevators == 1) {
                    selectedElevator.setVisible(false);
                } else {
                    selectedElevator.setVisible(true);
                    if (selectedElevator.getItemCount() == 0) {
                        for (int i = 0; i < applicationState.numberOfElevators; i++) {
                            selectedElevator.add(String.valueOf(i));
                        }
                    }
                }
                if (floorSelect.getItemCount() == 0) {
                    for (int i = 0; i < applicationState.numberOfFloors; i++) {
                        floorSelect.add(String.valueOf(i));
                    }

                }

                selectedElevator.setSize(50, 50);

                doorStatus.setText(String.valueOf(applicationState.elevators.get(elevatorIndex).doorStatus));
                speed.setText(String.valueOf(applicationState.elevators.get(elevatorIndex).currentSpeed));
                payload.setText(String.valueOf(applicationState.elevators.get(elevatorIndex).currentPassengerWeight));
                payload.setSize(100, 35);
                target.setText(String.valueOf(applicationState.elevators.get(elevatorIndex).currentTarget));
                target.setSize(100, 35);
                position.setText(String.valueOf(applicationState.elevators.get(elevatorIndex).currentFloor));
                position.setSize(100, 35);
                buttonUpPressed.setText("UP: " + String.valueOf(applicationState.buttonUpPressed));
                buttonUpPressed.setSize(500, 35);
                buttonDownPressed.setText("DOWN: " + String.valueOf(applicationState.buttonDownPressed));
                buttonDownPressed.setSize(500, 35);
                elevatorPanelButtonsPressed.setText("Floor: " + String.valueOf(applicationState.elevators.get(elevatorIndex).activeFloorButtons));
                elevatorPanelButtonsPressed.setSize(500, 35);
            }
//            System.out.println(applicationState.elevators.get(elevatorIndex).toString());
        }else {
            selectedElevator.setVisible(false);
        }
    }
}
