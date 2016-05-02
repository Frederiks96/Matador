package entity.fields;

import boundary.GUI_Commands;
import entity.Player;
import entity.Texts;

public interface Ownable {
	
	Player getOwner();
	void setOwner(Player owner);
	int getRent();
	boolean isOwned();
	void buyProperty(Player player, Texts text, GUI_Commands gui);
	void mortgage(Texts text, GUI_Commands gui);
	void unMortgage();
	void sellPproperty(Player player);
	
}
