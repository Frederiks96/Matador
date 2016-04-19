package fields;

import main.Player;

public abstract class AbstractFields {
	
	private int id;
	
	public AbstractFields(int id) {
		this.id=id;
	}
	
	abstract public void landOnField(Player player);
	

}
