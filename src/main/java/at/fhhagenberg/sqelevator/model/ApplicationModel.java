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

            autoOperateElevator(0);
            notifyObservers(applicationState);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void autoOperateElevator(int elevatorIndex) throws RemoteException {

        Elevator elevator = applicationState.elevators.get(elevatorIndex);

        switch (elevator.committedDirection) {
            case IElevator.ELEVATOR_DIRECTION_UP: {

                if (elevator.currentFloor == elevator.currentTarget && elevator.currentSpeed == 0 && elevator.doorStatus == IElevator.ELEVATOR_DOORS_OPEN) {
                    int target = getNextFloorTargetUpwards(elevator);

                    if (elevator.currentTarget != target) {
                        elevatorControl.setTarget(elevatorIndex, target);
                    } else {
                        elevatorControl.setCommittedDirection(elevatorIndex, IElevator.ELEVATOR_DIRECTION_UNCOMMITTED);
                    }
                }
                break;
            }
            case IElevator.ELEVATOR_DIRECTION_DOWN: {

                if (elevator.currentFloor == elevator.currentTarget && elevator.currentSpeed == 0 && elevator.doorStatus == IElevator.ELEVATOR_DOORS_OPEN) {
                    int target = getNextFloorTargetDownwards(elevator);

                    if (elevator.currentTarget != target) {
                        elevatorControl.setTarget(elevatorIndex, target);
                    } else {
                        elevatorControl.setCommittedDirection(elevatorIndex, IElevator.ELEVATOR_DIRECTION_UNCOMMITTED);
                    }
                }
                break;
            }
            case IElevator.ELEVATOR_DIRECTION_UNCOMMITTED: {
                if (elevator.activeFloorButtons.size() > 0) {

                    int goingUp = getAmountHigherThan(elevator.activeFloorButtons, elevator.currentFloor);
                    int goingDown = getAmountLowerThan(elevator.activeFloorButtons, elevator.currentFloor);

                    if (goingUp >= goingDown) {
                        elevatorControl.setCommittedDirection(elevatorIndex, IElevator.ELEVATOR_DIRECTION_UP);
                    } else {
                        elevatorControl.setCommittedDirection(elevatorIndex, IElevator.ELEVATOR_DIRECTION_DOWN);
                    }
                } else if (applicationState.buttonDownPressed.size() > 0 || applicationState.buttonUpPressed.size() > 0) {

                    int goingUp = getAmountHigherThan(applicationState.buttonUpPressed, elevator.currentFloor) +
                            getAmountHigherThan(applicationState.buttonDownPressed, elevator.currentFloor);
                    int goingDown = getAmountLowerThan(applicationState.buttonDownPressed, elevator.currentFloor) +
                            getAmountLowerThan(applicationState.buttonUpPressed, elevator.currentFloor);

                    if (goingUp >= goingDown) {
                        elevatorControl.setCommittedDirection(elevatorIndex, IElevator.ELEVATOR_DIRECTION_UP);
                    } else {
                        elevatorControl.setCommittedDirection(elevatorIndex, IElevator.ELEVATOR_DIRECTION_DOWN);
                    }
                }
                break;
            }
            default: {
                break;
            }
        }
    }

    public int getNextFloorTargetDownwards(Elevator elevator) {
        int activeFloorButtonIndex = getNextIndexLowerThan(elevator.activeFloorButtons, elevator.currentFloor);
        int buttonDownIndex = getNextIndexLowerThan(applicationState.buttonDownPressed, elevator.currentFloor);

        int target;
        if (activeFloorButtonIndex < 0 && buttonDownIndex < 0) {

            // Check if someone is lower down who wants to go up
            int buttonUpIndex = getNextIndexHigherThan(applicationState.buttonUpPressed, elevator.currentFloor);
            if (buttonUpIndex < 0) {
                // Set direction to uncommitted, no need to go higher
                target = elevator.currentFloor;
            } else {
                target = applicationState.buttonUpPressed.get(buttonUpIndex);
            }
        } else if (activeFloorButtonIndex < 0) {
            return applicationState.buttonDownPressed.get(buttonDownIndex);
        } else if (buttonDownIndex < 0) {
            return elevator.activeFloorButtons.get(activeFloorButtonIndex);
        }
        else {
            int activeFloorButtonValue = elevator.activeFloorButtons.get(activeFloorButtonIndex);
            int buttonDownValue = applicationState.buttonDownPressed.get(buttonDownIndex);

            target = activeFloorButtonValue <= buttonDownValue ? activeFloorButtonValue : buttonDownValue;
        }
        return target;
    }

    public int getNextFloorTargetUpwards(Elevator elevator) {
        int activeFloorButtonIndex = getNextIndexHigherThan(elevator.activeFloorButtons, elevator.currentFloor);
        int buttonUpIndex = getNextIndexHigherThan(applicationState.buttonUpPressed, elevator.currentFloor);

        int target;
        if (activeFloorButtonIndex < 0 && buttonUpIndex < 0) {

            // Check if someone is higher up who wants to go down
            int buttonDownIndex = getNextIndexHigherThan(applicationState.buttonDownPressed, elevator.currentFloor);
            if (buttonDownIndex < 0) {
                // Set direction to uncommitted, no need to go higher
                target = elevator.currentFloor;
            } else {
                target = applicationState.buttonDownPressed.get(buttonDownIndex);
            }
        } else if (activeFloorButtonIndex < 0) {
            return applicationState.buttonUpPressed.get(buttonUpIndex);
        } else if (buttonUpIndex < 0) {
            return elevator.activeFloorButtons.get(activeFloorButtonIndex);
        } else {
            int activeFloorButtonValue = elevator.activeFloorButtons.get(activeFloorButtonIndex);
            int buttonUpValue = applicationState.buttonUpPressed.get(buttonUpIndex);

            target = activeFloorButtonValue <= buttonUpValue ? activeFloorButtonValue : buttonUpValue;
        }
        return target;
    }

    /** @noinspection Duplicates*/
    public int getAmountLowerThan(ArrayList<Integer> list, int value) {
        int lowerCount = 0;
        for (Integer number : list) {
            if (number < value) {
                lowerCount++;
            }
        }
        return lowerCount;
    }

    /** @noinspection Duplicates*/
    public int getAmountHigherThan(ArrayList<Integer> list, int value) {

        int higherCount = 0;
        for (Integer number : list) {
            if (number > value) {
                higherCount++;
            }
        }
        return higherCount;
    }

    public int getNextIndexHigherThan(ArrayList<Integer> list, int value) {

        int index = -1;
        int difference = -1;

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) > value) {

                int currentDifference = list.get(i) - value;
                if (difference < 0 || currentDifference < difference) {
                    difference = currentDifference;
                    index = i;
                }
            }
        }

        return index;
    }

    public int getNextIndexLowerThan(ArrayList<Integer> list, int value) {

        int index = -1;
        int difference = -1;

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) < value) {

                int currentDifference = value - list.get(i);
                if (difference < 0 || currentDifference < difference) {
                    difference = currentDifference;
                    index = i;
                }
            }
        }

        return index;
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
