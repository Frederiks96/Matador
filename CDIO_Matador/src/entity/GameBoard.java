package entity;

import entity.Texts;
import entity.fields.AbstractFields;
import entity.fields.Brewery;
import entity.fields.Fleet;
import entity.fields.Refuge;
import entity.fields.Tax;
import entity.fields.Territory;

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

	public void setupBoard(Texts text ) {

		for  (int i = 0; i < logicFields.length; i++){

			if (i == 5 || i==15 || i==25 || i==35){
				logicFields[i] = new Fleet(i);
			}

			if (i == 4 || i == 38){
				logicFields[i] = new Tax(i);
			}

			if (i == 30){
				logicFields[i] = new Refuge(i);
			}
			
			if (i == 12 || i == 28){
				logicFields[i] = new Brewery(i,text);
			}
			
			else {
				logicFields[i] = new Territory(i,text.getInfo(i + "_color"), text.getInfo(i + "_name"),text.getInfo(i + "_price"),text.getInfo(i + "_0"), text.getInfo(i + "_1"), text.getInfo(i + "_2"),text.getInfo(i + "_3"), text.getInfo(i + "_4"), text.getInfo(i + "_5"),text.getInfo(i + "_house")  )
				
				
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

