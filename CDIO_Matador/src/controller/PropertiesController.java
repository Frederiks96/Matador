package controller;

import boundary.GUI_Commands;
import entity.GameBoard;
import entity.Player;
import entity.Texts;
import entity.fields.AbstractFields;
import entity.fields.Territory;
import entity.fields.Ownable;

/**
 * The class that manages mortgages and buildings
 * 
 * @author Frederik
 *
 */

public class PropertiesController {

	//Kontrollerer alt du kan gøre med properties. mangage /mortgage osv. 

	private AbstractFields property;
	private String propertyName;
	private AbstractFields[] fields;
	private String choice;
	
	/**
	 *Allows a player to manage his/hers properties so he/she can build houses/hotels
	  and mortgage/un-mortgage properties. 
	 * 
	 * @param gui- The GUI_Commands object, that controls the GUI and shows messages to the user and gets the user's choice
	 * @param player - The player who wishes to 
	 * @param text - The text object that specifies which texts should be shown to the user through the GUI
	 * @param gameboard - The gameboard on which the game is being played
	 */
	

	public void manage(GUI_Commands gui, Player player, Texts text, GameBoard gameboard) {

		fields = gameboard.getLogicFields();

		if (gameboard.getOwnedProperties(player) != null) {
			String[] properties = new String[gameboard.getOwnedProperties(player).size()];
			propertyName = gui.getUserSelection(text.getString("choosePropertyBuild"),
					gameboard.getOwnedProperties(player).toArray(properties));

			property = gameboard.getProperty(propertyName);


			do {
				if (!((Ownable)(property)).isMortgaged() && property instanceof Territory){
					choice = gui.getUserButtonPressed("", text.getStrings("mortgage","manageBuildings","back"));
				} else if (((Ownable)(property)).isMortgaged()) {
					choice = gui.getUserButtonPressed("", text.getString("unMortgage"), text.getString("back"));
				} else {
					choice = gui.getUserButtonPressed("", text.getString("mortgage"), text.getString("back"));
				}

				if (choice.equals(text.getString("manageBuildings")) && gameboard.hasAll(player, ((Territory)property).getColour())) {
					// Build
					build(player, gui, text, gameboard);
				} else if (choice.equals(text.getString("mortgage"))) {
					// Mortgage
					((Ownable)(property)).mortgage(text, gui);
				} else if (choice.equals(text.getString("unMortgage"))) {
					// UnMortgage
					((Territory)(property)).unMortgage();
				} else if (choice.equals(text.getString("manageBuildings")) && !gameboard.hasAll(player, ((Territory)property).getColour())) {
					gui.showMessage(text.getString("notEnoughTerritory"));
				}
				gui.setBalance(player.getName(), player.getBalance());
			} while (!choice.equals(text.getString("back")));
		} else {
			gui.showMessage(text.getString("noPropOwned"));
		}
	}
	
	/**
	 * Allows player to build/remove house(s)/hotel.
	 * 
	 * @param player - The player who wishes to build
	 * @param gui - The GUI_Commands object, that controls the GUI and shows messages to the user and gets the user's choice
	 * @param text - The text object that specifies which texts should be shown to the user through the GUI
	 * @param gameboard - The gameboard on which the game is being played
	 */

	private void build(Player player, GUI_Commands gui, Texts text, GameBoard gameboard) {
		String building;
		do {
			building = gui.getUserButtonPressed("",	text.getString("house+"), text.getString("house-"),
					text.getString("hotel+"), text.getString("hotel-"), text.getString("back"));

			// Builds a house
			if (building.equals(text.getString("house+"))) {
				if (gameboard.getHouseCount() < 32 && buildEven()) {
					((Territory)(property)).buyHouse(text, gui);
				}
				else if (gameboard.getHouseCount() == 32) { 
					gui.showMessage(text.getString("noMoreHouses"));
				} else {
					gui.showMessage(text.getString("buildEven"));
				}
			}

			// Removes a house
			if(building.equals(text.getString("house-"))) {
				if (sellEven()) {
					((Territory)(property)).sellHouse(gui);
				} else {
					gui.showMessage(text.getString("sellEven"));
				}
			}

			// Builds a hotel
			if(building.equals(text.getString("hotel+"))) {
				if (gameboard.getHotelCount() < 12 && buildEven()) {
					((Territory)(property)).buyHotel(text, gui);
				} else if (gameboard.getHotelCount() == 12) {
					gui.showMessage(text.getString("noMoreHotels"));
				} else {
					gui.showMessage(text.getString("buildEven"));
				}
			}

			// Removes a hotel
			if(building.equals(text.getString("hotel-"))) {
				if (sellEven()) {
					((Territory)(property)).sellHotel(gui);
				} else {
					gui.showMessage(text.getString("sellEven"));
				}
			}

		} while (!building.equals(text.getString("back")));
	}
	
	/**
	 * Controls that the user builds even across fields of the same colour. 
	 * 
	 * @return True if the field won't contain more than one more building than any of the others
	 */

	private boolean buildEven() {
		boolean legal = false;
		int totalHouse = 0;

		for (int i = 0; i < fields.length; i++) {
			if(fields[i] instanceof Territory && !property.equals(fields[i])) {
				if (((Territory)fields[i]).getColour().equals(((Territory)property).getColour())) {
					if(i==1 || i==3 || i==37 || i==39) {
						totalHouse += ((Territory)fields[i]).getHouseCount();				
						legal = (totalHouse >= ((Territory)property).getHouseCount());
					} else {
						totalHouse += ((Territory)fields[i]).getHouseCount();	
						legal = (totalHouse/2 >= ((Territory)property).getHouseCount());
					}
				}
			}
		}
		return legal;
	}
	
	/**
	 * Controls that the user sells even across fields of the same colour
	 * 
	 * @return True if the field won't contain less than one building less than any of the others
	 */
	
	private boolean sellEven() {
		boolean legal = false;
		int totalHouse = 0;

		for (int i = 0; i < fields.length; i++) {
			if(fields[i] instanceof Territory && !property.equals(fields[i])) {
				if (((Territory)fields[i]).getColour().equals(((Territory)property).getColour())) {
					if(i==1 || i==3 || i==37 || i==39) {
						totalHouse += ((Territory)fields[i]).getHouseCount();				
						legal = (totalHouse <= ((Territory)property).getHouseCount());
					} else {
						totalHouse += ((Territory)fields[i]).getHouseCount();	
						legal = (totalHouse/2 <= ((Territory)property).getHouseCount());
					}
				}
			}
		}
		return legal;
	}

}
