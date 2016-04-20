package main;

import desktop_codebehind.Car;
import desktop_resources.GUI;

public class ControllerGUI implements IControllerUI {
	
	private Player[] players;
	
	
		
	public String getUserString(String message) {
		return GUI.getUserString(message);
	}
	
	public void addPlayer(String name, int balance) {
		GUI.addPlayer(name, balance);
	}
	
	public void addPlayer(String name, int balance, Car car) {
		GUI.addPlayer(name, balance, car);
	}

	public String getUserButtonPressed(String message, String... buttons) {
		return GUI.getUserButtonPressed(message, buttons);
	}

	public int getUserInteger(String message, int min, int max) {
		return GUI.getUserInteger(message, min, max);
	}

	public int getUserInteger(String message) {
		return GUI.getUserInteger(message);
	}

	public String getUserSelection(String message, String... options) {
		return GUI.getUserSelection(message, options);
	}

	public boolean getUserLeftButtonPressed(String message, String trueButton, String falseButton) {
		return GUI.getUserLeftButtonPressed(message, trueButton, falseButton);
	}

	public void showMessage(String message) {
		GUI.showMessage(message);
	}

	public void closeGUI() {
		GUI.close();
	}

	public void setCar(int fieldNumber, String name) {
		GUI.setCar(fieldNumber, name);
	}

	
	public void setOwner(int id, String name){
		GUI.setOwner(id, name);
	}

	
	public void setHouse(int fieldID, int houseCount) {
		GUI.setHouses(fieldID, houseCount);
	}

	public void setHotel(int fieldID, boolean hasHotel) {
		GUI.setHotel(fieldID, hasHotel);	
	}

	

}
