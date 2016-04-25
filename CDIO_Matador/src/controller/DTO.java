package controller;

import java.sql.SQLException;

public interface DTO {
	
	// Data Transfer Object - Setters
	
	void createPlayer(int id, String name, int position, int jailTime, boolean isActive, int aId, 
			int balance, int vId, String vColor, String vType) throws SQLException;
	void createAccount(int aId, int balance) throws SQLException;
	void createVehicle(int vId, String vColour, String vType) throws SQLException;
	void updatePosition() throws SQLException;
	void setBalance() throws SQLException;
	void setJailTime() throws SQLException;
	void setCardId() throws SQLException; // Ikke nødvendig?
	void setCardPosition() throws SQLException;
	void setFieldId() throws SQLException; // Ikke nødvendig?
	void setHouseCount() throws SQLException;
	void buildHotel() throws SQLException;
	void mortgage() throws SQLException;
	void setVehicleID() throws SQLException; // Ikke nødvendig?
	void setVehicleColour() throws SQLException;
	void setVehicleType() throws SQLException;
	void setAccountId() throws SQLException; // Ikke nødvendig?

}
