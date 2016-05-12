package entity.fields;

import boundary.GUI_Commands;
import entity.GameBoard;
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
	
	/**
	 * @inheritDoc
	 */
	@Override
	public void landOnField(Player player, Texts text, GUI_Commands gui, GameBoard board) {
		gui.showMessage(text.getFormattedString("land", this.name));
		if (!isOwned()) {
			boolean buy = gui.getUserLeftButtonPressed(text.getFormattedString("buy",getName(),
					getPrice()),text.getString("Yes"), text.getString("No"));
			if (buy) {	// The Fleet is not Owned, and the player wishes to buy it
				buyProperty(player, text, gui);
			}
		} else if (!isMortgaged && owner!=player && isOwned()) {	//another player owns the Fleet
			gui.showMessage(text.getFormattedString("rent", getRent(board), owner.getName()));
			if (player.updateBalance(-getRent(board))) {
				owner.updateBalance(getRent(board));
				gui.setBalance(player.getName(), player.getBalance());
				gui.setBalance(owner.getName(), owner.getBalance());
			} else {
				if (board.netWorth(player)>getRent(board)) {
					
				} else {
					// Player is done
					player.bankrupt();
				}
			}
		}
	}

	/**
	 * @inheritDoc
	 */
	public void buyProperty(Player player, Texts text, GUI_Commands gui) { // 
		if (player.getAccount().legalTransaction(-price)){
			player.updateBalance(-price);
			setOwner(player, gui);
		}else gui.showMessage(text.getString("failedTransaction"));
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
	public void setOwner(Player owner, GUI_Commands gui) {
		this.owner=owner;
		owner.addFleet();
		gui.setOwner(id, owner.getName());
	}
	
	/**
	 * @inheritDoc
	 */
	public int getRent(GameBoard board) {
		return (int) Math.pow(2, (owner.getNumFleetsOwned()-1))*BASERENT;
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
	public void mortgage(Texts text, GUI_Commands gui) {
		isMortgaged = true;
		owner.sellFleet();
		owner.updateBalance((int) (this.price*0.5));
	}

	/**
	 * @inheritDoc
	 */
	public void unMortgage() {
		isMortgaged = false;
		owner.addFleet();
		owner.updateBalance(-(int)(this.price*0.5*1.1)); 
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
	public boolean isMortgaged() {
		return this.isMortgaged;
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
	public void sellProperty(Player player) {
		player.sellFleet();
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
