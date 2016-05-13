package entity.fields;

import boundary.GUI_Commands;
import entity.GameBoard;
import entity.Player;
import entity.Texts;

public class Tax extends AbstractFields {
	
	private String answer;
	private String name;

	public Tax(int id, Texts text) {
		super(id);
		this.name = (String) text.getInfo(id+"_name");
	}
	
	/**
	 * @inheritDoc
	 */
	@Override
	public void landOnField(Player player, Texts text, GUI_Commands gui, GameBoard board) {
		gui.showMessage(text.getFormattedString("landField", this.id));
		if (this.id==4) {
			answer = gui.getUserButtonPressed(text.getString("taxOrFine"), text.getString("tax"),text.getString("fine"));
			if (answer.equals(text.getString("tax"))) {
				gui.showMessage(text.getString("choseTax"));
				player.updateBalance(-(int)(board.netWorth(player)*0.1));
			} else {
				gui.showMessage(text.getString("choseFine"));
				if (!player.updateBalance(-4000)) {
					if (board.netWorth(player)>4000) {
						board.probateCourt(player, 4000, text, gui);
					} else {
						board.bankrupt(player, null, gui, text);
					}
				}
			}
		} else {
			gui.showMessage(text.getFormattedString("payTax",2000));
			if (!player.updateBalance(-2000)) {
				if (board.netWorth(player)>2000) {
					board.probateCourt(player, 2000, text, gui);
					player.updateBalance(-2000);
					gui.setBalance(player.getName(), player.getBalance());
				} else {
					board.bankrupt(player, null, gui, text);
				}
			}
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
