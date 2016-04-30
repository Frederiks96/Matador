package entity.fields;

import entity.Player;
import entity.Texts;

public class CardField extends AbstractFields {
	private String name;
	
	public CardField(int id, Texts text) {
		super(id);
		this.name = (String) text.getInfo(id+"_name");
	}

	@Override
	public void landOnField(Player player, Texts text) {
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
