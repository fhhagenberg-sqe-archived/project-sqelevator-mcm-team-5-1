package at.fhhagenberg.sqelevator.model;

import java.util.ArrayList;

/**
 * Class that represents the state of one elevator within the managed building.
 */
public class Elevator {

    /**
     * Default constructor initializing some fields with default values.
     */
    public Elevator() {
        this.automatic = true;
        this.activeFloorButtons = new ArrayList<>();
    }

    private boolean automatic;

    private int committedDirection;

    private int currentTarget;

    private int currentAcceleration;

    private int doorStatus;

    private int currentFloor;

    private int currentHeightOverGround;

    private int currentSpeed;

    private int currentPassengerWeight;

    private int maxPassengerNumber;

    private ArrayList<Integer> activeFloorButtons;

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Committed Direction: ").append(committedDirection).append("\n");
        builder.append("Current Target: ").append(currentTarget).append("\n");
        builder.append("Current Acceleration: ").append(currentAcceleration).append("\n");
        builder.append("Door Status: ").append(doorStatus).append("\n");
        builder.append("Current Floor: ").append(currentFloor).append("\n");
        builder.append("Current Height: ").append(currentHeightOverGround).append("\n");
        builder.append("Current Speed: ").append(currentSpeed).append("\n");
        builder.append("Current Weight: ").append(currentPassengerWeight).append("\n");
        builder.append("Max Passengers: ").append(maxPassengerNumber).append("\n");
        builder.append("Elevator Buttons Pressed: ");

        for (Integer button : activeFloorButtons) {
            builder.append(button).append(", ");
        }

        return builder.toString();
    }

    // Generated code

    public boolean isAutomatic() {
        return automatic;
    }

    public void setAutomatic(boolean automatic) {
        this.automatic = automatic;
    }

    public int getCommittedDirection() {
        return committedDirection;
    }

    public void setCommittedDirection(int committedDirection) {
        this.committedDirection = committedDirection;
    }

    public int getCurrentTarget() {
        return currentTarget;
    }

    public void setCurrentTarget(int currentTarget) {
        this.currentTarget = currentTarget;
    }

    public int getCurrentAcceleration() {
        return currentAcceleration;
    }

    public void setCurrentAcceleration(int currentAcceleration) {
        this.currentAcceleration = currentAcceleration;
    }

    public int getDoorStatus() {
        return doorStatus;
    }

    public void setDoorStatus(int doorStatus) {
        this.doorStatus = doorStatus;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public void setCurrentFloor(int currentFloor) {
        this.currentFloor = currentFloor;
    }

    public int getCurrentHeightOverGround() {
        return currentHeightOverGround;
    }

    public void setCurrentHeightOverGround(int currentHeightOverGround) {
        this.currentHeightOverGround = currentHeightOverGround;
    }

    public int getCurrentSpeed() {
        return currentSpeed;
    }

    public void setCurrentSpeed(int currentSpeed) {
        this.currentSpeed = currentSpeed;
    }

    public int getCurrentPassengerWeight() {
        return currentPassengerWeight;
    }

    public void setCurrentPassengerWeight(int currentPassengerWeight) {
        this.currentPassengerWeight = currentPassengerWeight;
    }

    public int getMaxPassengerNumber() {
        return maxPassengerNumber;
    }

    public void setMaxPassengerNumber(int maxPassengerNumber) {
        this.maxPassengerNumber = maxPassengerNumber;
    }

    public ArrayList<Integer> getActiveFloorButtons() {
        return activeFloorButtons;
    }

    public void setActiveFloorButtons(ArrayList<Integer> activeFloorButtons) {
        this.activeFloorButtons = activeFloorButtons;
    }
}
