package entity.fields;

import boundary.GUI_Commands;
import entity.GameBoard;
import entity.Player;
import entity.Texts;
import entity.dicecup.DiceCup;

public class Brewery extends AbstractFields implements Ownable {

	private Player owner;
	private int price;
	private boolean isMortgaged;
	private String name;

	public Brewery(int id, Player owner, Texts text) {
		super(id);
		this.owner = null;
		this.isMortgaged = false;
		this.price = 3000;
		this.name = (String) text.getInfo(id+"_name");
	}

	@Override
	public void landOnField(Player player, Texts text, GUI_Commands gui, GameBoard board) {
		if (!isOwned()) {
			boolean buy = gui.getUserLeftButtonPressed(text.getFormattedString("buy",getName(),
					getPrice()),text.getString("Yes"), text.getString("No"));

			if (buy) { // The Brewery is owned and the Player wishes to buy it
				buyProperty(player, text, gui);
			}
		} else if(!isMortgaged && owner!=player && isOwned()) {
			gui.showMessage(text.getFormattedString("rent", getRent(board), owner));
			player.updateBalance(-getRent(board));
			owner.updateBalance(getRent(board));
		}
	}

	public void buyProperty(Player player, Texts text, GUI_Commands gui) {
		if(player.getAccount().legalTransaction(-price)){
			player.updateBalance(-price);
			setOwner(player, gui);
		}else gui.showMessage(text.getString("failedTransaction"));
	}

	@Override
	public void sellProperty(Player player) {
		player.sellBrewery();
	}

	public void mortgage(Texts text, GUI_Commands gui) {
		this.isMortgaged=true;
		owner.mortgageBrewery();
		owner.updateBalance((int)(this.price*0.5));
	}

	public void unMortgage() {
		this.isMortgaged=false;
		owner.addBrewery();
		owner.updateBalance(-(int)(this.price*0.5*1.1));
	}

	public boolean isMortgaged() {
		return this.isMortgaged;
	}

	public boolean isOwned() {
		return this.owner != null;
	}

	public void setOwner(Player player, GUI_Commands gui) {
		this.owner = player;
		player.addBrewery();
		gui.setOwner(id, owner.getName());
		
	}

	public Player getOwner() {
		return this.owner;
	}

	public int getRent(GameBoard board) {
		return owner.getNumBreweriesOwned() * board.getDiceCup().getLastRoll() * 100;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public int getID() {
		return id;
	}

	@Override
	public int getPrice() {
		return price;
	}



}
