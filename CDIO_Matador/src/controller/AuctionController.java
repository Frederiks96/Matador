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

	/**
	 * Creates an auction if the field is eligible. The player that wins the auction is set as owner of the field.
	 * Subtracts the winner's bid from his account.  
	 * 
	 * @param players – The array of players who will be participating in the auction.
	 * @param field – The field that is on auction.
	 * @param gui – The GUI that will receive the bids from the players and display messages to the players
	 * @param text – The text object that specifies which texts will be printed based on the language
	 */
	public void auction (Player[] players, AbstractFields field, GUI_Commands gui, Texts text){

		currentBid = 0;
		pass = 0;
		winner = -1;


		if (legalAuction(field)){

			do {
				for (int i=0; i < players.length; i++) {
					if (players[i].isAlive()) {

						boolean choice = gui.getUserLeftButtonPressed(text.getFormattedString("bidQuestion", players[i].getName(),
								field.getName()),text.getString("Yes"),text.getString("No"));

						if (choice && (players[i].getBalance()<currentBid || players[i].getBalance()==currentBid)) {
							gui.showMessage(text.getString("failedTransaction"));
							pass++;
						} else if (choice) {

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
					if (pass == numPlayersAlive(players) && winner == -1) {
						return;
					} else if (pass >= numPlayersAlive(players)-1 && winner != -1) {
						break;
					}
				}
				
				
			} while (pass < numPlayersAlive(players)-1);

			gui.showMessage(text.getFormattedString("bidWinner", players[winner].getName(), field.getName(),currentBid));

			((Ownable) field).setOwner(players[winner], gui);
			players[winner].updateBalance(-currentBid);
		}
		// Hvad gør vi, hvis det ikke er en legal auction???
	}

	/**
	 * Returns whether the field is eligible for an auction
	 * 
	 * @param field – The field that will be evaluated
	 * @return true while the field is unbuilt
	 */
	private boolean legalAuction(AbstractFields field){
		if (field instanceof Territory){
			if (((Territory)field).getHouseCount() == 0){
				return true;
			}else return false;
		}else return true;
	}
	
	/**
	 * Returns the number of players alive
	 * 
	 * @param players – The array of Players to be examined
	 * @return num
	 */
	private int numPlayersAlive(Player[] players) {
		int num = 0;
		for (int i = 0; i < players.length; i++) {
			if (players[i].isAlive()) {
				num++;
			}
		}
		return num;
	}

}
