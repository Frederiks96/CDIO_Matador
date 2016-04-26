package Test;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import entity.Player;
import entity.Texts;
import entity.fields.Territory;
import boundary.SQL;

public class SQLTest {
	
	Player player;
	Territory territory;
	
	@Before
	public void setUp(Texts text) throws Exception {
			player = new Player("Mads","green","ufo");
			territory = new Territory(1,null,text);
	}

			
	@Test 
	public void getBalancetest() {
		
		player.updateBalance(-5000);
			
		try {
			SQL sql = new SQL();
			sql.setBalance(player.getBalance());
			sql.getBalance(player);
		} catch (SQLException e) {
			
			int actual = 10;
			
			e.printStackTrace();
			
		}
		
		expected = player.getBalance();
		
		assertEquals(expected,10);
		
	}
	
	
	@Test //House-count Test
	
	public void houseCountTest(){
		int a = 0;
		try{
			SQL sql = new SQL();
			
			
			//public Territory(int id, Player owner, Texts text)
		}
		
	}
	

}
