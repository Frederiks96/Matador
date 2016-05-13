package entity;

import java.util.ResourceBundle;

/**
 * The class manages the internationalization of the game through properties files 
 * @author Frederik
 *
 */
public class Texts {
	
	/**
	 * 
	 * This class has created an enum containing the languages the game supports
	 *
	 */
	
	public enum language {Dansk,English};
	private ResourceBundle fieldInfo;
	private ResourceBundle texts;
	private ResourceBundle cards;
	
	/**
	 * The constructor constructs an object that determines the language of the texts returned to the user.
	 * 
	 * @param l – The enum specifying the language of the game
	 */
	public Texts(language l) {
		fieldInfo = ResourceBundle.getBundle("properties.fieldInfo");
		texts = ResourceBundle.getBundle("properties."+l+"_texts");
		cards = ResourceBundle.getBundle("properties."+l+"_cards");
	}
	
	/**
	 *Returns the string specified by the key in the *_texts file
	 * 
	 * @param key – The keyword to search for in the file
	 * @return The corresponding string
	 */
	public String getString(String key) {
		return texts.getString(key);
	}
	
	/**
	 * Returns multiple strings in a String array.	
	 * 
	 * @param keys – The The keywords to search for in the file
	 * @return The strings specified by the keys, in a String array
	 */
	public String[] getStrings(String...keys) {
		String[] result = new String[keys.length];
		for (int i = 0; i < keys.length; i++) {
			result[i] = texts.getString(keys[i]);
		}
		return result;
	}
	
	/**
	 * Formats a string by inserting strings or numbers in the string
	 * 	
	 * @param key – The keyword to search for in the file
	 * @param args – The Objects to be inserted in the strings
	 * @return The formatted String
	 */
	public String getFormattedString(String key, Object... args) {
		return String.format(texts.getString(key), args);
	}
	
	/**
	 * Returns the string of the corresponding keyword in the fieldInfo file
	 * 	
	 * @param key – The keyword to search for in the file
	 * @return The corresponding string
	 */
	public String getInfo(String key) {
		return fieldInfo.getString(key);
	}
	
	/**
	 * Returns the string of the corresponding keyword in the *_cards file
	 * 	
	 * @param key – The keyword to search for in the file
	 * @return The corresponding string
	 */
	public String getCardString(String key) {
		return cards.getString(key);
	}
	
	
}
