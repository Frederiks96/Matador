package entity.fields;

import entity.Player;

public interface Ownable {
	
	Player getOwner();
	void setOwner(Player owner);
	int getRent();
	boolean isOwned();
	void buyProperty(Player player);
	void mortgage();
	void unMortgage();
	
}
