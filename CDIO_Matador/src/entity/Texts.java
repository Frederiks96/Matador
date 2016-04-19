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
	
}
