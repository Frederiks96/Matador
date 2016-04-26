package entity.fields;

import boundary.GUI_Commands;
import entity.Player;
import entity.Texts;

public class Fleet extends AbstractFields implements Ownable {

	private final int BASERENT = 500;
	private Player owner;
	private int price;
	private GUI_Commands myGUI = new GUI_Commands();
	private boolean isMortgaged;
	private String name;

	public Fleet(int id, Player owner, Texts text) {
		super(id);
		this.owner=null;
		this.isMortgaged=false;
		this.price = 4000;
		this.name = (String) text.getInfo(id+"_name");
	}
	
	public void landOnField(Player player, Texts text) {

		if(owner.equals(null)) {
			String s = myGUI.getUserSelection(text.getFormattedString("buy",this.price),
					text.getString("Yes"),text.getString("No"));
			if (s.equals(text.getString("Yes"))) {
				buyProperty(player,text);
			}
		}

		if (!isMortgaged && !this.owner.equals(player)) {

			player.updateBalance(-getRent());
			owner.updateBalance(getRent());
		}
	}

	public Player getOwner() {
		return this.owner;
	}

	public void setOwner(Player owner) {
		this.owner=owner;
		owner.addFleet();
	}

	public int getRent() {
		return (int) Math.pow(2, (owner.getNumFleetsOwned()-1))*BASERENT;
	}

	public boolean isOwned() {
		return this.owner==null;
	}

	public void mortgage(Texts text) {
		isMortgaged = true;
		owner.mortgageFleet();
		owner.updateBalance((int) (this.price*0.5));
	}

	public void unMortgage() {
		isMortgaged = false;
		owner.addFleet();
		owner.updateBalance(-(int)(this.price*0.5*1.1)); 
	}

	public void buyProperty(Player player, Texts text) { // 
		if (player.getAccount().legalTransaction(-price)){
			player.updateBalance(-price);
			myGUI.setOwner(id, player.getName());
			setOwner(player);
		}
		else 
			myGUI.showMessage(text.getString("failedTransaction"));
	}
	
	@Override
	public String getName() {
		return this.name;
	}
	
	public boolean isMortgaged() {
		return this.isMortgaged;
	}

}
