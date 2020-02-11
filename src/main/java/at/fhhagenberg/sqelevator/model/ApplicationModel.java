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
            applicationState.setNumberOfFloors(elevatorControl.getFloorNum());
            applicationState.setNumberOfElevators(elevatorControl.getElevatorNum());
            applicationState.setFloorHeight(elevatorControl.getFloorHeight());

            // Init with empty elevator objects for each elevator
            for (int i = 0; i < applicationState.getNumberOfElevators(); i++) {
                applicationState.getElevators().add(new Elevator());
            }

            if (applicationState.getNumberOfElevators() > 0) {
                applicationState.setSelectedElevator(0);
            }

            update();
        } catch (Exception e) {
            // Do nothing here - this occurs if simulation isn't started yet.
        }
    }

    /**
     * Updates all changing data. To be called regularly.
     * @throws RemoteException
     */
    public void update() {

        try {
            // Get lists of all the floor requests
            updateUpDownRequestLists(applicationState.getNumberOfFloors());

            // Get the dynamic information that has to be regularly updated
            for (int i = 0; i < applicationState.getNumberOfElevators(); i++) {
                updateElevatorData(i, applicationState.getNumberOfFloors());
            }

            for (int i = 0; i < applicationState.getNumberOfElevators(); i++) {
                if (applicationState.getElevators().get(i).isAutomatic()) {
                    autoOperateElevator(i);
                } else {
                    manualOperationHelper(i);
                }
            }
            notifyObservers(applicationState);
        } catch (Exception e) {
            // This occurs when simulation isn't started yet - try to connect again
            initApplication();
        }
    }

    public void setSelectedElevator(int elevatorIndex) {
        if (elevatorIndex >= 0 && elevatorIndex < applicationState.getNumberOfElevators()) {
            applicationState.setSelectedElevator(elevatorIndex);
            notifyObservers(applicationState);
        }
    }

    public void setElevatorAutomaticMode(int elevatorIndex, boolean automatic) {
        if (elevatorIndex < 0 || elevatorIndex >= applicationState.getNumberOfElevators()) {
            // not a valid index
            return;
        }
        Elevator elevator = applicationState.getElevators().get(elevatorIndex);
        elevator.setAutomatic(automatic);
        notifyObservers(applicationState);
    }

    public void setManualElevatorTarget(int elevatorIndex, int target) throws RemoteException {

        if (applicationState.getElevators().get(elevatorIndex).isAutomatic()) {
            // Not possible to set manual target in automatic mode
            return;
        }

        Elevator elevator = applicationState.getElevators().get(elevatorIndex);

        if (elevator.getCurrentFloor() > target) {

            elevatorControl.setCommittedDirection(elevatorIndex, IElevator.ELEVATOR_DIRECTION_DOWN);
            elevatorControl.setTarget(elevatorIndex, target);

        } else if (elevator.getCurrentFloor() < target) {

            elevatorControl.setCommittedDirection(elevatorIndex, IElevator.ELEVATOR_DIRECTION_UP);
            elevatorControl.setTarget(elevatorIndex, target);
        }
    }

    public void manualOperationHelper(int elevatorIndex) throws RemoteException {
        Elevator elevator = applicationState.getElevators().get(elevatorIndex);

        if (elevator.getCurrentFloor() == elevator.getCurrentTarget() && elevator.getCurrentSpeed() == 0 && elevator.getDoorStatus() == IElevator.ELEVATOR_DOORS_OPEN) {
            elevatorControl.setCommittedDirection(elevatorIndex, IElevator.ELEVATOR_DIRECTION_UNCOMMITTED);
        }
    }

    public void autoOperateElevator(int elevatorIndex) throws RemoteException {

        Elevator elevator = applicationState.getElevators().get(elevatorIndex);

        switch (elevator.getCommittedDirection()) {
            case IElevator.ELEVATOR_DIRECTION_UP: {

                if (elevator.getCurrentSpeed() == 0 && elevator.getDoorStatus() == IElevator.ELEVATOR_DOORS_OPEN) {
                    if (elevator.getCurrentFloor() < applicationState.getNumberOfFloors() - 1) {
                        // Not on top floor yet, go up
                        elevatorControl.setTarget(elevatorIndex, elevator.getCurrentFloor() + 1);
                    } else {
                        // Top floor, set to uncommitted
                        elevatorControl.setCommittedDirection(elevatorIndex, IElevator.ELEVATOR_DIRECTION_UNCOMMITTED);
                    }
                }
                break;
            }
            case IElevator.ELEVATOR_DIRECTION_DOWN: {

                if (elevator.getCurrentSpeed() == 0 && elevator.getDoorStatus() == IElevator.ELEVATOR_DOORS_OPEN) {
                    if (elevator.getCurrentFloor() > 0) {
                        // Not on bottom floor yet, go down
                        elevatorControl.setTarget(elevatorIndex, elevator.getCurrentFloor() - 1);
                    } else {
                        // Bottom floor, set to uncommitted
                        elevatorControl.setCommittedDirection(elevatorIndex, IElevator.ELEVATOR_DIRECTION_UNCOMMITTED);
                    }
                }
                break;
            }
            case IElevator.ELEVATOR_DIRECTION_UNCOMMITTED: {
                if (elevator.getCurrentFloor() < applicationState.getNumberOfFloors() - 1) {
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

        applicationState.setButtonUpPressed(buttonUpPressed);
        applicationState.setButtonDownPressed(buttonDownPressed);
    }

    public void updateElevatorData(int elevatorIndex, int numberOfFloors) throws RemoteException {

        Elevator result = applicationState.getElevators().get(elevatorIndex);

        result.setCommittedDirection(elevatorControl.getCommittedDirection(elevatorIndex));
        result.setCurrentTarget(elevatorControl.getTarget(elevatorIndex));
        result.setCurrentAcceleration(elevatorControl.getElevatorAccel(elevatorIndex));
        result.setDoorStatus(elevatorControl.getElevatorDoorStatus(elevatorIndex));
        result.setCurrentFloor(elevatorControl.getElevatorFloor(elevatorIndex));
        result.setCurrentHeightOverGround(elevatorControl.getElevatorPosition(elevatorIndex));
        result.setCurrentSpeed(elevatorControl.getElevatorSpeed(elevatorIndex));
        result.setCurrentPassengerWeight(elevatorControl.getElevatorWeight(elevatorIndex));
        result.setMaxPassengerNumber(elevatorControl.getElevatorCapacity(elevatorIndex));

        // For each floor of the building, query if the button in the elevator was pressed.
        ArrayList<Integer> floorButtonsPressed = new ArrayList<>();

        for (int i = 0; i < numberOfFloors; i++) {
            boolean floorButtonPressed = elevatorControl.getElevatorButton(elevatorIndex, i);
            if (floorButtonPressed) {
                floorButtonsPressed.add(i);
            }
        }

        result.setActiveFloorButtons(floorButtonsPressed);
    }

}
