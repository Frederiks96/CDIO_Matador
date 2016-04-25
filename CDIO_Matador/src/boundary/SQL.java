package boundary;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import controller.DAO;
import controller.DTO;
import entity.Player;

public class SQL implements DAO, DTO {

	private Connection myCon;

	public SQL() throws SQLException {
//		this.myCon = DriverManager.getConnection("jdbc:mysql://localhost/Matador","root","");
	}


	// >>>> Data acces objects <<<< //

	public int getPosition(Player player) throws SQLException { 
		Statement stmt = myCon.createStatement(); 
		ResultSet rs = stmt.executeQuery("Select position from player where player_id = '"+player.getPlayerID()+"'");
		rs.next();
		return rs.getInt(1);
	}

	public int getBalance()throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getJailTime() throws SQLException{
		// TODO Auto-generated method stub
		return 0;
	}

	public int getCardId()throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getCardPosition() throws SQLException{
		// TODO Auto-generated method stub
		return 0;
	}

	public int getFieldId()throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getHouseCount() throws SQLException{
		// TODO Auto-generated method stub
		return 0;
	}

	public boolean hasHotel() throws SQLException{
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isMortgaged()throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public int getVehicleID() throws SQLException{
		// TODO Auto-generated method stub
		return 0;
	}

	public String getVehicleColour()throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public String getVehicleType() throws SQLException{
		// TODO Auto-generated method stub
		return null;
	}

	public int getAccountId()throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	} 

	// ----------------------------------	
	//   >>> Data transfer objects <<<<
	// ----------------------------------

	public void updatePosition() throws SQLException{
		// TODO Auto-generated method stub

	}

	public void setBalance()throws SQLException {
		// TODO Auto-generated method stub

	}

	public void setJailTime()throws SQLException {
		// TODO Auto-generated method stub

	}

	public void setCardId() throws SQLException{
		// TODO Auto-generated method stub

	}

	public void setCardPosition() throws SQLException{
		// TODO Auto-generated method stub
	}

	public void setFieldId() throws SQLException{
		// TODO Auto-generated method stub
	}

	public void setHouseCount() throws SQLException{
		// TODO Auto-generated method stub
	}

	public void buildHotel()throws SQLException {
		// TODO Auto-generated method stub
	}

	public void mortgage()throws SQLException {
		// TODO Auto-generated method stub
	}

	public void setVehicleID() throws SQLException{
		// TODO Auto-generated method stub

	}

	public void setVehicleColour() throws SQLException{
		// TODO Auto-generated method stub

	}

	public void setVehicleType() throws SQLException{
		// TODO Auto-generated method stub

	}

	public void setAccountId() throws SQLException{
		// TODO Auto-generated method stub

	}


	public void createPlayer(int id, String name, int position, int jailTime, boolean isActive, int aId, 
			int balance, int vId, String vColor, String vType) throws SQLException {
		Statement stmt = myCon.createStatement(); 
		createAccount(aId, balance);
		createVehicle(vId, vColor, vType);
		stmt.executeQuery("insert into player values(" +id+ ","+name+ ","+position+","
				+ jailTime +"," +isActive+ ");" 
				); 

	}

	public void createAccount(int aId, int balance) throws SQLException {
		Statement stmt = myCon.createStatement();
		stmt.executeQuery("insert into Bank values("+aId+","+balance+");");

	}

	public void createVehicle(int vId, String vColor, String vType) throws SQLException {
		Statement stmt = myCon.createStatement();
		stmt.executeQuery("insert into Vehicle values("+vId+","+vColor+","+vType+");");
	}
}