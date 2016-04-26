package entity.fields;

import boundary.GUI_Commands;
import entity.Player;
import entity.Texts;

public class Tax extends AbstractFields {
	
	private GUI_Commands myGUI = new GUI_Commands();
	private String answer;
	private String name;

	public Tax(int id, Texts text) {
		super(id);
		this.name = (String) text.getInfo(id+"_name");
	}

	@Override
	public void landOnField(Player player, Texts text) {
		if (this.id==4) {
			answer = myGUI.getUserButtonPressed(text.getString("taxOrFine"), text.getString("tax"),text.getString("fine"));
			if (answer.equals(text.getString("tax"))) {
				myGUI.showMessage(text.getString("choseTax"));
				player.updateBalance(-(int)(player.getBalance()*0.1));
			} else {
				myGUI.showMessage(text.getString("choseFine"));
				player.updateBalance(-4000);
			}
		} else {
			myGUI.showMessage(text.getFormattedString("payTax",2000));
			player.updateBalance(-2000);
		}
	}
	
	@Override
	public String getName() {
		return this.name;
	}
}
