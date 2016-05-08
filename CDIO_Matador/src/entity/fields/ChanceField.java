package entity.fields;

import boundary.GUI_Commands;
import entity.GameBoard;
import entity.Player;
import entity.Texts;

public class ChanceField extends AbstractFields {
	private String name;
	
	public ChanceField(int id, Texts text) {
		super(id);
		this.name = (String) text.getInfo(id+"_name");
	}

	@Override
	public void landOnField(Player player, Texts text, GUI_Commands gui, GameBoard board) {
		gui.showMessage(text.getFormattedString("land",this.name));
		String cardText = board.drawCard(player);
		
		if (cardText.equals(text.getCardString("k1"))) {
			
		} else if (cardText.equals(text.getCardString("k2"))) {
			
		} else if (cardText.equals(text.getCardString("k3"))) {
			
		} else if (cardText.equals(text.getCardString("k5"))) {
			
		} else if (cardText.equals(text.getCardString("k6"))) {
			
		} else if (cardText.equals(text.getCardString("k7"))) {
			
		} else if (cardText.equals(text.getCardString("k8"))) {
			
		} else if (cardText.equals(text.getCardString("k10"))) {
			
		} else if (cardText.equals(text.getCardString("k11"))) {
			
		} else if (cardText.equals(text.getCardString("k12"))) {
			
		} else if (cardText.equals(text.getCardString("k13"))) {
			
		} else if (cardText.equals(text.getCardString("k14"))) {
			
		} else if (cardText.equals(text.getCardString("k15"))) {
			
		} else if (cardText.equals(text.getCardString("k17"))) {
			
		} else if (cardText.equals(text.getCardString("k18"))) {
			
		} else if (cardText.equals(text.getCardString("k20"))) {
			
		} else if (cardText.equals(text.getCardString("k21"))) {
			
		} else if (cardText.equals(text.getCardString("k22"))) {
			
		} else if (cardText.equals(text.getCardString("k23"))) {
			
		} else if (cardText.equals(text.getCardString("k24"))) {
			
		} else if (cardText.equals(text.getCardString("k25"))) {
			
		} else if (cardText.equals(text.getCardString("k26"))) {
			
		} else if (cardText.equals(text.getCardString("k28"))) {
			
		} else if (cardText.equals(text.getCardString("k29"))) {
			
		} else if (cardText.equals(text.getCardString("k30"))) {
			
		} else if (cardText.equals(text.getCardString("k32"))) {
			
		} else if (cardText.equals(text.getCardString("k33"))) {
			
		}
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public int getID() {
		return id;
	}

}
