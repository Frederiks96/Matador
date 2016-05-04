package controller;

import entity.Player;
import entity.Texts;
import entity.fields.Territory;
import boundary.GUI_Commands;
import desktop_fields.Ownable;
import entity.GameBoard;
import entity.fields.AbstractFields;

public class PropertiesController {

	private AbstractFields property;
	private String propertyName;
	private AbstractFields[] fields;

	public void manage(GUI_Commands gui, Player player, Texts text, GameBoard gameboard){
		
		fields = gameboard.getLogicFields();
		
		propertyName = gui.getUserSelection(text.getString("choosePropertyBuild"),
				getOwnedProperties(player));
		
		for (int i = 0; i < fields.length; i++) {
				if(fields[i].getName()==propertyName)
					property = fields[i];
			}
		}
	
		String choice;
		do{

			boolean isMortgaged = false;

			// Checks if the property is mortgaged
			for (int j = 0; j < fields.length; j++) {
				if (fields[j].getName().equals(propertyName)){
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
				build(player, gui, text, gameboard);
			}

			// Mortgage
			if(choice == text.getString("mortgage")){
				for (int j = 0; j < fields.length; j++) {
					if (fields[j].getName().equals(propertyName)){
						((Territory)(fields[j])).mortgage(text, gui);
					}
				}
			}

			// UnMortgage
			if(choice == text.getString("unMortgage")){
				for (int j = 0; j < fields.length; j++) {
					if (fields[j].getName().equals(propertyName)){
						((Territory)(fields[j])).unMortgage();
					}
				}
			}

		}while (choice != text.getString("back"));
	}
	
	private void build(Player player, GUI_Commands gui, Texts text,
			GameBoard gameboard){ // TODO mangler build even
		String building;
		do{
			building = gui.getUserButtonPressed("",	text.getString("house+"),text.getString("house-"),
					text.getString("hotel+"),text.getString("hotel-"), text.getString("back"));

			// Builds a house
			if (building == text.getString("house+") && gameboard.getHouseCount() < 32 && buildEven()){
				for (int j = 0; j < fields.length; j++) {
					if (fields[j].getName().equals(propertyName))
						((Territory) (fields[j])).buyHouse(text, gui);
				}	
			}else gui.showMessage(text.getString("noMoreHouses"));

			// Remove a house
			if(building == text.getString("house-")){
				for (int j = 0; j < fields.length; j++) {
					if (fields[j].getName().equals(propertyName))
						((Territory) (fields[j])).sellHouse(gui);
				}
			}
			// Builds a hotel
			if(building == text.getString("hotel+") && gameboard.getHotelCount() < 12 && buildEven()){
				for (int j = 0; j < fields.length; j++) {
					if (fields[j].getName().equals(propertyName))
						((Territory) (fields[j])).buyHotel(text, gui);
				}	
			}else gui.showMessage(text.getString("noMoreHotels"));

			// Remove a hotel
			if(building == text.getString("hotel-")){
				for (int j = 0; j < fields.length; j++) {
					if (fields[j].getName().equals(propertyName))
						((Territory) (fields[j])).sellHotel(gui);
				}	
			}

		}while (building != text.getString("back"));
	}
	
	private String[] getOwnedProperties(Player player) { 
		
		int numPropertiesOwned = player.getNumTerritoryOwned()+player.getNumFleetsOwned()+player.getNumBreweriesOwned();
		
		String[] ownedProperties = new String[numPropertiesOwned];
		
		int j = 0;
		for (int i = 0; i < fields.length; i++) {
			if(fields[i] instanceof Ownable && ((Territory)(fields[i])).getOwner()!=null){
				if (((Territory)(fields[i])).getOwner().equals(player)){
					ownedProperties[j] = fields[i].getName();
					j++;
				}
			}
		}
		return ownedProperties;
	}

	private boolean buildEven(){
		boolean legal = false;
		
		int houseCount = 0;
		
		for (int i = 0; i < fields.length; i++) {
			 
		}
		
		return legal;
	}

	private boolean removeEven(){
		boolean legal = false;
		
		
		return legal;
	}
	
}
