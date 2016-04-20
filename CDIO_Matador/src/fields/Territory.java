package fields;

import main.ControllerGUI;
import main.GameBoard;
import main.Player;

public class Territory extends AbstractFields implements Ownable {
	
	ControllerGUI myGUI = new ControllerGUI();
	GameBoard gameboard = new GameBoard();
	private Player owner;
	private int houseCount;
	private boolean hasHotel; 
	private String colour;
	private int[] rent;
	private int price;
	private int housePrice;
	private boolean mortgaged;

	public Territory(int id, String colour, int price, int baseRent, 
			int rent1, int rent2, int rent3, int rent4, int hotelRent, int housePrice){
		
		super(id);
		this.owner = null;
		this.houseCount = 0;
		this.colour = colour;
		this.mortgaged = false;
		
		this.price = price;
		this.rent[0] = baseRent;
		this.rent[1] = rent1;
		this.rent[2] = rent2;
		this.rent[3] = rent3;
		this.rent[4] = rent4;
		this.rent[5] = hotelRent;
		this.housePrice = housePrice;
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
	

	public int getRent() {
		if(allOwned() && houseCount == 0)
			return this.rent[0]*2;
		else
			return this.rent[this.houseCount];	
	}

	public boolean isOwned() {
		return this.owner != null;
	}

	
	private void buyHouse(){	// housecount betyer hvor mange huse der skal stå på grunden
		if(owner.getAccount().legalTransaction(-housePrice) && houseCount < 4){
			myGUI.showMessage(owner.updateBalance(-housePrice));
			myGUI.setHouse(fieldID, houseCount);
			this.houseCount++;
		}
	}
		
	private void buyHotel(){	// has hotel betyder om der skal sættes eller fjernes hotel
		if(owner.getAccount().legalTransaction(-housePrice) && !hasHotel){
			myGUI.showMessage(owner.updateBalance(-housePrice));
			myGUI.setHouse(fieldID, houseCount);
			hasHotel = true;
		}
	}
	
	public int getHouseCount() {	//unødvendig???
		return this.houseCount;
	}
	
	public boolean allOwned() {
		// Returnerer true, hvis en spiller ejer alle felterne, inden for den specifikke farve
		if(colour.equals("BLUE") || colour.equals("PURPLE")){
			int j = 0;
			for(int i = 0; i<40; i++){
				if(gameboard.getLogicField()[i] instanceof Territory){
					if (gameboard.getLogicField().getColour() == colour && gameboard.getLogicField().getOwner() == owner ){
						j++;
					}
				}
			}  
			return 2 == j;
		}
		
		else{
			int j;
			for(int i = 0; i<40; i++){
				if(gameboard.getLogicField()[i] instanceof Territory){
					if (gameboard.getLogicField().getColour() == colour && gameboard.getLogicField().getOwner() == owner ){
						j++;
					}
				}
			}
			return 3 == j;
		}		
	}

	@Override
	public void buyProperty(Player player) {
		this.owner = player;
		myGUI.showMessage(player.updateBalance(-price));
		
	}

	@Override
	public void mortage() {   // stavefejl
		mortgaged = true;	
	}

	@Override
	public void unMortage() {  // stavefejl
		mortgaged = false;		
	}
	
	public void setOwner(Player owner) {
		this.owner = owner;
	}
	
	public Player getOwner() {
		return this.owner;
	}

	private String getColour(){
		return colour;
	}

	
	
}
