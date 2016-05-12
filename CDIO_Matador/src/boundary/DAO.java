package boundary;

import java.sql.SQLException;

import entity.fields.AbstractFields;
import entity.fields.Territory;

public interface DAO {
	
	// Data Access object - "getters"
	
	/**
	 * 
	 * @param playerID
	 * @return
	 * @throws SQLException
	 */
	int getPosition(int playerID) 				throws SQLException;
	
	/**
	 * 
	 * @param playerID
	 * @return
	 * @throws SQLException
	 */
	String getPlayerName(int playerID) 			throws SQLException;
	
	/**
	 * 
	 * @param playerID
	 * @return
	 * @throws SQLException
	 */
	int getBalance(int playerID) 				throws SQLException;
	
	/**
	 * 
	 * @param playerID
	 * @return
	 * @throws SQLException
	 */
	int getJailTime(int playerID) 				throws SQLException;
	
	/**
	 * 
	 * @param playerID
	 * @return
	 * @throws SQLException
	 */
	String getVehicleColour(int playerID) 		throws SQLException;
	
	/**
	 * 
	 * @param playerID
	 * @return
	 * @throws SQLException
	 */
	String getVehicleType(int playerID) 		throws SQLException;
	
	/**
	 * 
	 * @param fieldID
	 * @return
	 * @throws SQLException
	 */
	int getOwnerID(int fieldID) 				throws SQLException;
	
	/**
	 * 
	 * @param position
	 * @return
	 * @throws SQLException
	 */
	int getCardId(int position) 				throws SQLException;
	
	/**
	 * 
	 * @param cardID
	 * @return
	 * @throws SQLException
	 */
	int getCardPosition(int cardID) 			throws SQLException;
	
	/**
	 * 
	 * @return
	 * @throws SQLException
	 */
	int countPlayers() 							throws SQLException;
	
	/**
	 * 
	 * @param territory
	 * @return
	 * @throws SQLException
	 */
	int getFieldHouseCount(Territory territory) throws SQLException;
	
	/**
	 * 
	 * @param field
	 * @return
	 * @throws SQLException
	 */
	boolean isMortgaged(AbstractFields field) 	throws SQLException;
	
	/**
	 * 
	 * @return
	 * @throws SQLException
	 */
	public String[] getActiveGames() 			throws SQLException;
	
	/**
	 * 
	 * @param playerID
	 * @return
	 * @throws SQLException
	 */
	public boolean getIsAlive(int playerID)	    throws SQLException;
	
	/**
	 * 
	 * @param playerID
	 * @return
	 * @throws SQLException
	 */
	public boolean getTurn(int playerID) 		throws SQLException;

}
