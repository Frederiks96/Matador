package entity.fields;

import boundary.GUI_Commands;
import entity.Player;
import entity.Texts;

public class ChanceField extends AbstractFields {
	private String name;
	
	public ChanceField(int id, Texts text) {
		super(id);
		this.name = (String) text.getInfo(id+"_name");
	}

	@Override
	public void landOnField(Player player, boolean buy, Texts text, GUI_Commands gui) {
		// Du er landet på prøv lykken
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
