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

            // Init with empty elevator objects for each elevator
            for (int i = 0; i < applicationState.numberOfElevators; i++) {
                applicationState.elevators.add(new Elevator());
            }
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

            // Get the dynamic information that has to be regularly updated
            for (int i = 0; i < applicationState.numberOfElevators; i++) {
                updateElevatorData(i, applicationState.numberOfFloors);
            }

            for (int i = 0; i < applicationState.numberOfElevators; i++) {
                autoOperateElevator(i);
            }
            notifyObservers(applicationState);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setManualElevatorTarget(int elevatorIndex, int target) throws RemoteException {

        Elevator elevator = applicationState.elevators.get(elevatorIndex);
        if (elevator.currentFloor > target) {
            elevatorControl.setCommittedDirection(elevatorIndex, IElevator.ELEVATOR_DIRECTION_DOWN);
        } else if (elevator.currentFloor < target) {
            elevatorControl.setCommittedDirection(elevatorIndex, IElevator.ELEVATOR_DIRECTION_UP);
        }
    }

    public void autoOperateElevator(int elevatorIndex) throws RemoteException {

        Elevator elevator = applicationState.elevators.get(elevatorIndex);

        switch (elevator.committedDirection) {
            case IElevator.ELEVATOR_DIRECTION_UP: {

                if (elevator.currentSpeed == 0 && elevator.doorStatus == IElevator.ELEVATOR_DOORS_OPEN) {
                    if (elevator.currentFloor < applicationState.numberOfFloors-1) {
                        // Not on top floor yet, go up
                        elevatorControl.setTarget(elevatorIndex, elevator.currentFloor+1);
                    } else {
                        // Top floor, set to uncommitted
                        elevatorControl.setCommittedDirection(elevatorIndex, IElevator.ELEVATOR_DIRECTION_UNCOMMITTED);
                    }
                }
                break;
            }
            case IElevator.ELEVATOR_DIRECTION_DOWN: {

                if (elevator.currentSpeed == 0 && elevator.doorStatus == IElevator.ELEVATOR_DOORS_OPEN) {
                    if (elevator.currentFloor > 0) {
                        // Not on bottom floor yet, go down
                        elevatorControl.setTarget(elevatorIndex, elevator.currentFloor-1);
                    } else {
                        // Bottom floor, set to uncommitted
                        elevatorControl.setCommittedDirection(elevatorIndex, IElevator.ELEVATOR_DIRECTION_UNCOMMITTED);
                    }
                }
                break;
            }
            case IElevator.ELEVATOR_DIRECTION_UNCOMMITTED: {
                if (elevator.currentFloor < applicationState.numberOfFloors-1) {
                    elevatorControl.setCommittedDirection(elevatorIndex, IElevator.ELEVATOR_DIRECTION_UP);
                } else {
                    elevatorControl.setCommittedDirection(elevatorIndex, IElevator.ELEVATOR_DIRECTION_DOWN);
                }
                break;
            }
            default: {
                break;
            }
        }
    }

    public void updateUpDownRequestLists(int numberOfFloors) throws RemoteException {

        ArrayList<Integer> buttonUpPressed = new ArrayList<>();
        ArrayList<Integer> buttonDownPressed = new ArrayList<>();

        for (int i = 0; i < numberOfFloors; i++) {
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

    public void updateElevatorData(int elevatorIndex, int numberOfFloors) throws RemoteException {

        Elevator result = applicationState.elevators.get(elevatorIndex);

        result.committedDirection = elevatorControl.getCommittedDirection(elevatorIndex);
        result.currentTarget = elevatorControl.getTarget(elevatorIndex);
        result.currentAcceleration = elevatorControl.getElevatorAccel(elevatorIndex);
        result.doorStatus = elevatorControl.getElevatorDoorStatus(elevatorIndex);
        result.currentFloor = elevatorControl.getElevatorFloor(elevatorIndex);
        result.currentHeightOverGround = elevatorControl.getElevatorPosition(elevatorIndex);
        result.currentSpeed = elevatorControl.getElevatorSpeed(elevatorIndex);
        result.currentPassengerWeight = elevatorControl.getElevatorWeight(elevatorIndex);
        result.maxPassengerNumber = elevatorControl.getElevatorCapacity(elevatorIndex);

        // For each floor of the building, query if the button in the elevator was pressed.
        ArrayList<Integer> floorButtonsPressed = new ArrayList<>();

        for (int i = 0; i < numberOfFloors; i++) {
            boolean floorButtonPressed = elevatorControl.getElevatorButton(elevatorIndex, i);
            if (floorButtonPressed) {
                floorButtonsPressed.add(i);
            }
        }

        result.activeFloorButtons = floorButtonsPressed;
    }

}
