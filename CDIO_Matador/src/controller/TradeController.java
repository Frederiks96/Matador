package controller;

import java.sql.SQLException;
import java.util.ArrayList;

import boundary.GUI_Commands;
import entity.GameBoard;
import entity.Player;
import entity.Texts;
import entity.fields.Brewery;
import entity.fields.Fleet;
import entity.fields.Territory;


public class TradeController {
	private ArrayList<String> ownProperties;
	private ArrayList<String> foeProperties;
	private String answer;
	private int ownOffer;
	private int foeOffer;

	public TradeController() throws SQLException {
	}
	/**
	 * This method is for suggesting a deal to other players
	 * @param offeror - The player starting the offer
	 * @param offeree - The player receiving the offer
	 * @param text - The text object that specifies which texts should be shown to the user through the GUI
	 * @param board - The location of the fields on the gameboard
	 * @param gui- The GUI_Commands object, that controls the GUI and shows messages to the user and gets the user's choice
	 */
	public void suggestDeal(Player offeror, Player offeree, Texts text, GameBoard board, GUI_Commands gui) {
		ownProperties = getOwnProperties(board, offeror, gui, text);
		foeProperties = getFoeProperties(board, offeree, gui, text);

		do {
			ownOffer = gui.getUserInteger(text.getString("getOwnOffer")+text.getFormattedString("lessThan",offeror.getBalance()));
		} while (ownOffer>offeror.getBalance());
		do {
		foeOffer = gui.getUserInteger(text.getFormattedString("getFoeOffer",offeree.getName())+text.getFormattedString("lessThan",offeree.getBalance()));
		} while (foeOffer>offeree.getBalance());
		gui.showMessage(text.getFormattedString("handover", offeree.getName()));
		answer = gui.getUserSelection(text.getString("accept"),text.getString("Yes"),text.getString("No"),text.getString("counterOffer"));

		if (text.getString("Yes").equals(answer)) {
			completeDeal(board, offeror, offeree, ownProperties, foeProperties, ownOffer, foeOffer, gui);
			gui.showMessage(text.getString("doneDeal"));
		} else if (text.getString("counterOffer").equals(answer)) {
			suggestDeal(offeree, offeror, text, board, gui);
		} else {
			gui.showMessage(text.getFormattedString("handover", offeror.getName()));
			gui.showMessage(text.getFormattedString("dealRejected", offeree.getName()));
		}
	}
	/**
	 * 
	 * @param board - The location of the fields on the gameboard
	 * @param offeror - The player starting the offer
	 * @param offeree - The player receiving the offer
	 * @param ownProperties - The properties of the player making the offer
	 * @param foeProperties - The properties of the player recieving the offer
	 * @param ownOffer - The amount of money the player making the offer, offers
	 * @param foeOffer - The amount of money the player receiving the offer, offers
	 * @param gui- The GUI_Commands object, that controls the GUI and shows messages to the user and gets the user's choice
	 */
	private void completeDeal(GameBoard board, Player offeror, Player offeree, ArrayList<String> ownProperties, ArrayList<String> foeProperties, int ownOffer, int foeOffer, GUI_Commands gui) {
		// Setting offeror as owner to offeree's properties
		if (foeProperties.size() > 0) {
			for (int i = 0; i < foeProperties.size(); i++) {
				gui.removeOwner(board.getProperty(foeProperties.get(i)).getID());
				board.setOwner(board.getProperty(foeProperties.get(i)).getID(), offeror, gui);
				if (board.getProperty(foeProperties.get(i)) instanceof Fleet) {
					offeree.sellFleet();
				} else if (board.getProperty(foeProperties.get(i)) instanceof Brewery) {
					offeree.sellBrewery();
				} else if (board.getProperty(foeProperties.get(i)) instanceof Territory) {
					offeree.sellTerritory();
				}
			}
		}
		// Setting offeree as owner to offeror's properties
		if (ownProperties.size() > 0) {
			for (int i = 0; i < ownProperties.size(); i++) {
				gui.removeOwner(board.getProperty(ownProperties.get(i)).getID());
				board.setOwner(board.getProperty(ownProperties.get(i)).getID(), offeree, gui);
				if (board.getProperty(ownProperties.get(i)) instanceof Fleet) {
					offeror.sellFleet();
				} else if (board.getProperty(ownProperties.get(i)) instanceof Brewery) {
					offeror.sellBrewery();
				} else if (board.getProperty(ownProperties.get(i)) instanceof Territory) {
					offeror.sellTerritory();
				}
			}
		}

		offeror.updateBalance(-ownOffer);
		offeree.updateBalance(ownOffer);
		offeree.updateBalance(-foeOffer);
		offeror.updateBalance(foeOffer);

		gui.setBalance(offeror.getName(), offeror.getBalance());
		gui.setBalance(offeree.getName(), offeree.getBalance());
	}
	/**
	 * 
	 * @param board - The location of the fields on the gameboard
	 * @param offeror - The player starting the offer
	 * @param gui- The GUI_Commands object, that controls the GUI and shows messages to the user and gets the user's choice
	 * @param text - The text object that specifies which texts should be shown to the user through the GUI
	 * @return ownProperties - The properties the player making the offer owns
	 */
	private ArrayList<String> getOwnProperties(GameBoard board, Player offeror, GUI_Commands gui, Texts text) {
		ownProperties = new ArrayList<String>();
		ArrayList<String> presented = board.getOwnedUnbuiltProperties(offeror);
		do {
			String[] possible;
			if (presented.size()>0) {
				if (ownProperties.size() == 0) {
					presented.add(text.getString("none"));
					possible = new String[presented.size()];
					presented.toArray(possible);
					String choice = gui.getUserSelection(text.getString("commenceTrade"), possible);
					if (!choice.equals(text.getString("none"))) {
						ownProperties.add(choice);
					} else {
						break;
					}
				} else if (ownProperties.size() == board.getOwnedUnbuiltProperties(offeror).size()) { 
					gui.showMessage(text.getString("noMoreProp"));
					break;
				}
				else {
					// Sorter nogle fra
					for (int i = 0; i < presented.size(); i++) {
						if (ownProperties.contains(presented.get(i))) {
							presented.remove(i);
						}
					}
					possible = new String[presented.size()];
					String choice = gui.getUserSelection(text.getString("commenceTrade"), presented.toArray(possible));
					if (!choice.equals(text.getString("none"))) {
						ownProperties.add(choice);
					} else {
						break;
					}
				}
			} else {
				gui.showMessage(text.getFormattedString("noPropOwned"));
				break;
			}
		} while (gui.getUserLeftButtonPressed(text.getString("moreProperties"), text.getString("Yes"), text.getString("No")));
		return ownProperties;
	}
	/**
	 * 
	 * @param board - The location of the fields on the gameboard
	 * @param offeree - The player receiving the offer
	 * @param gui- The GUI_Commands object, that controls the GUI and shows messages to the user and gets the user's choice
	 * @param text - The text object that specifies which texts should be shown to the user through the GUI
	 * @return foeProperties - The properties the player receiving the offer owns
	 */
	private ArrayList<String> getFoeProperties(GameBoard board, Player offeree, GUI_Commands gui, Texts text) {
		foeProperties = new ArrayList<String>();
		ArrayList<String> presented = board.getOwnedUnbuiltProperties(offeree);
		do {
			String[] possible;
			if (presented.size()>0) {
				if (foeProperties.size() == 0) {
					presented.add(text.getString("none"));
					possible = new String[presented.size()];
					presented.toArray(possible);
					String choice = gui.getUserSelection(text.getFormattedString("foeProperties",offeree.getName()), possible);
					if (!choice.equals(text.getString("none"))) {
						foeProperties.add(choice);
					} else {
						break;
					}
				} else if (foeProperties.size() == board.getOwnedUnbuiltProperties(offeree).size()) {
					gui.showMessage(text.getFormattedString("noMorePropFoe",offeree.getName()));
					break;
				} else {
					for (int i = 0; i < presented.size(); i++) {
						if (foeProperties.contains(presented.get(i))) {
							presented.remove(i);
						}
					}
					possible = new String[presented.size()];
					String choice = gui.getUserSelection(text.getString("commenceTrade"), presented.toArray(possible));
					if (!choice.equals(text.getString("none"))) {
						foeProperties.add(choice);
					} else {
						break;
					}
				}
			} else {
				gui.showMessage(text.getFormattedString("noPropOwnedByFoe",offeree.getName()));
				break;
			}
		} while (gui.getUserLeftButtonPressed(text.getString("moreProperties"), text.getString("Yes"), text.getString("No")));
		return foeProperties;
	}

}
