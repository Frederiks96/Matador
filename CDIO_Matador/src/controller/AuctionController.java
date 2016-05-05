package controller;
import boundary.GUI_Commands;
import entity.Player;
import entity.Texts;
import entity.fields.AbstractFields;
import entity.fields.Brewery;
import entity.fields.Fleet;
import entity.fields.Territory;

public class AuctionController {

	private int currentbid;
	private int pass;
	private int winner;

	public void auction (Player[] players, AbstractFields field, Controller con, GUI_Commands gui, Texts text){

		currentbid = 0;
		pass = 0;
		winner = -1;
		
		if (((Territory) field).getHouseCount()==0){

			do {
				for (int i=0; i < players.length; i++) {
					if (players[i].isAlive()){

						boolean choice = gui.getUserLeftButtonPressed(text.getFormattedString("bidQuestion", players[i].getName(),
								field.getName()),text.getString("Yes"),text.getString("No"));

						if (choice){
							int bid;

							do{
								bid = gui.getUserInteger(text.getFormattedString("bid",players[i].getName(),currentbid));

								if (bid > players[i].getBalance()){
									gui.showMessage(text.getString("failedTransaction"));
								}

								if (bid <= currentbid){
									gui.showMessage(text.getString("lowBid"));
								}
								
							} while (bid > players[i].getBalance() || bid <= currentbid);

							if (bid > currentbid){
								currentbid = bid;
								winner = i;
							}	
						}else pass++;
					}
					if(pass == con.numPlayersAlive()-1)break;
				}
			} while (pass < con.numPlayersAlive()-1);

			gui.showMessage(text.getFormattedString("bidWinner", players[winner].getName(), field.getName(),currentbid));


			//territory
			if(field instanceof Territory){
				((Territory) field).setOwner(players[winner], gui);
				players[winner].updateBalance(-currentbid);
			}
			//fleet
			if (field instanceof Fleet){
				((Fleet) field).setOwner(players[winner], gui);
				players[winner].updateBalance(-currentbid);
			}	
			//brewery
			if (field instanceof Brewery){
				((Brewery) field).setOwner(players[winner], gui);
				players[winner].updateBalance(-currentbid);
			}		
		}
	}

}
