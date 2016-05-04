package entity.fields;

import boundary.GUI_Commands;
import entity.GameBoard;
import entity.Player;
import entity.Texts;

public abstract class AbstractFields {
	
	protected int id;
	
	public AbstractFields(int id) {
		this.id = id;
	}
	
	abstract public void landOnField(Player player, Texts text, GUI_Commands gui, GameBoard board);
	
	abstract public String getName();
	
	abstract public int getID();
}
