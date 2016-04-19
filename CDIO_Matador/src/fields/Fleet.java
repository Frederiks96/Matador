package fields;

import desktop_resources.GUI;
import main.ControllerGUI;
import main.Player;

public class Fleet extends AbstractFields implements Ownable {

	private final int BASERENT = 500;
	private Player owner;
	int Price = 4000;
	int id;
	private ControllerGUI myGUI = new ControllerGUI();

	public Fleet(int id) {
		super(id);
		this.owner=null;
	}

	public Player getOwner() {
		return this.owner;
	}

	public void setOwner(Player owner) {
		this.owner=owner;
		owner.setFleets();
	}

	public int getRent() {
		return (int) Math.pow(2, (owner.getFleets()-1))*BASERENT;
	}

	public boolean isOwned() {
		return this.owner==null;
	}
	
	@Override
	public void buyProperty(Player player) {
		this.owner = player;
		GUI.setOwner(id, player.getName());
		player.updateBalance(Price);
		myGUI.showMessage(player.updateBalance(Price));
		player.setFleets();
		
		// TODO Auto-generated method stub
	}

	@Override
	public void landOnField(Player player) {
		if(player.getPosition()==id){
			if(owner == null){
				String s = myGUI.getUserSelection("Do you want to buy fleet? Price: " + Price,"Yes","No");
				if (s == "YES"){
					buyProperty(player);
					
					

				}
			}

		}

	}

	
		
	}
