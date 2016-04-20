package main;

import fields.AbstractFields;
import fields.Territory;

public class ControllerLogic {

	private ControllerGUI c = new ControllerGUI(); 
	private GameBoard gameBoard;
	private AbstractFields[] fields;


	public void run() {

	}

	public void playerTurn(Player player) {
		c.closeGUI();
	}

	public boolean hasAll(Player owner, String COLOUR) {
		fields = gameBoard.getFields();
		int j = 0;
		for (int i = 0; i < fields.length; i++) {
			if (fields[i] instanceof Territory) {
				if (((Territory) (fields[i])).getColour().equals(COLOUR)) {
					if (((Territory) (fields[i])).getOwner().equals(owner)) {
						j++;
					}
				}
			}
		}
		if (COLOUR.equals("BLUE") || COLOUR.equals("PURPLE")) {
			return j==2;
		}
		return j==3;
	}
	
	
}
