package at.fhhagenberg.sqelevator.model;

import java.util.ArrayList;

public class ApplicationState {

    public ApplicationState() {
        this.buttonUpPressed = new ArrayList<>();
        this.buttonDownPressed = new ArrayList<>();
        this.elevators = new ArrayList<>();
    }

    private int numberOfElevators = 0;

    private int selectedElevator = -1;

    private int numberOfFloors;

    private int floorHeight;

    private ArrayList<Integer> buttonUpPressed;

    private ArrayList<Integer> buttonDownPressed;

    private ArrayList<Elevator> elevators;

    // Generated

    public int getNumberOfElevators() {
        return numberOfElevators;
    }

    public void setNumberOfElevators(int numberOfElevators) {
        this.numberOfElevators = numberOfElevators;
    }

    public int getSelectedElevator() {
        return selectedElevator;
    }

    public void setSelectedElevator(int selectedElevator) {
        this.selectedElevator = selectedElevator;
    }

    public int getNumberOfFloors() {
        return numberOfFloors;
    }

    public void setNumberOfFloors(int numberOfFloors) {
        this.numberOfFloors = numberOfFloors;
    }

    public int getFloorHeight() {
        return floorHeight;
    }

    public void setFloorHeight(int floorHeight) {
        this.floorHeight = floorHeight;
    }

    public ArrayList<Integer> getButtonUpPressed() {
        return buttonUpPressed;
    }

    public void setButtonUpPressed(ArrayList<Integer> buttonUpPressed) {
        this.buttonUpPressed = buttonUpPressed;
    }

    public ArrayList<Integer> getButtonDownPressed() {
        return buttonDownPressed;
    }

    public void setButtonDownPressed(ArrayList<Integer> buttonDownPressed) {
        this.buttonDownPressed = buttonDownPressed;
    }

    public ArrayList<Elevator> getElevators() {
        return elevators;
    }

    public void setElevators(ArrayList<Elevator> elevators) {
        this.elevators = elevators;
    }
}
