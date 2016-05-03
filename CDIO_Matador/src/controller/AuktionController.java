package controller;
import boundary.GUI_Commands;
import entity.Player;
import entity.Texts;
import entity.fields.AbstractFields;
import entity.fields.Brewery;
import entity.fields.Fleet;
import entity.fields.Territory;

public class AuktionController {

	Texts text;
	int currentbid = 0;
	int previousbid = -1;
	int pass = 0;
	int winner = -1;

	public void auction (Player[] players, AbstractFields field, Controller con, GUI_Commands gui){

		if (((Territory) field).getHouseCount()==0){


			do {

				for (int i=0; i < players.length; i++) {

					if (players[i].isAlive()){

						boolean choice = gui.getUserLeftButtonPressed(text.getFormattedString("bidQuestion", players[i].getName(),
								field.getName()),text.getString("Yes"),text.getString("No"));

						if (choice){
							int bid = gui.getUserInteger(text.getFormattedString("bid",players[i].getName(),previousbid));

							if (bid > previousbid){
								previousbid = currentbid;
								currentbid = bid;
							}	
						}	
						else pass++;
					}

					winner++;

					if (i == players.length){
						i = 0;
						winner = -1;
					}
				}
			} while (pass < con.numPlayersAlive()-1);

			gui.showMessage(text.getFormattedString("bidWinner", players[winner],field.getName(),currentbid));


			//territory
			if(field instanceof Territory){
				((Territory) field).setOwner(players[winner]);
				players[winner].updateBalance(-currentbid);
			}
			//fleet
			if (field instanceof Fleet){
				((Fleet) field).setOwner(players[winner]);
				players[winner].updateBalance(-currentbid);
			}	
			//brewery
			if (field instanceof Brewery){
				((Brewery) field).setOwner(players[winner]);
				players[winner].updateBalance(-currentbid);
			}		
		}
	}

}
