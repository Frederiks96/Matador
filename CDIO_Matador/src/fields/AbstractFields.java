package fields;

import entity.Texts;
import main.Player;

public abstract class AbstractFields {
	
	protected int fieldID;
	
	public AbstractFields(int id) {
		this.fieldID = id;
	}
	
	abstract public void landOnField(Player player, Texts text);
	

}
