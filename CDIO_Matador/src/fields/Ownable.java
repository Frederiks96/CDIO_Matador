package fields;

import main.Player;

public interface Ownable {
	
	Player getOwner();
	void setOwner(Player owner);
	int getRent();
	boolean isOwned();
	void buyProperty();
	void buyProperty(Player player);
	
	
}
