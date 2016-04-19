package fields;

import desktop_resources.GUI;
import main.ControllerGUI;
import dicecup.DiceCup;
import main.Player;

public class Brewery extends AbstractFields implements Ownable {
	
	private Player owner;
	private DiceCup dicecup;
	int Price = 3000;
	private ControllerGUI myGUI = new ControllerGUI();
	
	public Brewery(int id) {
		super(id);
		this.owner = null;
	}

	public Player getOwner() {
		return this.owner;
	}

	public void setOwner(Player owner) {
		this.owner = owner;
	}

	public int getRent() {
		return owner.getBreweries()*dicecup.getLastRoll()*100;
	}

	public boolean isOwned() {
		return this.owner == null;
	}
	
	public void buyProperty(Player player) {
		this.owner = player;
		player.updateBalance(Price);
		myGUI.showMessage(player.updateBalance(Price));
		
	}

	@Override
	public void landOnField(Player player) {
		
		
	}

}
