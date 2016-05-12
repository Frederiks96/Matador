package entity.fields;

import boundary.GUI_Commands;
import entity.GameBoard;
import entity.Player;
import entity.Texts;

public interface Ownable {
	
	/**
	 * Returns the owner of the field if the field is owned, otherwise returns null
	 * 
	 * @return player
	 */
	Player getOwner();
	
	/**
	 * Sets the owner of the field in the game logic and the GUI to the player given in the parameter
	 * 
	 * @param player – The player who will be set owner of the field
	 * @param gui – the GUI object, which will set the owner of field in the GUI
	 */
	void setOwner(Player player, GUI_Commands gui);
	
	/**
	 * Returns whether a field is owned.
	 * 
	 * @return true if the field is owned, otherwise returns null
	 */
	boolean isOwned();
	
	/**
	 * Sets player as the owner of the field in the game logic and the GUI, if the owner has enough credit to buy. 
	 * Subtracts the price of the field from the player's account, otherwise prints a message to the user that he has insufficient funds.
	 * 
	 * @param player – The player who wishes to buy the field
	 * @param text – The text object that specifies which text will be shown to the user, depending on the language
	 * @param gui – The GUI object that sets the player as owner or displays a message to the user
	 */
	void buyProperty(Player player, Texts text, GUI_Commands gui);
	
	/**
	 * Mortgages a field, and updates the player's balance with the value of the mortgage 
	 * 
	 * @param text – The text object that specifies which text will be shown to the user, depending on the language
	 * @param gui – The GUI object which will display messages to the user
	 */
	void mortgage(Texts text, GUI_Commands gui);
	
	/**
	 * Sets the attribute mortgage of a field to the attribute given.
	 * 
	 * @param x – the boolean value, that determines whether a field should be mortgaged or not.
	 */
	void setMortgage(boolean x);
	
	/**
	 * Cancels the mortgage of a field and subtracts the value of the mortgage and an interest of 10% from the player's account.
	 */
	void unMortgage();
	
	/**
	 * Sells the property to the bank for half the price, and sets the owner to null. Updates the player's balance with the amount. 
	 * 
	 * @param player – The owner of the field, who sells the property
	 */
	void sellProperty(Player player);
	
	/**
	 * Returns the price of the field
	 * 
	 * @return price
	 */
	int getPrice();
	
	/**
	 * Returns the current rent of the field, based on the owner and number of properties on the field (if any).
	 * 
	 * @param board – The board on which the field is placed
	 * @return rent
	 */
	int getRent(GameBoard board);
	
	/**
	 * Returns whether a field is mortgaged or not.
	 * @return true if the field is mortgaged, otherwise false.
	 */
	boolean isMortgaged();
}
