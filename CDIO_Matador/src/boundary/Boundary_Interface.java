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
	 * Returns the user input as a string
	 * 
	 * @param message - The message that prompts the user
	 * @return userString
	 */
	String getUserString(String message);
	
	/**
	 * Returns the string of the button the user pressed
	 * 
	 * @param message - The message that prompts the user
	 * @param buttons - The strings printed to each button
	 * @return userButtonPressed
	 */
	String getUserButtonPressed(String message, String... buttons);

	/**
	 * Returns the user input as an integer
	 * 
	 * @param message - The message that prompts the user
	 * @return userIntger
	 */
	int getUserInteger(String message);
	
	/**
	 * Returns the string the user selected
	 * 
	 * @param message - The message that prompts the user
	 * @param options - The options the user has to choose from
	 * @return userSelection
	 */
	String getUserSelection(String message, String... options);
	
	/**
	 * Returns whether the user pressed the left button
	 * 
	 * @param message - The message that prompts the user
	 * @param trueButton - The string that should be showned in the true button
	 * @param falseButton - The string that should be showned in the false button
	 * @return userLeftButtonPressed - True if true button is pressed, otherwise false 
	 */
	boolean getUserLeftButtonPressed(String message, String trueButton, String falseButton);
	
	// GUI relateret
	
	/**
	 * Adds a player to the GUI board
	 * 
	 * @param name - The name of the player to be added
	 * @param balance - The balance of the player to be added
	 */
	void addPlayer(String name, int balance);
	
	/**
	 * Places a player on the board in a specific location
	 * 
	 * @param name - The name of the player to be placed on the board
	 * @param balance - The balance of the player to be placed on the board
	 * @param car - The car of the player to be placed on the board
	 */
	void addPlayer(String name, int balance, Car car);
	
	/**
	 * Shows the user a message
	 * 
	 * @param message - The message that prompts the user
	 */
	void showMessage(String message);
	
	/**
	 * Closes the GUI
	 */
	void closeGUI();
	
	/**
	 * Sets the car of a player at a specific location
	 * 
	 * @param fieldNumber - The location on the board
	 * @param name - The name of player  who's car is to be placed
	 */
	void setCar(int fieldNumber, String name);
	
	/**
	 * Sets a specific number of houses on a field. If amount is 0 all houses will be removed.
	 * 
	 * @param fieldID - The ID of the field
	 * @param houseCount - The amount of houses to be placed on the field
	 */
	void setHouse(int fieldID, int houseCount);
	
	/**
	 * Sets or removes a hotel on a field
	 * 
	 * @param fieldID - The ID of the field
	 * @param hasHotel - Whether the field should have a hotel or not
	 */
	void setHotel(int fieldID, boolean hasHotel);
	
	/**
	 * Sets the owner of the field to a specific player's name
	 * 
	 * @param id - The ID of the field
	 * @param name - The name of the owner
	 */
	void setOwner(int id, String name);
	
	/**
	 * Sets the dice in the GUI to the specified values
	 * 
	 * @param faceValue1 - The first die value
	 * @param faceValue2 - The second die value
	 */
	void setDice(int faceValue1, int faceValue2);
	
	/**
	 * Removes the owner of a field
	 * 
	 * @param field_id - The ID of the field
	 */
	void removeOwner(int field_id);
	
	/**
	 * Removes the car of a player from a specific location
	 * 
	 * @param position - The position of the car on the gameboard
	 * @param name - The player's name
	 */
	void removeCar(int position, String name);
	
	/**
	 * Removes all cars from the board that belongs to a player
	 * 
	 * @param name - The player's name
	 */
	void removeAllCars(String name);
	
	/**
	 * Sets the balance of a player to a specific amount
	 * 
	 * @param name - The name of the player
	 * @param balance - The balance of the player
	 */
	void setBalance(String name, int balance);
	
}
