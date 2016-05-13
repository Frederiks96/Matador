package entity.fields;

import boundary.GUI_Commands;
import entity.GameBoard;
import entity.Player;
import entity.Texts;

public class Refuge extends AbstractFields {
	private String name;
	
	public Refuge(int id, Texts text) {
		super(id);
		this.name = (String) text.getInfo(id+"_name");
	}

	/**
	 * @inheritDoc
	 */
	@Override
	public void landOnField(Player player, Texts text, GUI_Commands gui, GameBoard board) {
		if (player.getPosition() == 0) {
			// START - the player receives his money in Player.updatePosition()
			gui.showMessage(text.getString("startLanded"));
		} else if (player.getPosition() == 10) {
			// visit in jail
			gui.showMessage(text.getString("jailVisit"));
		} else if (player.getPosition() == 20) {
			// Fri parking
			gui.showMessage(text.getString("freeParking"));
		} else if (player.getPosition() == 30) {
			// Go to Jail
			gui.showMessage(text.getString("goToJail"));
			gui.removeCar(player.getPosition(), player.getName());
			player.imprison();
			gui.setCar(player.getPosition(), player.getName());
		}
	}
	
	/**
	 * @inheritDoc	
	 */
	@Override
	public String getName() {
		return this.name;
	}
	
	/**
	 * @inheritDoc
	 */
	@Override
	public int getID() {
		return id;
	}
	

}
