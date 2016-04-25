package entity.fields;

import entity.Player;
import entity.Texts;

public interface Ownable {
	
	Player getOwner();
	void setOwner(Player owner);
	int getRent();
	boolean isOwned();
	void buyProperty(Player player);
	void mortgage(Texts text);
	void unMortgage();
	
}
