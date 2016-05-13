package boundary;

import java.sql.SQLException;

import entity.fields.AbstractFields;
import entity.fields.Territory;

/**
 * An interface for pulling data from the Database
 * @author Benjamin Jensen
 *
 */
public interface DAO {
	
	// Data Access object - "getters"
	
	/**
	 * Returns the last saved position of a player
	 * 
	 * @param playerID - The ID of the player
	 * @return position - The player's position
	 * @throws SQLException
	 */
	int getPosition(int playerID) 				throws SQLException;
	
	/**
	 * Returns the playername of a playerID
	 * 
	 * @param playerID - The ID of the player
	 * @return playerName - The name of the player
	 * @throws SQLException
	 */
	String getPlayerName(int playerID) 			throws SQLException;
	
	/**
	 * Returns the last saved balance of a player
	 * 
	 * @param playerID - The ID of the player
	 * @return balance - The player's balance
	 * @throws SQLException
	 */
	int getBalance(int playerID) 				throws SQLException;
	
	/**
	 * Returns the jailTime of a player
	 * 
	 * @param playerID - The ID of the player
	 * @return jailTime - The jailTime attribute of a player
	 * @throws SQLException
	 */
	int getJailTime(int playerID) 				throws SQLException;
	
	/**
	 * Returns the colour of a specific player's vehicle
	 * 
	 * @param playerID - The ID of the player
	 * @return vehicleColour - The colour of the player's vehicle
	 * @throws SQLException
	 */
	String getVehicleColour(int playerID) 		throws SQLException;
	
	/**
	 * Returns the type of the vehicle belonging to the player
	 * 
	 * @param playerID - The ID of the player
	 * @return vehicleType - The type of the player's vehicle
	 * @throws SQLException
	 */
	String getVehicleType(int playerID) 		throws SQLException;
	
	/**
	 * Returns the ownerID of a specific field
	 * 
	 * @param fieldID - The ID of the field
	 * @return ownerID - The owner of the field's ID
	 * @throws SQLException
	 */
	int getOwnerID(int fieldID) 				throws SQLException;
	
	/**
	 * Returns the id of a card in a specific location in the deck
	 * 
	 * @param position - The position of the card in the deck
	 * @return cardId - The ID of the card
	 * @throws SQLException
	 */
	int getCardId(int position) 				throws SQLException;
	
	/**
	 * Returns the position of a specific card
	 * 
	 * @param cardID - The ID of the card
	 * @return cardPosition - The position of the card in the deck
	 * @throws SQLException
	 */
	int getCardPosition(int cardID) 			throws SQLException;
	
	/**
	 * Returns the amount of players in the database
	 * 
	 * @return countPlayers - The amount of players
	 * @throws SQLException
	 */
	int countPlayers() 							throws SQLException;
	
	/**
	 * Returns the number of houses on a field
	 * 
	 * @param territory - The field of which we want to know the houseCount
	 * @return fieldHouseCount
	 * @throws SQLException
	 */
	int getFieldHouseCount(Territory territory) throws SQLException;
	
	/**
	 * Returns whether a specific field is mortgaged or not
	 * 
	 * @param field - The field that is to be examined
	 * @return isMortgaged - True if the field is mortgaged, otherwise false
	 * @throws SQLException
	 */
	boolean isMortgaged(AbstractFields field) 	throws SQLException;
	
	/**
	 * Returns a String array containing all the saved games in the database
	 * 
	 * @return activeGames - The names of the saved games in the database 
	 * @throws SQLException
	 */
	public String[] getActiveGames() 			throws SQLException;
	
	/**
	 * Returns the isAlive attribute of a player
	 * 
	 * @param playerID - The ID of the player
	 * @return isAlive - True if the player hasn't gone bankrupt, otherwise false
	 * @throws SQLException
	 */
	public boolean getIsAlive(int playerID)	    throws SQLException;
	
	/**
	 * Returns if it is the specific player's turn
	 * 
	 * @param playerID - The ID of the player
	 * @return turn - True if it is the player's turn, otherwise false
	 * @throws SQLException
	 */
	public boolean getTurn(int playerID) 		throws SQLException;

}
