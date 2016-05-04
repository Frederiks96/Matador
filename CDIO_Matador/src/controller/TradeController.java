package controller;

import java.sql.SQLException;
import java.util.ArrayList;

import boundary.GUI_Commands;
import entity.GameBoard;
import entity.Player;
import entity.Texts;

public class TradeController {
	private ArrayList<String> ownProperties;
	private ArrayList<String> foeProperties;
	private String answer;
	private int ownOffer;
	private int foeOffer;

	public TradeController() throws SQLException {
	}

	public void suggestDeal(Player offeror, Player offeree, Texts text, GameBoard board, GUI_Commands gui, Controller con) {
		ownProperties = new ArrayList<String>();
		foeProperties = new ArrayList<String>();
		do {
			if (con.getOwnedProperties(offeror)!=null) {
			ownProperties.add(gui.getUserSelection(text.getString("commenceTrade"), con.getOwnedProperties(offeror)));
			} else {
				gui.showMessage(text.getString("noPropOwned"));
				break;
			}
		} while (gui.getUserLeftButtonPressed(text.getString("moreProperties"), text.getString("Yes"), text.getString("No")));
		
		do {
			foeProperties.add(gui.getUserSelection(text.getString("foeProperties"), con.getOwnedProperties(offeree)));
		} while (gui.getUserLeftButtonPressed(text.getString("moreProperties"), text.getString("Yes"), text.getString("No")));
		
		ownOffer = gui.getUserInteger(text.getString("getOwnOffer"));
		foeOffer = gui.getUserInteger(text.getString("getFoeOffer"));
		gui.showMessage(text.getFormattedString("handover", offeree.getName()));
		answer = gui.getUserSelection(text.getString("accept"),text.getString("Yes"),text.getString("No"),text.getString("counterOffer"));
		
		if (text.getString("Yes").equals(answer)) {
			completeDeal(offeror, offeree, ownProperties, ownOffer,foeOffer);
		} else if (text.getString("counterOffer").equals(answer)) {
			suggestDeal(offeree, offeror, text, board, gui,con);
		} else {
			gui.showMessage(text.getFormattedString("handover", offeror.getName()));
			gui.showMessage(text.getFormattedString("dealRejected", offeree.getName()));
		}
	}

	private void completeDeal(Player offeror, Player offeree, ArrayList<String> properties, int ownOffer, int foeOffer) {
		
	}
	
	public void beginAuction() {
		
	}
}
