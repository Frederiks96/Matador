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

	public void suggestDeal(Player offeror, Player offeree, Texts text, GameBoard board, GUI_Commands gui) {
		ownProperties = getOwnProperties(board, offeror, gui, text);
		foeProperties = getFoeProperties(board, offeree, gui, text);

		ownOffer = gui.getUserInteger(text.getString("getOwnOffer"));
		foeOffer = gui.getUserInteger(text.getString("getFoeOffer"));
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

	private void completeDeal(GameBoard board, Player offeror, Player offeree, ArrayList<String> ownProperties, ArrayList<String> foeProperties, int ownOffer, int foeOffer, GUI_Commands gui) {
		// Setting offeror as owner to offeree's properties
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
		// Setting offeree as owner to offeror's properties
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

		offeror.updateBalance(-ownOffer);
		offeree.updateBalance(ownOffer);
		offeree.updateBalance(-foeOffer);
		offeror.updateBalance(foeOffer);

		gui.setBalance(offeror.getName(), offeror.getBalance());
		gui.setBalance(offeree.getName(), offeree.getBalance());
	}

	private ArrayList<String> getOwnProperties(GameBoard board, Player offeror, GUI_Commands gui, Texts text) {
		ownProperties = new ArrayList<String>();
		do {
			ArrayList<String> presented = new ArrayList<String>();
			if (board.getOwnedUnbuiltProperties(offeror) != null) {
				String[] possible = board.getOwnedUnbuiltProperties(offeror);
				if (ownProperties.size() == 0) {
					// Spilleren har endnu ikke tilføjet nogle
					ownProperties.add(gui.getUserSelection(text.getString("commenceTrade"), possible));
				} else {
					// Sorter nogle fra
					String[] a = new String[presented.size()];
					ownProperties.add(gui.getUserSelection(text.getString("commenceTrade"), presented.toArray(a)));
				}
			} else {
				gui.showMessage(text.getString("noPropOwned"));
				break;
			}
		} while (gui.getUserLeftButtonPressed(text.getString("moreProperties"), text.getString("Yes"), text.getString("No")));
		return ownProperties;
	}

	private ArrayList<String> getFoeProperties(GameBoard board, Player offeree, GUI_Commands gui, Texts text) {
		foeProperties = new ArrayList<String>();
		do {
			if (board.getOwnedUnbuiltProperties(offeree)!=null) {
				foeProperties.add(gui.getUserSelection(text.getFormattedString("foeProperties",offeree.getName()), board.getOwnedUnbuiltProperties(offeree)));
			} else {
				gui.showMessage(text.getString("noPropOwnedByFoe"));
				break;
			}

		} while (gui.getUserLeftButtonPressed(text.getString("moreProperties"), text.getString("Yes"), text.getString("No")));
		return foeProperties;
	}

}
