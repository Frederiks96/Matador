package entity;

import java.util.ResourceBundle;

public class Texts {
	
	public enum language {Dansk,English};
	private ResourceBundle fieldInfo;
	private ResourceBundle texts;
	private ResourceBundle cards;
	
	public Texts(language l) {
		fieldInfo = ResourceBundle.getBundle("properties.fieldInfo");
		texts = ResourceBundle.getBundle("properties."+l+"_texts");
		cards = ResourceBundle.getBundle("properties."+l+"_cards");
	}	
	
	public String getString(String key) {
		return texts.getString(key);
	}
	
	public String[] getStrings(String...keys) {
		String[] result = new String[keys.length];
		for (int i = 0; i < keys.length; i++) {
			result[i] = texts.getString(keys[i]);
		}
		return result;
	}
	
	public String getFormattedString(String key, Object... args) {
		return String.format(texts.getString(key), args);
	}
	
	public String getInfo(String key) {
		return fieldInfo.getString(key);
	}
	
	public String getCardString(String key) {
		return cards.getString(key);
	}
	
	
}
