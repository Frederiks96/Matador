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

	/**
	 * @inheritDoc
	 */
	@Override
	public void landOnField(Player player, Texts text, GUI_Commands gui, GameBoard board) {
		if (!isOwned()) {
			boolean buy = gui.getUserLeftButtonPressed(text.getFormattedString("buy",getName(),
					getPrice()),text.getString("Yes"), text.getString("No"));

			if (buy) { // The Brewery is owned and the Player wishes to buy it
				buyProperty(player, text, gui);
			}
		} else if(!isMortgaged && owner!=player && isOwned() && owner.getJailTime() == -1) {
			gui.showMessage(text.getFormattedString("rent", getRent(board), owner.getName()));
			if (!player.updateBalance(-getRent(board))) {
				if (board.netWorth(player)>getRent(board)) {
					board.probateCourt(player, getRent(board), text, gui);
					player.updateBalance(-getRent(board));
					owner.updateBalance(getRent(board));
					gui.setBalance(player.getName(), player.getBalance());
					gui.setBalance(owner.getName(), owner.getBalance());
				}
			} else {
				owner.updateBalance(getRent(board));
				gui.setBalance(player.getName(), player.getBalance());
				gui.setBalance(owner.getName(), owner.getBalance());
			}
		}
	}

	/**
	 * @inheritDoc
	 */
	public void buyProperty(Player player, Texts text, GUI_Commands gui) {
		if(player.getAccount().legalTransaction(-price)){
			player.updateBalance(-price);
			setOwner(player, gui);
		}else gui.showMessage(text.getString("failedTransaction"));
	}

	/**
	 * @inheritDoc
	 */
	public void sellProperty(Player player) {
		player.sellBrewery();
	}

	/**
	 * @inheritDoc
	 */
	public void mortgage(Texts text, GUI_Commands gui) {
		this.isMortgaged=true;
		owner.mortgageBrewery();
		owner.updateBalance((int)(this.price*0.5));
	}

	/**
	 * @inheritDoc
	 */
	public void unMortgage() {
		this.isMortgaged=false;
		owner.addBrewery();
		owner.updateBalance(-(int)(this.price*0.5*1.1));
	}

	/**
	 * @inheritDoc
	 */
	public boolean isMortgaged() {
		return this.isMortgaged;
	}

	/**
	 * @inheritDoc
	 */
	public boolean isOwned() {
		return this.owner != null;
	}

	/**
	 * @inheritDoc
	 */
	public void setOwner(Player player, GUI_Commands gui) {
		this.owner = player;
		player.addBrewery();
		gui.setOwner(id, owner.getName());

	}

	/**
	 * @inheritDoc
	 */
	public Player getOwner() {
		return this.owner;
	}

	/**
	 * @inheritDoc
	 */
	public int getRent(GameBoard board) {
		return owner.getNumBreweriesOwned() * board.getDiceCup().getLastRoll() * 100;
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

	/**
	 * @inheritDoc
	 */
	public int getPrice() {
		return price;
	}

	/**
	 * @inheritDoc
	 */
	public void setMortgage(boolean x) {
		isMortgaged = x;
	}

}
