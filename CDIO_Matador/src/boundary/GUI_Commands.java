package boundary;

import desktop_codebehind.Car;
import desktop_resources.GUI;

public class GUI_Commands implements Boundary_Interface {
	
	/**
	 * @inheritDoc
	 */
	public String getUserString(String message) {
		return GUI.getUserString(message);
	}
	
	/**
	 * @inheritDoc
	 */
	public void addPlayer(String name, int balance) {
		GUI.addPlayer(name, balance);
	}
	
	/**
	 * @inheritDoc
	 */
	public void addPlayer(String name, int balance, Car car) {
		GUI.addPlayer(name, balance, car);
	}

	/**
	 * @inheritDoc
	 */
	public String getUserButtonPressed(String message, String... buttons) {
		return GUI.getUserButtonPressed(message, buttons);
	}
	
	/**
	 * @inheritDoc
	 */
	public int getUserInteger(String message, int min, int max) {
		return GUI.getUserInteger(message, min, max);
	}
	
	/**
	 * @inheritDoc
	 */
	public int getUserInteger(String message) {
		return GUI.getUserInteger(message);
	}
	
	/**
	 * @inheritDoc
	 */
	public String getUserSelection(String message, String... options) {
		return GUI.getUserSelection(message, options);
	}

	/**
	 * @inheritDoc
	 */
	public boolean getUserLeftButtonPressed(String message, String trueButton, String falseButton) {
		return GUI.getUserLeftButtonPressed(message, trueButton, falseButton);
	}

	/**
	 * @inheritDoc
	 */
	public void showMessage(String message) {
		GUI.showMessage(message);
	}

	/**
	 * @inheritDoc
	 */
	public void closeGUI() {
		GUI.close();
	}

	/**
	 * @inheritDoc
	 */
	public void setCar(int fieldNumber, String name) {
		GUI.setCar(fieldNumber+1, name);
	}

	/**
	 * @inheritDoc
	 */
	public void setOwner(int id, String name){
		GUI.setOwner(id+1, name);
	}

	/**
	 * @inheritDoc
	 */
	public void setHouse(int fieldID, int houseCount) {
		GUI.setHouses(fieldID+1, houseCount);
	}

	/**
	 * @inheritDoc
	 */
	public void setHotel(int fieldID, boolean hasHotel) {
		GUI.setHotel(fieldID+1, hasHotel);	
	}
	
	/**
	 * @inheritDoc
	 */
	public void setDice(int faceValue1, int faceValue2) {
		GUI.setDice(faceValue1, faceValue2);
	}

	/**
	 * @inheritDoc
	 */
	public void removeCar(int position, String name) {
		GUI.removeCar(position+1, name);
	}

	/**
	 * @inheritDoc
	 */
	public void setBalance(String name, int balance) {
		GUI.setBalance(name, balance);
	}

	/**
	 * @inheritDoc
	 */
	public void removeOwner(int field_id) {
		GUI.removeOwner(field_id+1);
	}
	
	/**
	 * @inheritDoc
	 */
	public void removeAllCars(String name) {
		GUI.removeAllCars(name);
	}

}
