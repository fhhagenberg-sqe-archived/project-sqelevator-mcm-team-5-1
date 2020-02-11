package at.fhhagenberg.sqelevator.model;

import sqelevator.IElevator;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class ApplicationModel extends EccModel {

    protected IElevator elevatorControl = null;

    public ApplicationModel() {
        super();
    }

    public void initApplication() {

        try {
            elevatorControl = (IElevator) Naming.lookup("rmi://localhost/ElevatorSim");

            // Get the static building information (queried once)
            applicationState.numberOfFloors = elevatorControl.getFloorNum();
            applicationState.numberOfElevators = elevatorControl.getElevatorNum();
            applicationState.floorHeight = elevatorControl.getFloorHeight();
        } catch (Exception e) {
            // TODO: Handle exception
            e.printStackTrace();
        }

        update();
    }

    /**
     * Updates all changing data. To be called regularly.
     * @throws RemoteException
     */
    public void update() {

        try {
            // Get lists of all the floor requests
            updateUpDownRequestLists(applicationState.numberOfFloors);

            ArrayList<Elevator> elevatorList = new ArrayList<>();
            // Get the dynamic information that has to be regularly updated
            for (int i = 0; i < applicationState.numberOfElevators; i++) {
                elevatorList.add(getElevatorData(i, applicationState.numberOfFloors));
            }
            applicationState.elevators = elevatorList;

            notifyObservers(applicationState);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateUpDownRequestLists(int numberOfFloors) throws RemoteException {

        ArrayList<Integer> buttonUpPressed = new ArrayList<>();
        ArrayList<Integer> buttonDownPressed = new ArrayList<>();

        // Floor numeration starts with 1
        for (int i = 1; i <= numberOfFloors; i++) {
            // For each floor, get if the up or down buttons have been pressed.
            boolean upButton = elevatorControl.getFloorButtonUp(i);
            boolean downButton = elevatorControl.getFloorButtonDown(i);

            if (upButton) {
                buttonUpPressed.add(i);
            }
            if (downButton) {
                buttonDownPressed.add(i);
            }
        }

        applicationState.buttonUpPressed = buttonUpPressed;
        applicationState.buttonDownPressed = buttonDownPressed;
    }

    public Elevator getElevatorData(int elevatorIndex, int numberOfFloors) throws RemoteException {

        Elevator result = new Elevator();
        result.committedDirection = elevatorControl.getCommittedDirection(elevatorIndex);
        result.currentAcceleration = elevatorControl.getElevatorAccel(elevatorIndex);
        result.doorStatus = elevatorControl.getElevatorDoorStatus(elevatorIndex);
        result.currentFloor = elevatorControl.getElevatorFloor(elevatorIndex);
        result.currentHeightOverGround = elevatorControl.getElevatorPosition(elevatorIndex);
        result.currentSpeed = elevatorControl.getElevatorSpeed(elevatorIndex);
        result.currentPassengerWeight = elevatorControl.getElevatorWeight(elevatorIndex);
        result.maxPassengerNumber = elevatorControl.getElevatorCapacity(elevatorIndex);

        // For each floor of the building, query if the button in the elevator was pressed.
        ArrayList<Integer> floorButtonsPressed = new ArrayList<>();
        // Floors start with floor 1
        for (int i = 1; i <= numberOfFloors; i++) {
            boolean floorButtonPressed = elevatorControl.getElevatorButton(elevatorIndex, i);
            if (floorButtonPressed) {
                floorButtonsPressed.add(i);
            }
        }

        result.activeFloorButtons = floorButtonsPressed;

        return result;
    }

}
