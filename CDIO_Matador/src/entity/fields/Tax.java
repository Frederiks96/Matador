package entity.fields;

import boundary.GUI_Commands;
import entity.GameBoard;
import entity.Player;
import entity.Texts;

public class Tax extends AbstractFields {
	
	private String answer;
	private String name;

	public Tax(int id, Texts text) {
		super(id);
		this.name = (String) text.getInfo(id+"_name");
	}

	@Override
	public void landOnField(Player player, Texts text, GUI_Commands gui, GameBoard board) {
		gui.showMessage(text.getFormattedString("landField", this.id));
		if (this.id==4) {
			answer = gui.getUserButtonPressed(text.getString("taxOrFine"), text.getString("tax"),text.getString("fine"));
			if (answer.equals(text.getString("tax"))) {
				gui.showMessage(text.getString("choseTax"));
				player.updateBalance(-(int)(board.netWorth(player)*0.1));
			} else {
				gui.showMessage(text.getString("choseFine"));
				player.updateBalance(-4000);
			}
		} else {
			gui.showMessage(text.getFormattedString("payTax",2000));
			player.updateBalance(-2000);
		}
	}
	
	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public int getID() {
		return id;
	}
}
