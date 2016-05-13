package boundary;

import desktop_codebehind.Car;
import desktop_resources.GUI;

public class GUI_Commands implements Boundary_Interface {
	
	
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
		GUI.setCar(fieldNumber+1, name);
	}

	
	public void setOwner(int id, String name){
		GUI.setOwner(id+1, name);
	}

	
	public void setHouse(int fieldID, int houseCount) {
		GUI.setHouses(fieldID+1, houseCount);
	}

	
	public void setHotel(int fieldID, boolean hasHotel) {
		GUI.setHotel(fieldID+1, hasHotel);	
	}
	
	
	public void setDice(int faceValue1, int faceValue2) {
		GUI.setDice(faceValue1, faceValue2);
	}

	
	public void removeCar(int position, String name) {
		GUI.removeCar(position+1, name);
	}

	
	public void setBalance(String name, int balance) {
		GUI.setBalance(name, balance);
	}

	
	public void removeOwner(int field_id) {
		GUI.removeOwner(field_id+1);
	}
	
	
	public void removeAllCars(String name) {
		GUI.removeAllCars(name);
	}

}
