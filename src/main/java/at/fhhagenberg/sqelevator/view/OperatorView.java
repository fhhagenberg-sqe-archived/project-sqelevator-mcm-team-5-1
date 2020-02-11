package at.fhhagenberg.sqelevator.view;

import at.fhhagenberg.sqelevator.controller.EccController;
import at.fhhagenberg.sqelevator.model.ApplicationState;

import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

public class OperatorView extends EccView {

    public OperatorView(EccController controller, int width, int height) {
        super(controller, width, height);
    }

    public int numOfFloors;
    private Label payload;
    private Label speed;
    private Label doorStatus;
    private Label target;
    private Label up;
    private Label down;
    private Label position;
    private TextField inputPosition;
    private Choice floorSelect;
    Panel callStopPanel;

    private Panel floorPanel;


    @Override
    public void addComponents(Frame windowFrame) {
        windowFrame.setLayout(new GridLayout(1, 3));
        windowFrame.setBackground(Color.lightGray);

        Panel infoPanel = new Panel();
        infoPanel.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.WEST;
        c.weightx = 1;
        Label labelPayload = new Label("Payload:");
        infoPanel.add(labelPayload, c);

        c.gridy = 1;
        c.anchor = GridBagConstraints.CENTER;
        payload = new Label("_____");
        infoPanel.add(payload, c);

        c.gridy = 2;
        c.anchor = GridBagConstraints.WEST;
        Label labelSpeed = new Label("Speed:");
        infoPanel.add(labelSpeed, c);

        c.gridy = 3;
        c.anchor = GridBagConstraints.CENTER;
        speed = new Label("_____");
        infoPanel.add(speed, c);

        c.gridy = 4;
        c.anchor = GridBagConstraints.WEST;
        Label labelDoorStatus = new Label("Door Status:");
        infoPanel.add(labelDoorStatus, c);

        c.gridy = 5;
        c.anchor = GridBagConstraints.CENTER;
        doorStatus = new Label("_____");
        infoPanel.add(doorStatus, c);

        c.gridy = 6;
        c.anchor = GridBagConstraints.WEST;
        Label labelTarget = new Label("Target:");
        infoPanel.add(labelTarget, c);

        c.gridy = 7;
        c.anchor = GridBagConstraints.CENTER;
        target = new Label("_____");
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
        floorSelect.setSize(100, 75);
        floorSelect.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                System.out.println(floorSelect.getSelectedIndex());
            }
        });
        floorSelect.setFont(new Font("Helvetica", Font.BOLD, 20));
        positionPanel.add(floorSelect, c);
        floorSelect.setVisible(false);


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

        floorPanel = new Panel();
        floorPanel.setLayout(new GridBagLayout());

        Panel modePanel = new Panel(new GridLayout());

        CheckboxGroup cbg = new CheckboxGroup();
        Checkbox checkBox1 = new Checkbox("Aut", cbg, true);
        checkBox1.setBounds(100, 100, 50, 50);
        Checkbox checkBox2 = new Checkbox("Man", cbg, false);
        checkBox2.setBounds(100, 150, 50, 50);

        checkBox1.addItemListener(e -> {
            floorSelect.setVisible(false);
            position.setVisible(true);
        });
        checkBox2.addItemListener(e -> {
            position.setVisible(false);
            floorSelect.setVisible(true);
        });

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

        callStopPanel = new Panel();
        callStopPanel.setLayout(new GridBagLayout());

        c = new GridBagConstraints();
        int gridWeight = 0;

        for (int i = numOfFloors; i >= 1; i--) {
            c.gridy = gridWeight;
            callStopPanel.add(new Label(), c);

        }

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
        System.out.println(applicationState.numberOfElevators);

        if (applicationState.numberOfElevators > 0) {
            numOfFloors = applicationState.numberOfFloors;
            ArrayList buttonDownList = applicationState.buttonDownPressed;
            ArrayList buttonUpList = applicationState.buttonUpPressed;

            if (floorSelect != null) {
                if (floorSelect.getItemCount() == 0) {
                    numOfFloors = applicationState.numberOfFloors;
                    for (int i = 1; i <= numOfFloors; i++) {
                        floorSelect.add(String.valueOf(i));
                    }
                }
                doorStatus.setText(String.valueOf(applicationState.elevators.get(0).doorStatus));
                speed.setText(String.valueOf(applicationState.elevators.get(0).currentSpeed));
                payload.setText(String.valueOf(applicationState.elevators.get(0).currentPassengerWeight));
                target.setText(String.valueOf(applicationState.elevators.get(0).committedDirection));
                position.setText(String.valueOf(applicationState.elevators.get(0).currentFloor));

                GridBagConstraints c = new GridBagConstraints();
                int gridWeight = 0;

                for (int i = numOfFloors; i >= 1; i--) {
                    c.gridx = 0;
                    c.gridy = gridWeight;
                    callStopPanel.add(new Label(String.valueOf(i)), c);
                    c.gridx = 1;
                    c.gridy = gridWeight;
                    if(buttonUpList.contains(i-1)){
                        callStopPanel.add(new Label("U:X"), c);
                    }else {
                        callStopPanel.add(new Label("U: "), c);
                    }
                    c.gridx = 2;
                    c.gridy = gridWeight;
                    if(buttonDownList.contains(i-1)){
                        callStopPanel.add(new Label("D:X"), c);
                    }else {
                        callStopPanel.add(new Label("D: "), c);
                    }
//                    c.gridx = 3;
//                    c.gridy = gridWeight;
//                    callStopPanel.add(new Label("P: "), c);

                    gridWeight++;
                }
            }
            System.out.println(applicationState.elevators.get(0).toString());
        }
    }
}
