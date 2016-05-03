package entity.fields;

import boundary.GUI_Commands;
import entity.Player;
import entity.Texts;

public class Fleet extends AbstractFields implements Ownable {

	private final int BASERENT = 500;
	private Player owner;
	private int price;
	private boolean isMortgaged;
	private String name;

	public Fleet(int id, Player owner, Texts text) {
		super(id);
		this.owner=null;
		this.isMortgaged=false;
		this.price = 4000;
		this.name = (String) text.getInfo(id+"_name");
	}
	
	public void landOnField(Player player, Texts text, GUI_Commands gui) {

		if(owner==null) {
			boolean choice = gui.getUserLeftButtonPressed(text.getFormattedString("buy", this.price),
					text.getString("Yes"), text.getString("No"));
			if (choice) buyProperty(player, text, gui);
		}

		if (!isMortgaged && !owner.equals(player)) {

			player.updateBalance(-getRent());
			owner.updateBalance(getRent());
		}
	}

	public void buyProperty(Player player, Texts text, GUI_Commands gui) { // 
		if (player.getAccount().legalTransaction(-price)){
			player.updateBalance(-price);
			gui.setOwner(id, player.getName());
			setOwner(player);
		}
		else 
			gui.showMessage(text.getString("failedTransaction"));
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

	public void mortgage(Texts text, GUI_Commands gui) {
		isMortgaged = true;
		owner.mortgageFleet();
		owner.updateBalance((int) (this.price*0.5));
	}

	public void unMortgage() {
		isMortgaged = false;
		owner.addFleet();
		owner.updateBalance(-(int)(this.price*0.5*1.1)); 
	}
	
	@Override
	public String getName() {
		return this.name;
	}
	
	public boolean isMortgaged() {
		return this.isMortgaged;
	}

	@Override
	public int getID() {
		return id;
	}

	@Override
	public void sellPproperty(Player player) {
		player.updateNumFleetOwned(-1);
	}

	@Override
	public int getPrice() {
		return price;
	}

	
}
