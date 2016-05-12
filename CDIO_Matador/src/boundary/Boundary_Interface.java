package boundary;

import desktop_codebehind.Car;

public interface Boundary_Interface {
	
	// User interference
	/**
	 * 
	 * @param message
	 * @return
	 */
	String getUserString(String message);
	/**
	 * 
	 * @param message
	 * @param buttons
	 * @return
	 */
	String getUserButtonPressed(String message, String... buttons);
	/**
	 * 
	 * @param message
	 * @param min
	 * @param max
	 * @return
	 */
	int getUserInteger(String message, int min, int max); // Kan udelades
	/**
	 * 
	 * @param message
	 * @return
	 */
	int getUserInteger(String message);
	/**
	 * 
	 * @param message
	 * @param options
	 * @return
	 */
	String getUserSelection(String message, String... options);
	/**
	 * 
	 * @param message
	 * @param trueButton
	 * @param falseButton
	 * @return
	 */
	boolean getUserLeftButtonPressed(String message, String trueButton, String falseButton);
	
	// GUI relateret
	/**
	 * 
	 * @param name
	 * @param balance
	 */
	void addPlayer(String name, int balance);
	/**
	 * 
	 * @param name
	 * @param balance
	 * @param car
	 */
	void addPlayer(String name, int balance, Car car);
	/**
	 * 
	 * @param message
	 */
	void showMessage(String message);
	/**
	 * 
	 */
	void closeGUI();
	/**
	 * 
	 * @param fieldNumber
	 * @param name
	 */
	void setCar(int fieldNumber, String name);
	/**
	 * 
	 * @param fieldID
	 * @param houseCount
	 */
	void setHouse(int fieldID, int houseCount);
	/**
	 * 
	 * @param fieldID
	 * @param hasHotel
	 */
	void setHotel(int fieldID, boolean hasHotel);
	/**
	 * 
	 * @param id
	 * @param name
	 */
	void setOwner(int id, String name);
	/**
	 * 
	 * @param faceValue1
	 * @param faceValue2
	 */
	void setDice(int faceValue1, int faceValue2);
	/**
	 * 
	 * @param field_id
	 */
	void removeOwner(int field_id);
	/**
	 * 
	 * @param position
	 * @param name
	 */
	void removeCar(int position, String name);
	/**
	 * 
	 * @param name
	 */
	void removeAllCars(String name);
	
}
