package boundary;

import java.sql.SQLException;

import entity.fields.AbstractFields;
import entity.fields.Territory;

public interface DAO {
	
	// Data Access object - "getters"
	
	/**
	 * 
	 * @param playerID - The ID of the player
	 * @return position - The players position
	 * @throws SQLException
	 */
	int getPosition(int playerID) 				throws SQLException;
	
	/**
	 * 
	 * @param playerID - The ID of the player
	 * @return playerName - The name of the player
	 * @throws SQLException
	 */
	String getPlayerName(int playerID) 			throws SQLException;
	
	/**
	 * 
	 * @param playerID - The ID of the player
	 * @return balance - The player's balance
	 * @throws SQLException
	 */
	int getBalance(int playerID) 				throws SQLException;
	
	/**
	 * 
	 * @param playerID - The ID of the player
	 * @return jailTime - Time the player have spent in jail
	 * @throws SQLException
	 */
	int getJailTime(int playerID) 				throws SQLException;
	
	/**
	 * 
	 * @param playerID - The ID of the player
	 * @return vehicleColour - The player's vehicle's colour
	 * @throws SQLException
	 */
	String getVehicleColour(int playerID) 		throws SQLException;
	
	/**
	 * 
	 * @param playerID - The ID of the player
	 * @return vehicleType - The player's vehicle's type
	 * @throws SQLException
	 */
	String getVehicleType(int playerID) 		throws SQLException;
	
	/**
	 * 
	 * @param fieldID - The ID of the field
	 * @return owner - The owner of the field
	 * @throws SQLException
	 */
	int getOwnerID(int fieldID) 				throws SQLException;
	
	/**
	 * 
	 * @param position - The cards position in the deck
	 * @return cardId - The chance cards ID
	 * @throws SQLException
	 */
	int getCardId(int position) 				throws SQLException;
	
	/**
	 * 
	 * @param cardID - The chance cards ID
	 * @return cardPosition - The cards position in the deck
	 * @throws SQLException
	 */
	int getCardPosition(int cardID) 			throws SQLException;
	
	/**
	 * 
	 * @return countPlayers - The amount of players
	 * @throws SQLException
	 */
	int countPlayers() 							throws SQLException;
	
	/**
	 * 
	 * @param territory - The field that is to be examined
	 * @return fieldHouseCount - How many houses are on the field
	 * @throws SQLException
	 */
	int getFieldHouseCount(Territory territory) throws SQLException;
	
	/**
	 * 
	 * @param field - The field that is to be examined
	 * @return isMortgaged - If the field is mortgaged
	 * @throws SQLException
	 */
	boolean isMortgaged(AbstractFields field) 	throws SQLException;
	
	/**
	 * 
	 * @return activeGames - how many unfinished games is in the database
	 * @throws SQLException
	 */
	public String[] getActiveGames() 			throws SQLException;
	
	/**
	 * 
	 * @param playerID - The ID of the player
	 * @return isAlive - Is the player in the game
	 * @throws SQLException
	 */
	public boolean getIsAlive(int playerID)	    throws SQLException;
	
	/**
	 * 
	 * @param playerID - The ID of the player
	 * @return turn - Which players turn it is
	 * @throws SQLException
	 */
	public boolean getTurn(int playerID) 		throws SQLException;

}
