package fields;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Text;

import desktop_resources.GUI;
import entity.Texts;
import main.ControllerGUI;
import main.Player;

public class Fleet extends AbstractFields implements Ownable {

	private final int BASERENT = 500;
	private Player owner;
	int Price = 4000;
	private ControllerGUI myGUI = new ControllerGUI();
	private boolean isMortaged;


	public Fleet(int id) {
		super(id);
		this.owner=null;
		this.isMortaged=false;
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
	public void mortage() {
		// TODO Auto-generated method stub
	}

	@Override
	public void unMortage() {
		// TODO Auto-generated method stub
	}


	@Override
	public void buyProperty(Player player) {
		this.owner = player;
		myGUI.setOwner(this.fieldID, player.getName());
		player.updateBalance(Price);
		myGUI.showMessage(player.updateBalance(Price));
		player.setFleets();

		// TODO Auto-generated method stub
	}


	@Override
	public void landOnField(Player player) {
		if(player.getPosition()==this.fieldID){
			if(owner == null){
				String s = myGUI.getUserSelection("buy fleet? "+ Price,"Yes","No");
				if (s == "YES"){
					buyProperty(player);
				}
			}
			if (!isMortaged){
				
				if (owner.getFleets()==1){
				
			}
				
			}

		}

//.equals

	}
}
