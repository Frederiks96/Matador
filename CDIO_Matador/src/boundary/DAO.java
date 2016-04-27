package boundary;

import java.sql.SQLException;

import entity.Player;
import entity.fields.Territory;

public interface DAO {
	
	// Data Acces object - getters
	
	int getPosition(int playerID) throws SQLException;
	String getPlayerName(int playerID) throws SQLException;
	int getBalance(int playerID)throws SQLException;
	int getJailTime(int playerID)throws SQLException;
	String getVehicleColour(int playerID)throws SQLException;
	String getVehicleType(int playerID)throws SQLException;
	
	int getCardId(int position)throws SQLException;
	int getCardPosition(int cardID)throws SQLException;
	
	int getFieldHouseCount(Territory territory)throws SQLException;
	boolean hasHotel(Territory territory)throws SQLException;
	boolean isMortgaged(Territory territory)throws SQLException;
	
	
	

}
