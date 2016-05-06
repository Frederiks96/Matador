package entity.fields;

import boundary.GUI_Commands;
import entity.GameBoard;
import entity.Player;
import entity.Texts;

public class ChanceField extends AbstractFields {
	private String name;
	
	public ChanceField(int id, Texts text) {
		super(id);
		this.name = (String) text.getInfo(id+"_name");
	}

	@Override
	public void landOnField(Player player, Texts text, GUI_Commands gui, GameBoard board) {
		gui.showMessage(text.getFormattedString("land",this.name));
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
