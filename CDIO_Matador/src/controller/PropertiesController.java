package controller;

import boundary.GUI_Commands;
import entity.GameBoard;
import entity.Player;
import entity.Texts;
import entity.fields.AbstractFields;
import entity.fields.Territory;

public class PropertiesController {

	private AbstractFields property;
	private String propertyName;
	private AbstractFields[] fields;
	private String choice;

	public void manage(GUI_Commands gui, Player player, Texts text, GameBoard gameboard){

		fields = gameboard.getLogicFields();

		if(gameboard.getOwnedProperties(player)!=(null)){
			propertyName = gui.getUserSelection(text.getString("choosePropertyBuild"),
					gameboard.getOwnedProperties(player));

			for (int i = 0; i < fields.length; i++) {
				if(fields[i].getName()==propertyName)
					property = fields[i];
			}

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
		}else gui.showMessage(text.getString("noProperties"));
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
			if(building == text.getString("house-") && !buildEven()){
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
			if(building == text.getString("hotel-") && !buildEven()){
				for (int j = 0; j < fields.length; j++) {
					if (fields[j].getName().equals(propertyName))
						((Territory) (fields[j])).sellHotel(gui);
				}	
			}

		}while (building != text.getString("back"));
	}

	private boolean buildEven(){
		boolean legal = false;
		int totalHouse = 0;

		for (int i = 0; i < fields.length; i++) {
			if(fields[i] instanceof Territory && !property.equals(fields[i]) && ((Territory)fields[i]).getColour() == ((Territory)property).getColour()){
				if(i==1 || i==3 || i==37 || i==39){
					totalHouse = ((Territory)fields[i]).getHouseCount();				
					legal =( totalHouse >= ((Territory)property).getHouseCount());
				}
				else{
					totalHouse += ((Territory)fields[i]).getHouseCount();	
					legal = ( totalHouse/2 >= ((Territory)property).getHouseCount());
				}
			}
		}
		return legal;
	}

}
