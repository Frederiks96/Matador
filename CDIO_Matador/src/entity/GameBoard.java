package entity;

import entity.fields.AbstractFields;

public class GameBoard {

	private AbstractFields[] logicFields;

//	public GameBoard(Texts text){
		
//		public Territory(int id, String colour, int price, int baseRent,
//		int rent1, int rent2, int rent3, int rent4, int hotelRent, int housePrice){
//			
//		
//		for (int i = 0; i < logicFields.length; i++) {
//			logicFields[i] = new Territory(1,text.getRent(i+"_color")
//		}
//		logicFields[1] = new Territory(1,
//	}
	
	public AbstractFields getLogicField(int i) {
		return logicFields[i];
	}
	
	public AbstractFields[] getFields() {
		return this.logicFields;
	}
	
	public void setupBoard(AbstractFields[] fields) {
		
	}
	

}
