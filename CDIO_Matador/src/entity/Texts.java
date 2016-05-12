package entity;

import java.util.ResourceBundle;

/**
 * This class is for pulling strings from our properties files
 * @author Benjamin Jensen
 *
 */
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
	/**
	 * 
	 * @param key is what text it searches for in the properties files
	 * @return the string from whatever key was chosen
	 */
	public String getString(String key) {
		return texts.getString(key);
	}
	/**
	 * 	
	 * @param keys the different texts taken from the properties files
	 * @return a string array
	 */
	public String[] getStrings(String...keys) {
		String[] result = new String[keys.length];
		for (int i = 0; i < keys.length; i++) {
			result[i] = texts.getString(keys[i]);
		}
		return result;
	}
	/**
	 * 	
	 * @param key is what text it searches for in the properties files
	 * @param args the amount of arguments
	 * @return the string from the chosen key
	 */
	public String getFormattedString(String key, Object... args) {
		return String.format(texts.getString(key), args);
	}
	/**
	 * 	
	 * @param key is what text it searches for in the properties files
	 * @return The string needed for the field
	 */
	public String getInfo(String key) {
		return fieldInfo.getString(key);
	}
	/**
	 * 	
	 * @param key is what text it searches for in the properties files
	 * @return the string needed for the chance card
	 */
	public String getCardString(String key) {
		return cards.getString(key);
	}
	
	
}
