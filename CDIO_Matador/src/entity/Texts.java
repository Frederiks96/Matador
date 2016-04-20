package entity;

import java.util.ResourceBundle;

public class Texts {
	
	public enum language {Dansk,English};
	private language l;
	private ResourceBundle rents;
	private ResourceBundle texts;
	private ResourceBundle cards;
	
	public Texts(language l) {
		this.l=l;
		rents = ResourceBundle.getBundle("properties.rent");
		texts = ResourceBundle.getBundle("properties."+l+"_texts");
		cards = ResourceBundle.getBundle("properties."+l+"_cards");
	}	
	
	public String getString(String key) {
		return texts.getString(key);
	}
	
	public String getFormattedString(String key, Object... args) {
		return String.format(texts.getString(key), args);
	}
	
	public Object getRent(String key) {
		if (Character.isDigit(rents.getString(key).charAt(0))) {
			return Integer.parseInt(rents.getString(key));
		}
		return rents.getString(key);
	}
	
	public String getCardString(String key) {
		return cards.getString(key);
	}
	
}
