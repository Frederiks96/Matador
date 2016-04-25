package entity;

import java.util.ResourceBundle;

public class Texts {
	
	public enum language {Dansk,English};
	private language l;
	private ResourceBundle fieldInfo;
	private ResourceBundle texts;
	private ResourceBundle cards;
	
	public Texts(language l) {
		this.l=l;
		fieldInfo = ResourceBundle.getBundle("properties.fieldInfo");
		texts = ResourceBundle.getBundle("properties."+l+"_texts");
		cards = ResourceBundle.getBundle("properties."+l+"_cards");
	}	
	
	public String getString(String key) {
		return texts.getString(key);
	}
	
	public String getFormattedString(String key, Object... args) {
		return String.format(texts.getString(key), args);
	}
	
	public Object getInfo(String key) {
		if (Character.isDigit(fieldInfo.getString(key).charAt(0))) {
			return Integer.parseInt(fieldInfo.getString(key));
		}
		return fieldInfo.getString(key);
	}
	
	public String getCardString(String key) {
		return cards.getString(key);
	}
	
	
}
