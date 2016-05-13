package entity.fields;

import boundary.GUI_Commands;
import desktop_resources.GUI;
import entity.GameBoard;
import entity.Player;
import entity.Texts;

/**
 * This class is for the territory fields
 * It counts houses and hotels on the territories
 * @author Benjamin Jensen
 *
 */
public class Territory extends AbstractFields implements Ownable {


	private Player owner;
	private int houseCount;
	private String colour;
	private int[] rent = new int[6];
	private int price;
	private int housePrice;
	private boolean isMortgaged;
	private String name;


	public Territory(int id, Player owner, Texts text){
		super(id);
		this.owner = null;
		this.houseCount = 0;
		this.colour = (String) text.getInfo(id+"_color");
		this.isMortgaged = false;
		this.name = (String) text.getInfo(id+"_name");

		this.price = Integer.parseInt(text.getInfo(id+"_price"));
		this.housePrice = Integer.parseInt(text.getInfo(id+"_house"));

		for (int i = 0; i < rent.length; i++) {
			this.rent[i] = Integer.parseInt(text.getInfo(id+"_"+i));
		}
	}

	/**
	 * @inheritDoc
	 */
	@Override
	public void landOnField(Player player, Texts text, GUI_Commands gui, GameBoard board) {
		gui.showMessage(text.getFormattedString("land", this.name));
		if (!isOwned()) {
			boolean buy = gui.getUserLeftButtonPressed(text.getFormattedString("buy",getName(),
					getPrice()),text.getString("Yes"), text.getString("No"));
			if (buy) {	// The territory is not owned and the player wishes to buy
				buyProperty(player, text, gui);
			}
		} else if (!isMortgaged && owner!=player && isOwned()){	// another player owns the territory
			gui.showMessage(text.getFormattedString("rent", getRent(board), owner.getName()));
			player.updateBalance(-getRent(board));
			owner.updateBalance(getRent(board));
			gui.setBalance(player.getName(), player.getBalance());
			gui.setBalance(owner.getName(), owner.getBalance());
		}
	}
/**
 * @inheritDoc
 */
	public int getRent(GameBoard board) {
		if (board.hasAll(this.owner, this.colour) && this.houseCount == 0) {
			return 2*this.rent[this.houseCount];
		}
		return this.rent[this.houseCount];	
	}
/**
 * @inheritDoc
 */
	public boolean isOwned() {
		return this.owner != null;
	}
	/**
	 * Builds a house if it is allowed and the player has sufficient funds
	 * @param text - The text object that specifies which texts should be shown to the user through the GUI
	 * @param gui – The GUI_Commands object, that controls the GUI and shows messages to the user and gets the user's choice
	 */
	public void buyHouse(Texts text, GUI_Commands gui){ 
		if(owner.getAccount().legalTransaction(-housePrice) && houseCount < 4 && !isMortgaged){
			owner.updateBalance(-housePrice);
			gui.setHouse(id, houseCount+1);
			this.houseCount++;
		}		
		else if (isMortgaged)
			gui.showMessage("failedMortgage");

		else if(houseCount == 4)
			gui.showMessage(text.getString("houseOverLoad"));

		else if(!owner.getAccount().legalTransaction(-housePrice))
			gui.showMessage(text.getString("faildTransaction"));
	}
	/**
	 * Builds a hotel if it is allowed and the player has sufficient funds
	 * @param text - The text object that specifies which texts should be shown to the user through the GUI
	 * @param gui – The GUI_Commands object, that controls the GUI and shows messages to the user and gets the user's choice
	 */
	public void buyHotel(Texts text,GUI_Commands gui){	// has hotel betyder om der skal sættes eller fjernes hotel
		if(owner.getAccount().legalTransaction(-housePrice) && houseCount == 4){
			owner.updateBalance(-housePrice);
			gui.setHotel(id, true);
			this.houseCount++;
		}
		else if (isMortgaged)
			gui.showMessage(text.getString("failedMortgage"));

		else if(houseCount < 4)
			gui.showMessage(text.getString("needMoreHouse"));

		else if(!owner.getAccount().legalTransaction(-housePrice))
			gui.showMessage(text.getString("faildTransaction"));
		else if(houseCount == 5) {
			gui.showMessage(text.getString("doubleHotel"));
		}
	}
	/**
	 * Sells a house and updates the player's balance
	 * @param gui – The GUI_Commands object, that controls the GUI and shows messages to the user and gets the user's choice
	 */
	public void sellHouse(GUI_Commands gui){
		if(houseCount>0){
			owner.updateBalance(housePrice/2);
			gui.setHouse(id, houseCount-1);
			this.houseCount--;
		}

	}
	/**
	 * Sells a hotel and updates the player's balance
	 * @param gui – The GUI_Commands object, that controls the GUI and shows messages to the user and gets the user's choice
	 */
	public void sellHotel(GUI_Commands gui){
		if(houseCount == 5){
			owner.updateBalance(housePrice/2);
			gui.setHotel(id, false);
			this.houseCount--;
			gui.setHouse(id, houseCount);
		}
	}
	/**
	 * @inheritDoc
	 */
	public void buyProperty(Player player, Texts text, GUI_Commands gui){
		if (player.getAccount().legalTransaction(-this.price)){
			player.updateBalance(-this.price);
			setOwner(player,gui);
		}else gui.showMessage(text.getString("failedTransaction"));
	}
	/**
	 * @inheritDoc
	 */
	public void sellProperty(Player player){
		player.sellTerritory();
	}
	/**
	 * @inheritDoc
	 */
	public void mortgage(Texts text, GUI_Commands gui) { 
		if (houseCount == 0) {
			isMortgaged = true;
			owner.updateBalance((int)(price*0.5));
		}
		else {
			gui.showMessage(text.getString("errorHouseOnField"));
		}
	}
	/**
	 * @inheritDoc
	 */
	public void unMortgage() {
		isMortgaged = false;
		this.owner.updateBalance(-(int)(this.price*0.5*1.1));
	}
	/**
	 * @inheritDoc
	 */
	public void setOwner(Player player, GUI_Commands gui) {
		this.owner = player;
		player.addTerritory();
		gui.setOwner(id, player.getName());
	}
	/**
	 * @inheritDoc
	 */
	public Player getOwner() {
		return this.owner;
	}
	/**
	 * @return the colour of the field
	 */
	public String getColour(){
		return colour;
	}
	/**
	 * @inheritDoc
	 */
	@Override
	public String getName() {
		return this.name;
	}
	/**
	 * @inheritDoc
	 */
	public boolean isMortgaged() {
		return this.isMortgaged;
	}
	/**
	 * @inheritDoc
	 */
	@Override
	public int getID() {
		return this.id;
	}
	/**
	 * 
	 * @param houseCount - counts the amount of houses on the field
	 */
	public void setHouseCount(int houseCount) {
		this.houseCount = houseCount;
	}
	/**
	 * 
	 * @return - the amount of houses on the field
	 */
	public int getHouseCount() {
		return houseCount;
	}
	/**
	 * @inheritDoc
	 */
	public int getPrice() {
		return price;
	}
	/**
	 * 
	 * @return - The price of a house
	 */
	public int getHousePrice(){
		return housePrice;
	}
	/**
	 * @inheritDoc
	 */
	public void setMortgage(boolean x) {
		isMortgaged = x;
	}
}
