package boundary;

import desktop_codebehind.Car;

/**
 * 
 * @author Benjamin Jensen
 *
 */
public interface Boundary_Interface {
	
	// User interference
	/**
	 * 
	 * @param message - The message sent to the gui
	 * @return userString - What the user writes
	 */
	String getUserString(String message);
	/**
	 * 
	 * @param message - The message sent to the gui
	 * @param buttons - The buttons sent to the gui
	 * @return userButtonPressed - what button the user pressed
	 */
	String getUserButtonPressed(String message, String... buttons);

	/**
	 * 
	 * @param message - The message sent to the gui
	 * @return userIntger - The number the user writes
	 */
	int getUserInteger(String message);
	/**
	 * 
	 * @param message - The message sent to the gui
	 * @param options - The options sent to the gui
	 * @return userSelection - The users selection
	 */
	String getUserSelection(String message, String... options);
	/**
	 * 
	 * @param message - The message sent to the gui
	 * @param trueButton - A button which returns true
	 * @param falseButton - A button which reutrns false
	 * @return userLeftButtonPressed - True if true button is pressed, false if false button is pressed
	 */
	boolean getUserLeftButtonPressed(String message, String trueButton, String falseButton);
	
	// GUI relateret
	/**
	 * 
	 * @param name - Name of the player added
	 * @param balance - Balance of the player added
	 */
	void addPlayer(String name, int balance);
	/**
	 * 
	 * @param name - Name of the player added
	 * @param balance - Balance of the player added
	 * @param car - The car of the player added
	 */
	void addPlayer(String name, int balance, Car car);
	/**
	 * 
	 * @param message - The message sent to the gui
	 */
	void showMessage(String message);
	/**
	 * Closes the GUI
	 */
	void closeGUI();
	/**
	 * 
	 * @param fieldNumber - The number of the field
	 * @param name - The name of the field
	 */
	void setCar(int fieldNumber, String name);
	/**
	 * 
	 * @param fieldID - The fields ID
	 * @param houseCount - The amount of houses on the field
	 */
	void setHouse(int fieldID, int houseCount);
	/**
	 * 
	 * @param fieldID - The fields ID
	 * @param hasHotel - If the field has a hotel
	 */
	void setHotel(int fieldID, boolean hasHotel);
	/**
	 * 
	 * @param id - The owners ID
	 * @param name - The owners name
	 */
	void setOwner(int id, String name);
	/**
	 * 
	 * @param faceValue1 - The first dice value
	 * @param faceValue2 - The second dice value
	 */
	void setDice(int faceValue1, int faceValue2);
	/**
	 * 
	 * @param field_id - The fields ID
	 */
	void removeOwner(int field_id);
	/**
	 * 
	 * @param position - The cars position on the gameboard
	 * @param name - The name of the cars owner
	 */
	void removeCar(int position, String name);
	/**
	 * 
	 * @param name - The name of the owner of the cars
	 */
	void removeAllCars(String name);
	
	/**
	 * 
	 * @param name - Name of the player
	 * @param balance - balance of the player
	 */
	void setBalance(String name, int balance);
	
}
