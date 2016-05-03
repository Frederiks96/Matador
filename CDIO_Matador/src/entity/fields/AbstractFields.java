package entity.fields;

import boundary.GUI_Commands;
import entity.Player;
import entity.Texts;

public abstract class AbstractFields {
	
	protected int id;
	
	public AbstractFields(int id) {
		this.id = id;
	}
	
	abstract public void landOnField(Player player, boolean buy, Texts text, GUI_Commands gui);
	
	abstract public String getName();
	
	abstract public int getID();
}
