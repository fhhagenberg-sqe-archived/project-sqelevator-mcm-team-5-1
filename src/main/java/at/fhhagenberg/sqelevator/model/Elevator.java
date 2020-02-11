package at.fhhagenberg.sqelevator.model;

import java.util.ArrayList;

public class Elevator {

    public boolean automatic = true;

    public int committedDirection;

    public int currentTarget;

    public int currentAcceleration;

    public int doorStatus;

    public int currentFloor;

    public int currentHeightOverGround;

    public int currentSpeed;

    public int currentPassengerWeight;

    public int maxPassengerNumber;

    public ArrayList<Integer> activeFloorButtons = new ArrayList<>();

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
}
