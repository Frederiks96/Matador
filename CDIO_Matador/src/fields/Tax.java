package fields;

import entity.Texts;
import main.ControllerGUI;
import main.Player;

public class Tax extends AbstractFields {
	
	private ControllerGUI myGUI = new ControllerGUI();

	public Tax(int id) {
		super(id);
	}

	@Override
	public void landOnField(Player player, Texts text) {
		if (this.fieldID==4) {
			myGUI.getUserButtonPressed(text.getString("taxOrFine"), text.getString("tax"),text.getString("fine"));
			
		}
	}



}
