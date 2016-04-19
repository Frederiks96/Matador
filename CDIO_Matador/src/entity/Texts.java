package entity;

import java.util.ResourceBundle;

public class Texts {
	
	private enum language {Dansk,English};
	private ResourceBundle rents;
	private ResourceBundle texts;
	private ResourceBundle cards;
	
	public Texts(language lang) {
		rents = ResourceBundle.getBundle("properties.rent");
		texts = ResourceBundle.getBundle("properties."+lang+"_texts");
		cards = ResourceBundle.getBundle("properties."+lang+"_cards");
	}	
	
	public String getString(String key) {
		return texts.getString(key);
	}
	
	public int getRent(String key) {
		return Integer.parseInt(rents.getString(key));
	}
	
	public String getCardString(String key) {
		return cards.getString(key);
	}
	
}
