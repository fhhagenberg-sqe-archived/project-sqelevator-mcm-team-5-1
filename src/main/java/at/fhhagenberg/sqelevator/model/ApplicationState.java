package at.fhhagenberg.sqelevator.model;

import java.util.ArrayList;

public class ApplicationState {

    public int numberOfElevators = 0;

    public int selectedElevator = -1;

    public int numberOfFloors;

    public int floorHeight;

    public ArrayList<Integer> buttonUpPressed = new ArrayList<>();

    public ArrayList<Integer> buttonDownPressed = new ArrayList<>();

    public ArrayList<Elevator> elevators = new ArrayList<>();
}
