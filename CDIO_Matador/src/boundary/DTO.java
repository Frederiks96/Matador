package boundary;

import java.io.IOException;
import java.sql.SQLException;

import entity.ChanceCard;
import entity.Player;
import entity.fields.AbstractFields;

public interface DTO {
	
	// Data Transfer Object - "Setters"
	
	/**
	 * 
	 * @param player
	 * @throws SQLException
	 */
	void createPlayer(Player player)										 	throws SQLException;
	
	/**
	 * 
	 * @param aId
	 * @param balance
	 * @throws SQLException
	 */
	void createAccount(int aId, int balance) 									throws SQLException;
	
	/**
	 * 
	 * @param vId
	 * @param vColour
	 * @param vType
	 * @throws SQLException
	 */
	void createVehicle(int vId, String vColour, String vType) 					throws SQLException;
	
	/**
	 * 
	 * @param card
	 * @param position
	 * @throws SQLException
	 */
	void createChanceCard(ChanceCard card, int position) 						throws SQLException;
	
	/**
	 * 
	 * @param player
	 * @throws SQLException
	 */
	void setPosition(Player player) 											throws SQLException;
	
	/**
	 * 
	 * @param player
	 * @throws SQLException
	 */
	void setBalance(Player player) 												throws SQLException;
	
	/**
	 * 
	 * @param player
	 * @throws SQLException
	 */
	void setJailTime(Player player) 											throws SQLException;
	
	/**
	 * 
	 * @param position
	 * @param card_id
	 * @throws SQLException
	 */
	void setCardPosition(int position, String card_id) 							throws SQLException;
	
	/**
	 * 
	 * @param field_id
	 * @param house_count
	 * @throws SQLException
	 */
	void setHouseCount(int field_id, int house_count) 							throws SQLException;
	
	/**
	 * 
	 * @param field_id
	 * @param mortgaged
	 * @throws SQLException
	 */
	void setMortgage(int field_id, boolean mortgaged) 							throws SQLException;
	
	/**
	 * 
	 * @param player
	 * @throws SQLException
	 */
	void setIsAlive(Player player) 												throws SQLException;
	
	/**
	 * 
	 * @param player
	 * @throws SQLException
	 */
	void setTurn(Player player) 												throws SQLException;
	
	/**
	 * 
	 * @param dbName
	 * @throws SQLException
	 * @throws IOException
	 */
	void createNewDB(String dbName)												throws SQLException, IOException;
	
	/**
	 * 
	 * @param card
	 * @param position
	 * @throws SQLException
	 */
	public void updateCard(ChanceCard card, int position) 						throws SQLException;
	
	/**
	 * 
	 * @param field_id
	 * @param player_id
	 * @throws SQLException
	 */
	public void setOwner(int field_id, int player_id) 							throws SQLException;
	
	/**
	 * 
	 * @throws SQLException
	 */
	public void closeConnection() 												throws SQLException;
	
	/**
	 * 
	 * @throws SQLException
	 */
	public void createProperties() 												throws SQLException;

}
