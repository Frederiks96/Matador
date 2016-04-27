package entity;

import java.sql.SQLException;

import boundary.SQL;
import entity.fields.AbstractFields;
import entity.fields.Brewery;
import entity.fields.CardField;
import entity.fields.Fleet;
import entity.fields.Refuge;
import entity.fields.Tax;
import entity.fields.Territory;

public class GameBoard {

	private int hotelCount;
	private int houseCount; 
	private AbstractFields[] logicFields;
	private CardStack deck;
	private SQL sql;


	public void setupBoard(Texts text) {
		logicFields = new AbstractFields[40];
		for  (int i = 0; i < logicFields.length; i++){
			if (i == 5 || i==15 || i==25 || i==35){
				logicFields[i] = new Fleet(i,null, text);
			} else if (i == 4 || i == 38){
				logicFields[i] = new Tax(i, text);
			} else if (i == 0 || i == 10 || i == 20 || i == 30){ 
				logicFields[i] = new Refuge(i, text);
			} else if (i == 2 || i == 7 || i == 17 || i == 22 || i == 33 || i == 36){
				logicFields[i] = new CardField(i,text);
			} else if (i == 12 || i == 28){
				logicFields[i] = new Brewery(i,null, text);
			} else {
				logicFields[i] = new Territory(i,null, text);
			}
		}
	}
	
	public void setupBoard(Texts text, String gameName) throws SQLException {
		logicFields = new AbstractFields[40];
		for  (int i = 0; i < logicFields.length; i++){
			if (i == 5 || i==15 || i==25 || i==35){
				logicFields[i] = new Fleet(i,null, text);
			} else if (i == 4 || i == 38){
				logicFields[i] = new Tax(i, text);
			} else if (i == 0 || i == 10 || i == 20 || i == 30){ 
				logicFields[i] = new Refuge(i, text);
			} else if (i == 2 || i == 7 || i == 17 || i == 22 || i == 33 || i == 36){
				logicFields[i] = new CardField(i,text);
			} else if (i == 12 || i == 28){
				logicFields[i] = new Brewery(i,null, text);
			} else {
				logicFields[i] = new Territory(i,null, text);
				sql = new SQL();
				((Territory) (logicFields[i])).setHouseCount(sql.getFieldHouseCount(((Territory)logicFields[i])));
			}
			for (int j = 0; j < logicFields.length; j++) {
				
			}
		}
	}

	public void countCountBuildings(){
		
	}
	
	
	public int getHotelCount(){
		return hotelCount;
	}

	public void addHotel(){
		hotelCount++;
	}

	public int getHouseCount(){
		return houseCount;
	}

	public void addHouse(){
		houseCount++;
	}

	public AbstractFields getLogicField(int i) {
		return logicFields[i];
	}

	public AbstractFields[] getFields() {
		return this.logicFields;
	}

	public void createCardDeck(Texts text) {
		deck = new CardStack();
		deck.shuffle();
		deck.newDeck(text);
	}
	
	public void loadCardDeck(Texts text) throws SQLException {
		deck = new CardStack();
		deck.loadCards(text);
	}
}