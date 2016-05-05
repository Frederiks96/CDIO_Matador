package entity.fields;

import boundary.GUI_Commands;
import entity.GameBoard;
import entity.Player;
import entity.Texts;

public interface Ownable {
	
	Player getOwner();
	void setOwner(Player player, GUI_Commands gui);
	boolean isOwned();
	void buyProperty(Player player, Texts text, GUI_Commands gui);
	void mortgage(Texts text, GUI_Commands gui);
	void unMortgage();
	void sellProperty(Player player);
	int getPrice();
	int getRent(GameBoard board);
	boolean isMortgaged();
}
