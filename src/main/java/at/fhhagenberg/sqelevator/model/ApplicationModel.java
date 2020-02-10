package at.fhhagenberg.sqelevator.model;

import sqelevator.IElevator;

import java.rmi.Naming;
import java.rmi.RemoteException;

public class ApplicationModel extends EccModel {

    protected IElevator elevatorControl = null;

    public ApplicationModel() {
        super();
    }

    public void initApplication() {

        try {
            elevatorControl = (IElevator) Naming.lookup("rmi://localhost/ElevatorSim");

            // Get the number of elevators in the building
            applicationState.numberOfElevators = elevatorControl.getElevatorNum();
            for (int i = 0; i < applicationState.numberOfElevators; i++) {
                applicationState.elevators.add(getElevatorData(i));
            }

            notifyObservers(applicationState);
        } catch (Exception e) {
            // TODO: Handle exception
            e.printStackTrace();
        }
    }

    public Elevator getElevatorData(int elevatorIndex) throws RemoteException {

        Elevator result = new Elevator();
        result.committedDirection = elevatorControl.getCommittedDirection(elevatorIndex);
        result.currentAcceleration = elevatorControl.getElevatorAccel(elevatorIndex);
        result.doorStatus = elevatorControl.getElevatorDoorStatus(elevatorIndex);
        result.currentFloor = elevatorControl.getElevatorFloor(elevatorIndex);
        result.currentHeightOverGround = elevatorControl.getElevatorPosition(elevatorIndex);
        result.currentSpeed = elevatorControl.getElevatorSpeed(elevatorIndex);
        result.currentPassengerWeight = elevatorControl.getElevatorWeight(elevatorIndex);
        result.maxPassengerNumber = elevatorControl.getElevatorCapacity(elevatorIndex);
        return result;
    }

}
