package boundary;

import desktop_codebehind.Car;

public interface Boundary_Interface {
	
	// User interference
	
	String getUserString(String message);
	String getUserButtonPressed(String message, String... buttons);
	int getUserInteger(String message, int min, int max); // Kan udelades
	int getUserInteger(String message);
	String getUserSelection(String message, String... options);
	boolean getUserLeftButtonPressed(String message, String trueButton, String falseButton);
	
	// GUI relateret
	void addPlayer(String name, int balance);
	void addPlayer(String name, int balance, Car car);
	void showMessage(String message);
	void closeGUI();
	void setCar(int fieldNumber, String name);
	void setHouse(int fieldID, int houseCount);
	void setHotel(int fieldID, boolean hasHotel);
	void setOwner(int id, String name);
	void setDice(int faceValue1, int faceValue2);
	

}
