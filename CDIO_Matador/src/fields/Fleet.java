package fields;

import entity.Texts;
import main.ControllerGUI;
import main.Player;

public class Fleet extends AbstractFields implements Ownable {

	private final int BASERENT = 500;
	private Player owner;
	private int price;
	private ControllerGUI myGUI = new ControllerGUI();
	private boolean isMortaged;


	public Fleet(int id) {
		super(id);
		this.owner=null;
		this.isMortaged=false;
		this.price = 4000;
	}

	public Player getOwner() {
		return this.owner;
	}

	public void setOwner(Player owner) {
		this.owner=owner;
		owner.setFleets();
	}

	public int getRent() {
		return (int) Math.pow(2, (owner.getFleets()-1))*BASERENT;
	}

	public boolean isOwned() {
		return this.owner==null;
	}

	@Override
	public void mortage() {
		// TODO Auto-generated method stub
	}

	@Override
	public void unMortage() {
		// TODO Auto-generated method stub
	}


	@Override
	public void buyProperty(Player player) { // 
		myGUI.showMessage(player.updateBalance(-this.price));
		if (player.getAccount().legalTransaction(-this.price)){
			myGUI.setOwner(this.fieldID, player.getName());
			setOwner(player);
		}

	}


	@Override
	public void landOnField(Player player, Texts text) {

		if(owner.equals(null)) {
			String s = myGUI.getUserSelection(text.getFormattedString("buy",this.price),
					text.getString("Yes"),text.getString("No"));
			if (s.equals(text.getString("Yes"))) {
				buyProperty(player);
			}
		}

		if (!isMortaged && !this.owner.equals(player)) {

			player.updateBalance(-getRent());
			owner.updateBalance(getRent());
		}

	}
}
