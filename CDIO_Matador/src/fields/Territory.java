package fields;

import entity.Texts;
import main.Player;

public class Territory extends AbstractFields implements Ownable {
	
	private Player owner;
	private int houseCount;
	private String colour;
	private int[] rent;
	private int price;
	private int housePrice;

	public Territory(int id, int price, String colour, 
			int baseRent, int rent1, int rent2, int rent3, int rent4, int hotelRent, int housePrice){
		
		super(id);
		this.owner = null;
		this.houseCount = 0;
		this.colour = colour;
		
		this.price = price;
		this.rent[0] = baseRent;
		this.rent[1] = rent1;
		this.rent[2] = rent2;
		this.rent[3] = rent3;
		this.rent[4] = rent4;
		this.rent[5] = hotelRent;
		this.housePrice = housePrice;
	}

	public void setOwner(Player owner) {
		this.owner = owner;
	}
	
	public Player getOwner() {
		return this.owner;
	}

	

	public int getRent() {
		// Undersøger om alle grunde er ejet i en farvekombination
		// Undersøger herefter om der er hus på den pågældende grund
		// Beregner/henter herefter lejen fra en fil?
		
		int finalRent=0;
		
		
		
		return finalRent;
	}

	public boolean isOwned() {
		return this.owner == null;
	}

	@Override
	public void landOnField(Player player) {
		if (this.owner==null) {
			// Ingen ejer
		}
		else if (this.owner!=player) {
			// En anden spiller ejer feltet
		}
		else {
			// Spilleren ejer selv feltet
		}
	}
	
	public int getHouseCount() {
		return this.houseCount;
	}
	
	public boolean hasAll() {
		// Returnerer true, hvis en spiller ejer alle felterne, inden for den specifikke farve
		return false;
	}

	@Override
	public void buyProperty() {
		// TODO Auto-generated method stub
		
	}

}
