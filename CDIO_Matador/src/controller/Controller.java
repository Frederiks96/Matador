package controller;

import boundary.GUI_Commands;
import entity.GameBoard;
import entity.Player;
import entity.fields.AbstractFields;
import entity.fields.Brewery;
import entity.fields.Fleet;
import entity.fields.Territory;

public class Controller {

	private GUI_Commands c = new GUI_Commands(); 
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

	private void playerStateMachine(){
		// TODO
	}
	private void arrested(){
		// TODO
	}
	
	public String[] getOwnedProperties(Player player) {
		String[] properties = {""}; // Skal rettes
		fields = gameBoard.getFields();
		int j = 0;
		for (int i = 0; i < fields.length; i++) {
			if (fields[i] instanceof Brewery) {
				if (((Brewery) (fields[i])).getOwner().equals(player)) {
					// properties[j] = fields[i].getName();
					// j++;
				}
			}
			
			if (fields[i] instanceof Fleet) {
				if (((Fleet) (fields[i])).getOwner().equals(player)) {
					if (fields[i] instanceof Fleet) {
						// properties[j] = fields[i].getName();
						// j++;
					}
				}
			}
			
			if (fields[i] instanceof Territory) {
				if (((Territory) (fields[i])).getOwner().equals(player)) {

					if (fields[i] instanceof Territory) {
						// properties[j] = fields[i].getName();
						// j++;
					}
				}
			}
		}
		
		return properties;
	}

}
