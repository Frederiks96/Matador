package main;

import desktop_codebehind.Car;

public interface IControllerUI {
	
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
	
}
