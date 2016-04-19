package fields;

import entity.Texts;
import main.ControllerGUI;
import main.Player;

public class Fleet extends AbstractFields implements Ownable {

	private final int BASERENT = 500;
	private Player owner;
	int Price = 4000;
	private ControllerGUI myGUI = new ControllerGUI();
	private boolean isMortaged;


	public Fleet(int id) {
		super(id);
		this.owner=null;
		this.isMortaged=false;
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
	public void buyProperty(Player player) {
		myGUI.showMessage(player.updateBalance(Price));
		if (player.getAccount().legalTransaction(Price)){
			this.owner = player;
			myGUI.setOwner(this.fieldID, player.getName());
			player.setFleets();
		}

	}


	@Override
	public void landOnField(Player player, Texts text) {
		if(owner.equals(null)) {
			String s = myGUI.getUserSelection("buy fleet? "+ Price,"Yes","No");
			if (s.equals("YES")){
				buyProperty(player);
			}
		}
		if (!isMortaged){

			if (owner.getFleets()==1){
				player.updateBalance(-getRent());
				owner.updateBalance(getRent());
			}
			if (owner.getFleets()==2){
				player.updateBalance(-getRent());
				owner.updateBalance(getRent());
			}
			if (owner.getFleets()==3){
				player.updateBalance(-getRent());
				owner.updateBalance(getRent());
			}
			if (owner.getFleets()==4){
				player.updateBalance(-getRent());
				owner.updateBalance(getRent());
			}

		}

	}
}
