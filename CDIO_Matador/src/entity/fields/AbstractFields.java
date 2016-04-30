package entity.fields;

import entity.Player;
import entity.Texts;

public abstract class AbstractFields {
	
	protected int id;
	
	public AbstractFields(int id) {
		this.id = id;
	}
	
	abstract public void landOnField(Player player, Texts text);
	
	abstract public String getName();
	
	abstract public int getID();

}
