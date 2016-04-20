package fields;

import entity.Texts;
import main.ControllerGUI;
import main.Player;

public class Tax extends AbstractFields {
	
	private ControllerGUI myGUI = new ControllerGUI();
	private String answer;

	public Tax(int id) {
		super(id);
	}

	@Override
	public void landOnField(Player player, Texts text) {
		if (this.fieldID==4) {
			answer = myGUI.getUserButtonPressed(text.getString("taxOrFine"), text.getString("tax"),text.getString("fine"));
			if (answer.equals(text.getString("tax"))) {
				player.updateBalance(-(int)(player.getBalance()*0.1));
			} else {
				player.updateBalance(-4000);
			}
		} else {
			myGUI.showMessage(text.getString("land"));
		}
	}



}
