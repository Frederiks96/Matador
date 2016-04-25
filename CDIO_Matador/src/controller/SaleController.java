package controller;

import boundary.GUI_Commands;
import entity.GameBoard;
import entity.Player;
import entity.Texts;

public class SaleController {
	private Controller c;
	private String[] ownProperties;
	private String[] foeProperties;
	private String answer;
	private int ownOffer;
	private int foeOffer;

	public SaleController() {
		c = new Controller();
	}

	public void suggestDeal(Player offeror, Player offeree, Texts text, GameBoard board, GUI_Commands gui) {
		int j = 0;
		ownProperties = null;
		
		do {
			ownProperties[j] = gui.getUserSelection(text.getString("commenceTrade"), c.getOwnedProperties(offeror));
			j++;
		} while (gui.getUserLeftButtonPressed(text.getString("moreProperties"), text.getString("Yes"), text.getString("No")));
		j = 0;
		
		do {
			foeProperties[j] = gui.getUserSelection(text.getString("foeProperties"), c.getOwnedProperties(offeree));
			j++;
		} while (gui.getUserLeftButtonPressed(text.getString("moreProperties"), text.getString("Yes"), text.getString("No")));
		
		ownOffer = gui.getUserInteger(text.getString("getOwnOffer"));
		foeOffer = gui.getUserInteger(text.getString("getFoeOffer"));
		gui.showMessage(text.getFormattedString("handover", offeree.getName()));
		answer = gui.getUserSelection(text.getString("accept"),text.getString("Yes"),text.getString("No"),text.getString("counterOffer"));
		
		if (text.getString("Yes").equals(answer)) {
			completeDeal(offeror, offeree, ownProperties, ownOffer,foeOffer);
		} else if (text.getString("counterOffer").equals(answer)) {
			suggestDeal(offeree, offeror, text, board, gui);
		} else {
			gui.showMessage(text.getFormattedString("handover", offeror.getName()));
			gui.showMessage(text.getFormattedString("dealRejected", offeree.getName()));
		}
	}

	private void completeDeal(Player offeror, Player offeree, String[] properties, int ownOffer, int foeOffer) {
		
	}
	
	public void beginAuction() {
		
	}
}
