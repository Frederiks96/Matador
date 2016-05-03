package controller;

import entity.Player;
import entity.Texts;
import entity.fields.Territory;
import boundary.GUI_Commands;
import entity.GameBoard;
import entity.fields.AbstractFields;

public class PropertiesController {


	public void build(String property, Player player, GUI_Commands gui, Texts text, GameBoard gameboard, AbstractFields[] fields){ // mangler build even
		String building;
		do{
			building = gui.getUserButtonPressed("",	text.getString("house+"),text.getString("house-"),
					text.getString("hotel+"),text.getString("hotel-"), text.getString("back"));

			// Builds a house
			if (building == text.getString("house+") && gameboard.getHouseCount() < 32){
				for (int j = 0; j < fields.length; j++) {
					if (fields[j].getName().equals(property))
						((Territory) (fields[j])).buyHouse(text, gui);
				}	
			}else gui.showMessage(text.getString("noMoreHouses"));

			// Remove a house
			if(building == text.getString("house-")){
				for (int j = 0; j < fields.length; j++) {
					if (fields[j].getName().equals(property))
						((Territory) (fields[j])).sellHouse(gui);
				}
			}
			// Builds a hotel
			if(building == text.getString("hotel+") && gameboard.getHotelCount() < 12){
				for (int j = 0; j < fields.length; j++) {
					if (fields[j].getName().equals(property))
						((Territory) (fields[j])).buyHotel(text, gui);
				}	
			}else gui.showMessage(text.getString("noMoreHotels"));

			// Remove a hotel
			if(building == text.getString("hotel-")){
				for (int j = 0; j < fields.length; j++) {
					if (fields[j].getName().equals(property))
						((Territory) (fields[j])).sellHotel(gui);
				}	
			}

		}while (building != text.getString("back"));
	}

	public void manage(GUI_Commands gui, Player player, Texts text, AbstractFields[] fields, GameBoard gameboard){
		String property = gui.getUserSelection(text.getString("choosePropertyBuild"),
				getOwnedTerritories(player, fields));

		String choice;
		do{

			boolean isMortgaged = false;

			for (int j = 0; j < fields.length; j++) {
				if (fields[j].getName().equals(property)){
					isMortgaged = ((Territory)(fields[j])).isMortgaged();
				}
			}	
			
			if(!isMortgaged){
				choice = gui.getUserButtonPressed("",  text.getString("mortgage"),
						text.getString("manageBuildings"), text.getString("back"));
			}else {
				choice = gui.getUserButtonPressed("",  text.getString("unMortgage"),
						text.getString("manageBuildings"), text.getString("back"));
			}

			// Build
			if(choice == text.getString("manageBuildings")){
				build(property, player, gui, text, gameboard, fields);
			}

			// Mortgage
			if(choice == text.getString("mortgage")){
				for (int j = 0; j < fields.length; j++) {
					if (fields[j].getName().equals(property)){
						((Territory)(fields[j])).mortgage(text, gui);
					}
				}
			}

			// UnMortgage
			if(choice == text.getString("unMortgage")){
				for (int j = 0; j < fields.length; j++) {
					if (fields[j].getName().equals(property)){
						((Territory)(fields[j])).unMortgage();
					}
				}
			}

		}while (choice != text.getString("back"));
	}
	
	
	public String[] getOwnedTerritories(Player player, AbstractFields[] fields) { 
		String[] ownedTerritories = new String[player.getNumTerritoryOwned()];
		int j = 0;
		for (int i = 0; i < fields.length; i++) {
			if(fields[i] instanceof Territory && ((Territory)(fields[i])).getOwner()!=null){
				if (((Territory)(fields[i])).getOwner().equals(player)){
					ownedTerritories[j] = fields[i].getName();
					j++;
				}
			}
		}
		return ownedTerritories;
	}


}
