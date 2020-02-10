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

        windowFrame.setLayout(new GridLayout(1, 3));
        ;
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
        Label payload = new Label("__");
        infoPanel.add(payload, c);

        c.gridy = 2;
        c.anchor = GridBagConstraints.WEST;
        Label labelSpeed = new Label("Speed:");
        infoPanel.add(labelSpeed, c);

        c.gridy = 3;
        c.anchor = GridBagConstraints.CENTER;
        Label speed = new Label("__");
        infoPanel.add(speed, c);

        c.gridy = 4;
        c.anchor = GridBagConstraints.WEST;
        Label labelDoorStatus = new Label("Door Status:");
        infoPanel.add(labelDoorStatus, c);

        c.gridy = 5;
        c.anchor = GridBagConstraints.CENTER;
        Label doorStatus = new Label("__");
        infoPanel.add(doorStatus, c);

        c.gridy = 6;
        c.anchor = GridBagConstraints.WEST;
        Label labelTarget = new Label("Target:");
        infoPanel.add(labelTarget, c);

        c.gridy = 7;
        c.anchor = GridBagConstraints.CENTER;
        Label target = new Label("__");
        infoPanel.add(target, c);

        Font myFont = new Font("Helvetica", Font.PLAIN, 30);
        labelPayload.setFont(myFont);
        labelSpeed.setFont(myFont);
        labelDoorStatus.setFont(myFont);
        labelTarget.setFont(myFont);

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
        Label up = new Label("UP");
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
        Label position = new Label("POS");
        position.setFont(new Font("Helvetica", Font.BOLD, 35));
        positionPanel.add(position, c);

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
        Label down = new Label("DOWN");
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

        CheckboxGroup cbg = new CheckboxGroup();
        Checkbox checkBox1 = new Checkbox("Automatic", cbg, true);
        checkBox1.setBounds(100, 100, 50, 50);
        Checkbox checkBox2 = new Checkbox("Manual", cbg, false);
        checkBox2.setBounds(100, 150, 50, 50);

        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.weighty = 1;
        c.fill = GridBagConstraints.EAST;
        Label labelMode = new Label("Mode:");
        floorPanel.add(labelMode);

        floorPanel.add(checkBox1);
        floorPanel.add(checkBox2);

        floorPanel.setFont(new Font("Helvetica", Font.PLAIN, 20));

        Panel callStopPanel = new Panel();
        callStopPanel.setLayout(new GridBagLayout());

        Label testFloor10 = new Label("10");
        Label testFloor9 = new Label("9");
        Label testFloor8 = new Label("8");
        Label testFloor7 = new Label("7");
        Label testFloor6 = new Label("6");
        Label testFloor5 = new Label("5");
        Label testFloor4 = new Label("4");
        Label testFloor3 = new Label("3");
        Label testFloor2 = new Label("2");
        Label testFloor1 = new Label("1");

        Checkbox labelFloor10 = new Checkbox("", false);
        Checkbox labelFloor9 = new Checkbox("", false);
        Checkbox labelFloor8 = new Checkbox("", false);
        Checkbox labelFloor7 = new Checkbox("", false);
        Checkbox labelFloor6 = new Checkbox("", false);
        Checkbox labelFloor5 = new Checkbox("", false);
        Checkbox labelFloor4 = new Checkbox("", false);
        Checkbox labelFloor3 = new Checkbox("", false);
        Checkbox labelFloor2 = new Checkbox("", false);
        Checkbox labelFloor1 = new Checkbox("", false);

        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.weighty = 1;
        c.fill = GridBagConstraints.EAST;
        callStopPanel.add(testFloor10, c);
        c.gridx = 1;
        c.gridy = 0;
        callStopPanel.add(labelFloor10, c);
        c.gridx = 0;
        c.gridy = 1;
        callStopPanel.add(testFloor9, c);
        c.gridx = 1;
        c.gridy = 1;
        callStopPanel.add(labelFloor9, c);
        c.gridx = 0;
        c.gridy = 2;
        callStopPanel.add(testFloor8, c);
        c.gridx = 1;
        c.gridy = 2;
        callStopPanel.add(labelFloor8, c);
        c.gridx = 0;
        c.gridy = 3;
        callStopPanel.add(testFloor7, c);
        c.gridx = 1;
        c.gridy = 3;
        callStopPanel.add(labelFloor7, c);
        c.gridx = 0;
        c.gridy = 4;
        callStopPanel.add(testFloor6, c);
        c.gridx = 1;
        c.gridy = 4;
        callStopPanel.add(labelFloor6, c);
        c.gridx = 0;
        c.gridy = 5;
        callStopPanel.add(testFloor5, c);
        c.gridx = 1;
        c.gridy = 5;
        callStopPanel.add(labelFloor5, c);
        c.gridx = 0;
        c.gridy = 6;
        callStopPanel.add(testFloor4, c);
        c.gridx = 1;
        c.gridy = 6;
        callStopPanel.add(labelFloor4, c);
        c.gridx = 0;
        c.gridy = 7;
        callStopPanel.add(testFloor3, c);
        c.gridx = 1;
        c.gridy = 7;
        callStopPanel.add(labelFloor3, c);
        c.gridx = 0;
        c.gridy = 8;
        callStopPanel.add(testFloor2, c);
        c.gridx = 1;
        c.gridy = 8;
        callStopPanel.add(labelFloor2, c);
        c.gridx = 0;
        c.gridy = 9;
        callStopPanel.add(testFloor1, c);
        c.gridx = 1;
        c.gridy = 9;
        callStopPanel.add(labelFloor1, c);

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
            System.out.println(applicationState.elevators.get(0).toString());
        }
    }
}
