package entity;

import java.sql.SQLException;
import java.util.ArrayList;

import boundary.GUI_Commands;
import boundary.SQL;
import controller.Controller;

public class Player {

	private String name;
	private int player_id; 							//--> Jeg forestiller mig, at vi i databasen laver 2 nuller foran,
	private int position;
	private static int numOfPlayers = 0;			//    så det kommer til at hedde 001, 002, osv..
	private int numFleetsOwned;
	private int numBreweriesOwned;
	private int numTerritoryOwned;
	private boolean isAlive;
	private boolean turn;
	private Account account;
	private ArrayList<ChanceCard> cards;
	private String vColor;
	private String vType;
	private int accountID;
	private int vID;
	private int jailTime;
	private GUI_Commands myGUI = new GUI_Commands();
	private Controller controller = new Controller();


	public Player(String name, String vColor, String vType) throws SQLException {
		numOfPlayers++;
		this.name = name;
		this.numFleetsOwned = 0;
		this.numBreweriesOwned = 0;
		this.position = 0;
		this.player_id = numOfPlayers;
		this.vID = numOfPlayers*11;
		this.accountID = numOfPlayers*111;
		this.jailTime = -1;
		this.vColor = vColor;
		this.vType = vType;
		this.isAlive=true;
		this.account = new Account(player_id);
		this.cards = new ArrayList<ChanceCard>();
		this.turn = false;

	}

	public int getPlayerID() {
		return this.player_id;
	}

	public void addFleet() {
		this.numFleetsOwned++;
	}

	public int getNumFleetsOwned() {
		return this.numFleetsOwned;
	}

	public void mortgageFleet(){
		numFleetsOwned--;
	}

	public void addBrewery() {
		this.numFleetsOwned++;
	}

	public int getNumBreweriesOwned() {
		return this.numBreweriesOwned;
	}

	public void mortgageBrewery(){
		numBreweriesOwned--;
	}

	public void turn() {
		// Spillerens tur
	}

	public boolean isAlive() {
		return this.isAlive;
	}

	public int getPosition() {
		return this.position;
	}

	public int getAccountID(){
		return account.getAccountID();
	}
	
	public void setPosition(int position) {
		if (position>0 && position<40) {
			if (position != 10) {
				if (position<this.position) {
					updateBalance(-4000);
				}
			}
			this.position = position;
		}
	}

	public void updatePosition(int lastRoll) throws SQLException {
		if (lastRoll>0 && lastRoll<13) {
			if ((position+lastRoll)>39) {
				updateBalance(4000);
				position += lastRoll-40;
			} else {
				position += lastRoll;
			}
		}
	}

	public int getBalance() {
		return account.getBalance();
	}

	public Boolean updateBalance(int d) { 
		return account.updateBalance(d);
	}

	public void giveCard(ChanceCard card) {
		this.cards.add(card);
	}

	public ChanceCard getCard() {
		if (this.cards.size()>0) {
			return this.cards.get(0);
		}
		else 
			return null;
	}

	public void takeCard() {
		this.cards.remove(0);
	}

	public boolean hasCards() {
		return this.cards.size()!=0;
	}

	public String getName() {
		return this.name;
	}

	public void imprison() {
		this.jailTime++;
		setPosition(10);
		myGUI.setCar(10, this.name);
	}

	public Account getAccount(){
		return this.account;
	}

	public boolean hasAll(String COLOUR) {
		return controller.hasAll(this, COLOUR);
	}

	public int getJailTime(){
		return this.jailTime;
	}

	public boolean isTurn() {
		return turn;
	}

	public void setTurn(boolean turn) {
		this.turn = turn;
	}

	public int getNumTerritoryOwned() {
		return numTerritoryOwned;
	}

	public void updateNumTerritoryOwned(int i){
		numTerritoryOwned = numTerritoryOwned + i;	
	}

	public void updateNumBreweriesOwned(int i) {
		numBreweriesOwned = numBreweriesOwned +i;
		
	}

	public void updateNumFleetOwned(int i) {
		numFleetsOwned = numFleetsOwned + i;
	}
	
	public String getVehicleColour() {
		return this.vColor;
	}
	
	public String getVehicleType() {
		return this.vType;
	}
	
	public int getVehicleID() {
		return this.vID;
	}
	
	
}
