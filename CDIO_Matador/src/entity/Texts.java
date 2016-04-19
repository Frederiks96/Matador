package entity;

import java.util.ResourceBundle;

public class Texts {
	
	private enum language {Dansk,English};
	private ResourceBundle rents;
	private ResourceBundle texts;
	
	public Texts(language lang) {
		rents = ResourceBundle.getBundle("properties.rent");
		texts = ResourceBundle.getBundle("properties."+lang+"_texts");
	}	
	
	public String getString(String key) {
		return texts.getString(key);
	}
	
	public int getRent(String key) {
		return Integer.parseInt(rents.getString(key));
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

//	private BufferedReader in;
//	private String valgtSprog;
//	private ControllerGUI c = new ControllerGUI();
//	String[] strings = null;
//
//	public Texts() {
//		valgtSprog = c.getUserSelection("Choose your language", "Dansk", "English");
//	}
//
//	public void openFile() {
//		String file = "Dansk.txt";
//
//		if (this.valgtSprog.equals("English"))
//			file = "English.txt";
//
//		try {
//			in = new BufferedReader(new FileReader(file));
//		} catch (IOException e) {
//			c.showMessage("Something went wrong, please restart the game");
//		}
//	}
//
//	public String[] readStrings() {
//		
//		try {
//			strings = in.readLine().split(";");
//		} catch (IOException e) {
//			c.showMessage("Something went wrong, please restart the game");
//		}
//		
//		return strings;
//	}
//	
//	public void closeFile() {
//		try {
//			if (in!=null)in.close();
//		} catch (IOException e) {
//			c.showMessage("Something went wrong, please restart the game");
//		}
//	}
//	
//	public String getString(int id) {
//		return strings[id];
//	}
	
	


}
