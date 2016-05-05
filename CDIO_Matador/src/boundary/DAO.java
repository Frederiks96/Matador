package boundary;

import java.sql.SQLException;

import entity.fields.AbstractFields;
import entity.fields.Territory;

public interface DAO {
	
	// Data Access object - "getters"
	
	int getPosition(int playerID) 				throws SQLException;
	String getPlayerName(int playerID) 			throws SQLException;
	int getBalance(int playerID) 				throws SQLException;
	int getJailTime(int playerID) 				throws SQLException;
	String getVehicleColour(int playerID) 		throws SQLException;
	String getVehicleType(int playerID) 		throws SQLException;
	Integer getOwnerID(int fieldID) 			throws SQLException;
	int getCardId(int position) 				throws SQLException;
	int getCardPosition(int cardID) 			throws SQLException;
	int countPlayers() 							throws SQLException;
	int getFieldHouseCount(Territory territory) throws SQLException;
	boolean isMortgaged(AbstractFields field) 	throws SQLException;
	public String[] getActiveGames() 			throws SQLException;
	

}
