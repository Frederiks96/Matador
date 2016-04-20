package fields;

import entity.Texts;
import main.ControllerGUI;
import main.Player;

public class Fleet extends AbstractFields implements Ownable {

	private final int BASERENT = 500;
	private Player owner;
	private int price;
	private ControllerGUI myGUI = new ControllerGUI();
	private boolean isMortgaged;

	public void landOnField(Player player, Texts text) {

		if(owner.equals(null)) {
			String s = myGUI.getUserSelection(text.getFormattedString("buy",this.price),
					text.getString("Yes"),text.getString("No"));
			if (s.equals(text.getString("Yes"))) {
				buyProperty(player);
			}
		}

		if (!isMortgaged && !this.owner.equals(player)) {

			player.updateBalance(-getRent());
			owner.updateBalance(getRent());
		}
	}

	public Fleet(int id) {
		super(id);
		this.owner=null;
		this.isMortgaged=false;
		this.price = 4000;
	}

	public Player getOwner() {
		return this.owner;
	}

	public void setOwner(Player owner) {
		this.owner=owner;
		owner.addFleet();
	}

	public int getRent() {
		return (int) Math.pow(2, (owner.getFleetsNum()-1))*BASERENT;
	}

	public boolean isOwned() {
		return this.owner==null;
	}

	public void mortgage() {
		isMortgaged = true;
	}

	public void unMortgage() {
		isMortgaged = false;
	}

	public void buyProperty(Player player) { // 
		myGUI.showMessage(player.updateBalance(-this.price));
		if (player.getAccount().legalTransaction(-this.price)){
			myGUI.setOwner(this.fieldID, player.getName());
			setOwner(player);
		}

	}

}
