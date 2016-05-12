package entity.fields;

import boundary.GUI_Commands;
import entity.GameBoard;
import entity.Player;
import entity.Texts;

public abstract class AbstractFields {
	
	protected int id;
	
	
	/**
	 * Constructs an AbstractFields object with the given id.
	 * 
	 * @param id – The id of the object
	 */
	public AbstractFields(int id) {
		this.id = id;
	}
	
	/**
	 * Specifies the chain of events when a player lands on a specific field
	 * 
	 * @param player – The player who lands on the field
	 * @param text – The text object that specifies which texts should be shown to the user through the GUI
	 * @param gui – The GUI_Commands object, that controls the GUI and shows messages to the user and gets the user's choice 
	 * @param board – The object that holds the fields
	 */
	abstract public void landOnField(Player player, Texts text, GUI_Commands gui, GameBoard board);
	
	/**
	 * Returns the name of the field
	 * 
	 * @return name
	 */
	abstract public String getName();
	
	/**
	 * Returns the id of the field
	 * 
	 * @return id
	 */
	abstract public int getID();
}
