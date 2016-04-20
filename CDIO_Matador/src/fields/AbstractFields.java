package fields;

import entity.Texts;
import main.Player;

public abstract class AbstractFields {
	
	protected int fieldID;
	
	public AbstractFields(int fieldID) {
		this.fieldID = fieldID;
	}
	
	abstract public void landOnField(Player player, Texts text);
	

}
