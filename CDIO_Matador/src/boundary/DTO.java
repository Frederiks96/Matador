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
	 * Inserts a row into the player table in the database
	 * @param player - takes a player object as a parameter to access the methods from the player class
	 * @throws SQLException
	 */
	void createPlayer(Player player)										 	throws SQLException;
	
	/**
	 * Insert a row into the bank table in the database
	 * @param aId - account ID
	 * @param balance - balance 
	 * @throws SQLException
	 */
	void createAccount(int aId, int balance) 									throws SQLException;
	
	/**
	 * Insert a row into the vehicle table in the database
	 * @param vId - vehicle ID
	 * @param vColour - colour of vehicle
	 * @param vType - type of vehicle
	 * @throws SQLException
	 */
	void createVehicle(int vId, String vColour, String vType) 					throws SQLException;
	
	/**
	 * Insert a row into the table ChanceCard in the database
	 * @param card - object of card the access methods in chanceCard class
	 * @param position - the position of the card in the deck
	 * @throws SQLException
	 */
	void createChanceCard(ChanceCard card, int position) 						throws SQLException;
	
	/**
	 * Updates the position of the player in the database
	 * @param player - takes a player object as a parameter to access the methods from the player class
	 * @throws SQLException
	 */
	void setPosition(Player player) 											throws SQLException;
	
	/**
	 * Update the balance of the player in the database
	 * @param player - takes a player object as a parameter to access the methods from the player class
	 * @throws SQLException
	 */
	void setBalance(Player player) 												throws SQLException;
	
	/**
	 *Updates the players jail time in the database
	 * @param player - takes a player object as a parameter to access the methods from the player class
	 * @throws SQLException
	 */
	void setJailTime(Player player) 											throws SQLException;
	
	/**
	 * Update the position of the card in the deck in the database
	 * @param position - cards position in the deck
	 * @param card_id - card ID
	 * @throws SQLException
	 */
	void setCardPosition(int position, String card_id) 							throws SQLException;
	
	/** 
	 * Updates the house count for the property in the database
	 * @param field_id - field ID
	 * @param house_count - number of houses on the property
	 * @throws SQLException
	 */
	void setHouseCount(int field_id, int house_count) 							throws SQLException;
	
	/**
	 * Updates whether or not the property is mortgages in the database
	 * @param field_id - field ID
	 * @param mortgaged - is the property mortgaged or not
	 * @throws SQLException
	 */
	void setMortgage(int field_id, boolean mortgaged) 							throws SQLException;
	
	/**
	 * Updates the players active statues in the database
	 * @param player - takes a player object as a parameter to access the methods from the player class
	 * @throws SQLException
	 */
	void setIsAlive(Player player) 												throws SQLException;
	
	/**
	 * Updates whether or not it is the players turn in the database
	 * @param player - takes a player object as a parameter to access the methods from the player class
	 * @throws SQLException
	 */
	void setTurn(Player player) 												throws SQLException;
	
	/**
	 * Creates a new database with the given name dbName
	 * @param dbName - name to the new database
	 * @throws SQLException
	 * @throws IOException
	 */
	void createNewDB(String dbName)												throws SQLException, IOException;
	
	/**
	 * Updates the information related to the ChanceCard table in the database
	 * @param card - takes an object of the ChanceCard as a parameter, to access the methods in the chanceCard class
	 * @param position - the position of the chanceCard in the deck
	 * @throws SQLException
	 */
	public void updateCard(ChanceCard card, int position) 						throws SQLException;
	
	/**
	 * Updates the the owner of a property in the database
	 * @param field_id - field ID
	 * @param player_id - player ID
	 * @throws SQLException
	 */
	public void setOwner(int field_id, int player_id) 							throws SQLException;
	
	/**
	 * Closes the connection to the database
	 * @throws SQLException
	 */
	public void closeConnection() 												throws SQLException;
	
	/**
	 * Inserts a row  into the properties table in the database for all Ownables
	 * @throws SQLException
	 */
	public void createProperties() 												throws SQLException;
	
	/**
	 * Updates all information about the player in the database
	 * @param id - player ID
	 * @param position - player's position on the board
	 * @param jailTime - player's time in jail
	 * @param isAcive - is the player active/not bankrupt
	 * @param turn - is it the players turn
	 * @param balance - player's balance
	 * @throws SQLException
	 */
	public void savePlayers(int id, int position, int jailTime, boolean isActive, boolean turn, int balance)	throws SQLException;
	
	
	/**
	 * Updates the cards position and and owner in the database
	 * @param cardID - card ID
	 * @param playerID - player ID
	 * @param cardPosition - card's position in the deck
	 * @throws SQLException
	 */
	public void saveCards(int cardID, int playerID, int cardPosition)			throws SQLException;

	/**
	 * Updates all information about a field in the database
	 * @param fieldID - field ID
	 * @param playerID - player ID
	 * @param houseCount - the number of houses on the property
	 * @param mortgage - is the property mortgaged
	 * @throws SQLException
	 */
	public void saveFields(int fieldID, int playerID, int houseCount, boolean mortgage)	throws SQLException;

	/**
	 * 
	 * @param player - Takes a player object as a parameter to access the methods in the player class
	 * @param owner - Takes a player object as a parameter to access the methods in the player class
	 * @throws SQLException
	 */
	public void moneyTransfer(Player player, Player owner)						throws SQLException;

	/**
	 * 
	 * @param player - Takes a player object as a parameter to access the methods in the player class
	 * @param field - Takes an AbstractFields object as a parameter to access the methods in the AbstractFields class
	 * @throws SQLException
	 */
	public void buyField(Player player, AbstractFields field)					throws SQLException;
}
