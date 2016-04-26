package entity.fields;

import entity.Player;
import entity.Texts;

public abstract class AbstractFields {
	
	protected int id;
	
	public AbstractFields(int fieldID) {
		this.id = fieldID;
	}
	
	abstract public void landOnField(Player player, Texts text);
	
	abstract public String getName();
	

}
