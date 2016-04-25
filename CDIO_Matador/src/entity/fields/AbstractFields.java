package entity.fields;

import entity.Player;
import entity.Texts;

public abstract class AbstractFields {
	
	protected int fieldID;
	
	public AbstractFields(int fieldID) {
		this.fieldID = fieldID;
	}
	
	abstract public void landOnField(Player player, Texts text);
	

}
