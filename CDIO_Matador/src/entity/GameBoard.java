package entity;

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


	public AbstractFields getLogicField(int i) {
		return logicFields[i];
	}

	public AbstractFields[] getFields() {
		return this.logicFields;
	}

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
	
	public void setupBoard(Texts text, String gameName) {
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

	public int getHotel(){
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

}

//public Territory(int id, String colour, int price, int baseRent,
//int rent1, int rent2, int rent3, int rent4, int hotelRent, int housePrice){
//	
//
//for (int i = 0; i < logicFields.length; i++) {
//	logicFields[i] = new Territory(i,text.getRent(i+"_color")
//}
//logicFields[1] = new Territory(1,
//}

