package boundary;

import java.io.IOException;
import java.sql.SQLException;

import entity.ChanceCard;
import entity.Player;
import entity.fields.AbstractFields;
/**
 * Data Transfer Objects Interface
 * @author Oliver
 */
public interface DTO {
	
	/**
	 * Adds a player to the database
	 * 
	 * @param player - The player to be added to the database
	 * @throws SQLException
	 */
	void createPlayer(Player player)										 	throws SQLException;
	
	/**
	 * Adds an account to the database
	 * 
	 * @param aId - The ID of the account
	 * @param balance - The balance of the account
	 * @throws SQLException
	 */
	void createAccount(int aId, int balance) 									throws SQLException;
	
	/**
	 * Adds a vehicle to the database
	 * 
	 * @param vId - The ID of the vehicle
	 * @param vColour - The colour of the vehicle
	 * @param vType - The type of the vehicle
	 * @throws SQLException
	 */
	void createVehicle(int vId, String vColour, String vType) 					throws SQLException;
	
	/**
	 * Adds a ChanceCard to the database
	 * 
	 * @param card - The card object to be saved in the database
	 * @param position - The position of the card in the deck
	 * @throws SQLException
	 */
	void createChanceCard(ChanceCard card, int position) 						throws SQLException;
	
	/**
	 * Updates the position of a player in the database
	 * 
	 * @param player - The player object who's position is to be updated 
	 * @throws SQLException
	 */
	void setPosition(Player player) 											throws SQLException;
	
	/**
	 * Updates the balance of a player in the database
	 * 
	 * @param player - The player object who's balance is to be updated
	 * @throws SQLException
	 */
	void setBalance(Player player) 												throws SQLException;
	
	/**
	 *Updates the jailTime attribute of a player object in the database
	 *
	 * @param player - The player object who's balance is to be updated
	 * @throws SQLException
	 */
	void setJailTime(Player player) 											throws SQLException;
	
	/**
	 * Updates the position of a card in the deck in the database
	 * @param position - The position of the card in the deck
	 * @param card_id - The ID of the card
	 * @throws SQLException
	 */
	void setCardPosition(int position, String card_id) 							throws SQLException;
	
	/** 
	 * Updates the house count for a property in the database
	 * 
	 * @param field_id - The ID of the field
	 * @param house_count - The number of houses built on the property
	 * @throws SQLException
	 */
	void setHouseCount(int field_id, int house_count) 							throws SQLException;
	
	/**
	 * Updates whether or not the property is mortgaged in the database
	 * 
	 * @param field_id - The ID of the field
	 * @param mortgaged - Whether or not the field is mortgaged
	 * @throws SQLException
	 */
	void setMortgage(int field_id, boolean mortgaged) 							throws SQLException;
	
	/**
	 * Updates the isActive attribute of a player in the database
	 * 
	 * @param player - The player object who's isActive attribute is to be updated
	 * @throws SQLException
	 */
	void setIsAlive(Player player) 												throws SQLException;
	
	/**
	 * Updates whether or not it is the player's turn in the database
	 * 
	 * @param player - The player object who's turn attribute is to be updated
	 * @throws SQLException
	 */
	void setTurn(Player player) 												throws SQLException;
	
	/**
	 * Creates a new database with the given name
	 * 
	 * @param dbName - The name of new database
	 * @throws SQLException
	 * @throws IOException
	 */
	void createNewDB(String dbName)												throws SQLException, IOException;
	
	/**
	 * Updates the information of a ChanceCard in the database
	 * 
	 * @param card - The Card object to be updated
	 * @param position - The position of the card in the deck
	 * @throws SQLException
	 */
	public void updateCard(ChanceCard card, int position) 						throws SQLException;
	
	/**
	 * Sets the the owner of a property in the database
	 * 
	 * @param field_id - The ID of the field
	 * @param player_id - The player's ID
	 * @throws SQLException
	 */
	public void setOwner(int field_id, int player_id) 							throws SQLException;
	
	/**
	 * Closes the connection to the database
	 * 
	 * @throws SQLException
	 */
	public void closeConnection() 												throws SQLException;
	
	/**
	 * Adds a property to the database
	 * 
	 * @throws SQLException
	 */
	public void createProperties() 												throws SQLException;
	
	/**
	 * Updates all information about the player in the database
	 * 
	 * @param id - The player's ID
	 * @param position - The player's position on the board
	 * @param jailTime - The player's time in jail
	 * @param isAcive - The player's isActive attribute
	 * @param turn - The player's turn attribute
	 * @param balance - The player's balance
	 * @throws SQLException
	 */
	public void savePlayers(int id, int position, int jailTime, boolean isActive, boolean turn, int balance)	throws SQLException;
	
	
	/**
	 * Updates the position and the owner of a card in the database
	 * 
	 * @param cardID - The ID of the card
	 * @param playerID - The player's ID
	 * @param cardPosition - The position of the card in the deck
	 * @throws SQLException
	 */
	public void saveCards(int cardID, int playerID, int cardPosition)			throws SQLException;

	/**
	 * Updates all information about a field in the database
	 * 
	 * @param fieldID - The ID of the field
	 * @param playerID - The player's ID
	 * @param houseCount - The number of houses on the property
	 * @param mortgage - The isMortgaged attribute of the field
	 * @throws SQLException
	 */
	public void saveFields(int fieldID, int playerID, int houseCount, boolean mortgage)	throws SQLException;

	/**
	 * Calls a stored procedure in the database to create a transaction between the two players' accounts
	 * 
	 * @param player - The player who is to pay
	 * @param owner - The player who is to receive money
	 * @throws SQLException
	 */
	public void moneyTransfer(Player player, Player owner)						throws SQLException;

	/**
	 * Calls a stored procedure in the database to start a transaction where the player pays the price of a field 
	 * and then becomes owner of that field
	 * 
	 * @param player - The player who is to buy the field
	 * @param field - The field the player wishes to buy
	 * @throws SQLException
	 */
	public void buyField(Player player, AbstractFields field)					throws SQLException;
}
