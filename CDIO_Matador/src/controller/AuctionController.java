package controller;
import boundary.GUI_Commands;
import entity.Player;
import entity.Texts;
import entity.fields.AbstractFields;
import entity.fields.Brewery;
import entity.fields.Fleet;
import entity.fields.Ownable;
import entity.fields.Territory;

public class AuctionController {

	private int currentBid;
	private int pass;
	private int winner;

	public void auction (Player[] players, AbstractFields field, Controller con, GUI_Commands gui, Texts text){

		currentBid = 0;
		pass = 0;
		winner = -1;


		if (legalAuction(field)){

			do {
				for (int i=0; i < players.length; i++) {
					if (players[i].isAlive()){

						boolean choice = gui.getUserLeftButtonPressed(text.getFormattedString("bidQuestion", players[i].getName(),
								field.getName()),text.getString("Yes"),text.getString("No"));

						if (choice) {
							int bid;

							do {
								bid = gui.getUserInteger(text.getFormattedString("bid",players[i].getName(),currentBid));

								if (bid > players[i].getBalance()){
									gui.showMessage(text.getString("failedTransaction"));
								}

								if (bid <= currentBid){
									gui.showMessage(text.getString("lowBid"));
								}

							} while (bid > players[i].getBalance() || bid <= currentBid);

								currentBid = bid;
								winner = i;
							
						} else { 
							pass++;
						}
					}
					if(pass == con.numPlayersAlive()-1) {
						break;
					}
				}
			} while (pass < con.numPlayersAlive()-1);

			gui.showMessage(text.getFormattedString("bidWinner", players[winner].getName(), field.getName(),currentBid));
			
			((Ownable) field).setOwner(players[winner], gui);
			players[winner].updateBalance(-currentBid);
		}
		// Hvad gør vi, hvis det ikke er en legal auction???
	}


	private boolean legalAuction(AbstractFields field){
		if (field instanceof Territory){
			if (((Territory)field).getHouseCount() == 0){
				return true;
			}else return false;
		}else return true;
	}

}
