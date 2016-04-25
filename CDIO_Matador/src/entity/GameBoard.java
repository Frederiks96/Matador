package entity;

import entity.Texts;
import entity.fields.AbstractFields;
import entity.fields.Fleet;
import entity.fields.Tax;

public class GameBoard {

	public int hotelCount;
	public int houseCount; 

	private AbstractFields[] logicFields;


	public AbstractFields getLogicField(int i) {
		return logicFields[i];
	}

	public AbstractFields[] getFields() {
		return this.logicFields;
	}

	public void setupBoard() {

		for  (int i = 0; i < logicFields.length; i++){

			if (i == 5 || i==15 || i==25 || i==35){
				logicFields[i] = new Fleet(i);
			}

			if (i == 4){
				logicFields[i] = new Tax(i);
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

