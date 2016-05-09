package entity.fields;

import boundary.GUI_Commands;
import entity.GameBoard;
import entity.Player;
import entity.Texts;

public class ChanceField extends AbstractFields {
	private String name;

	public ChanceField(int id, Texts text) {
		super(id);
		this.name = (String) text.getInfo(id+"_name");
	}

	@Override
	public void landOnField(Player player, Texts text, GUI_Commands gui, GameBoard board) {
		gui.showMessage(text.getFormattedString("land",this.name));
		String cardText = board.drawCard(player);
		gui.showMessage(text.getFormattedString("drawCard",cardText));

		// Vi skal tjekke om spilleren har råd til at betale, hvad der står på kortet

		if (cardText.equals(text.getCardString("k1"))) {
			player.updateBalance(1000);
		} else if (cardText.equals(text.getCardString("k2"))) {
			player.setPosition(0);
			player.updateBalance(4000);
		} else if (cardText.equals(text.getCardString("k3"))) {
			player.imprison();
		} else if (cardText.equals(text.getCardString("k5"))) {
			if (!player.updateBalance(-200)) {
				if (board.netWorth(player)>200) {
					// Kør logik med at spilleren kan vælge hvad han ønsker at sælge
				} else {
					// Bankrupt
				}
			}
		} else if (cardText.equals(text.getCardString("k6"))) {
			if (!player.updateBalance(-2000)) {
				if (board.netWorth(player)>2000) {
					// Kør logik med at spilleren kan vælge hvad han ønsker at sælge
				} else {
					// Bankrupt
				}
			}
		} else if (cardText.equals(text.getCardString("k7"))) {
			player.updateBalance(1000);
		} else if (cardText.equals(text.getCardString("k8"))) {
			player.updateBalance(1000);
		} else if (cardText.equals(text.getCardString("k10"))) {
			// Modtag fra hver medspiller... Hmm, så skal vi have en metode i GameBoard der hedder getPlayers
		} else if (cardText.equals(text.getCardString("k11"))) {
			player.updateBalance(200);
		} else if (cardText.equals(text.getCardString("k12"))) {
			if(!player.updateBalance(-1000)) {
				if (board.netWorth(player)>1000) {
					// Kør logik med at spilleren kan vælge hvad han ønsker at sælge
				} else {
					// Bankrupt
				}
			}
		} else if (cardText.equals(text.getCardString("k13"))) {
			String [] props = board.getOwnedProperties(player);
			if (props != null) {
				int numHouses = 0;
				int numHotels = 0;
				for (int i = 0; i < props.length; i++) {
					if (board.getProperty(props[i]) instanceof Territory) {
						if (((Territory)board.getProperty(props[i])).getHouseCount()==5) {
							numHotels++;
						} else {
							numHouses += ((Territory)board.getProperty(props[i])).getHouseCount();
						}
					}
				}
				if (!player.updateBalance(-(numHouses*800+numHotels*2300))) {
					if (board.netWorth(player)>(numHouses*800+numHotels*2300)) {
						// Kør logik med at spilleren kan vælge hvad han ønsker at sælge
					} else {
						// Bankrupt
					}
				}
			}
		} else if (cardText.equals(text.getCardString("k14"))) {
			if (player.getPosition()>24) {
				player.updateBalance(4000);
			}
			player.setPosition(24);
			board.landOnField(player, text, gui);
		} else if (cardText.equals(text.getCardString("k15"))) {
			if (player.getPosition()>4 && player.getPosition()<15) {
				player.setPosition(15);
			} else if (player.getPosition()>14 && player.getPosition()<25) {
				player.setPosition(25);
			} else if (player.getPosition()>24 && player.getPosition()<35) {
				player.setPosition(35);
			} else {
				if (player.getPosition()<40 && player.getPosition()>34) {
					player.updateBalance(4000);
				}
				player.setPosition(5);
			}
			if (((Ownable)board.getProperty(board.getLogicField(player.getPosition()).getName())).isOwned()) {
				((Ownable)board.getProperty(board.getLogicField(player.getPosition()).getName())).getOwner().addFleet();
				board.landOnField(player, text, gui);
				((Ownable)board.getProperty(board.getLogicField(player.getPosition()).getName())).getOwner().sellFleet();
			} else {
				board.landOnField(player, text, gui);
			}
		} else if (cardText.equals(text.getCardString("k17"))) {
			if (player.getPosition()<40 && player.getPosition()>5) {
				player.updateBalance(4000);
			}
			player.setPosition(5);
			board.landOnField(player, text, gui);
		} else if (cardText.equals(text.getCardString("k18"))) {
			// Benådningskort
		} else if (cardText.equals(text.getCardString("k20"))) {
			player.updateBalance(1000);
		} else if (cardText.equals(text.getCardString("k21"))) {
			if (player.getPosition()>11) {
				player.updateBalance(4000);
			}
			player.setPosition(11);
			board.landOnField(player, text, gui);
		} else if (cardText.equals(text.getCardString("k22"))) {
			player.updateBalance(500);
		} else if (cardText.equals(text.getCardString("k23"))) {
			player.setPosition(39);
			board.landOnField(player, text, gui);
		} else if (cardText.equals(text.getCardString("k24"))) {
			if (player.getPosition()-3<0) {
				player.setPosition(39);
			} else {
				player.setPosition(player.getPosition()-3);
			}
			board.landOnField(player, text, gui);
		} else if (cardText.equals(text.getCardString("k25"))) {
			String [] props = board.getOwnedProperties(player);
			if (props != null) {
				int numHouses = 0;
				int numHotels = 0;
				for (int i = 0; i < props.length; i++) {
					if (board.getProperty(props[i]) instanceof Territory) {
						if (((Territory)board.getProperty(props[i])).getHouseCount()==5) {
							numHotels++;
						} else {
							numHouses += ((Territory)board.getProperty(props[i])).getHouseCount();
						}
					}
				}
				if (!player.updateBalance(-(numHouses*500+numHotels*2000))) {
					if (board.netWorth(player)>(numHouses*500+numHotels*2000)) {
						// Kør logik med at spilleren kan vælge hvad han ønsker at sælge
					} else {
						// Bankrupt
					}
				}
			}
		} else if (cardText.equals(text.getCardString("k26"))) {
			if (!player.updateBalance(-3000)) {
				if (board.netWorth(player)>(3000)) {
					// Kør logik med at spilleren kan vælge hvad han ønsker at sælge
				} else {
					// Bankrupt
				}
			}
		} else if (cardText.equals(text.getCardString("k28"))) {
			if (board.netWorth(player)<15000) {
				player.updateBalance(40000);
			} else {
				gui.showMessage(text.getString("noScholarship"));
			}
		} else if (cardText.equals(text.getCardString("k29"))) {
			player.updateBalance(3000);
		} else if (cardText.equals(text.getCardString("k30"))) {
			player.updateBalance(1000);
		} else if (cardText.equals(text.getCardString("k32"))) {
			if (!player.updateBalance(-1000)) {
				if (board.netWorth(player)>1000) {
					// Kør logik med at spilleren kan vælge hvad han ønsker at sælge
				} else {
					// Bankrupt
				}
			}
		} else if (cardText.equals(text.getCardString("k33"))) {
			if (!player.updateBalance(-200)) {
				if (board.netWorth(player)>200) {
					// Kør logik med at spilleren kan vælge hvad han ønsker at sælge
				} else {
					// Bankrupt
				}
			}
		}
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public int getID() {
		return id;
	}

}
