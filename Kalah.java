package kalah;

import com.qualitascorpus.testsupport.IO;
import com.qualitascorpus.testsupport.MockIO;
import java.util.*;
/**
 * This class is the starting point for a Kalah implementation using
 * the test infrastructure.
 */
public class Kalah {
	// variable used to dictate number of houses played with
	public static final int numberOfHouses = 6;
	// Changeble variables to handicap either player
	public static final int P1NumberOfSeeds = 4;
	public static final int P2NumberOfSeeds = 4;
	public static final int P1StoreSeeds = 0;
	public static final int P2StoreSeeds = 0;
	//Initialises players turn
	public static final String startingPlayer = "P1";

	public static final boolean singlePlayer = true;

	public static void main(String[] args) {
		new Kalah().play(new MockIO());
	}
	public void play(IO io) {
		int input = -1;
		boolean turn = false;
		boolean completed = false;
		String PlayerTurn = "P1";
		boolean invalid;
		Player p1 = new Player(numberOfHouses,P1NumberOfSeeds,P1StoreSeeds);
		Player p2 = new Player(numberOfHouses,P2NumberOfSeeds,P2StoreSeeds);
		Board board = new Board(io,numberOfHouses);
		while(input != 0)
		{
			invalid = false;
			if (input == -1){//Used for start of game
				board.display(p1,p2);
				input = board.userResponse(startingPlayer);
			}
			else{
				if (PlayerTurn.equals("P1") ) {
					if (p1.getHouse()[input - 1].getSeeds() == 0){
						invalid = true;
						turn = true;
					}
					else {
						turn = seedSower(p1, p2, input);
					}
				}
				else{
					if (p2.getHouse()[input - 1].getSeeds() == 0){
						invalid = true;
						turn = true;
					}
					else {
						turn = seedSower(p2, p1, input);
					}
				}
				if (turn == false && invalid == false ){
					if (PlayerTurn.equals("P1")){
						PlayerTurn = "P2";
					}else{
						PlayerTurn = "P1";
					}
				}
				if (invalid == true){
					board.invalidMove();
				}
				if (PlayerTurn.equals("P2") && checkGameStatus(p2) == true){
					completed = true;
				}else if (PlayerTurn.equals("P1") && checkGameStatus(p1) == true){
					completed = true;
				}
				board.display(p1, p2);
				if (completed == true){input = 0;}
				else if (singlePlayer == false || PlayerTurn.equals("P1")){
					input = board.userResponse(PlayerTurn);
				}else{
					input = BMFRobot(p1,p2,board);
				}

			}
		}
		board.gameOver();
		board.display(p1,p2);
		if (completed == true){
			board.gameScore(p1,p2);
		}
	}
 	//Seed sowing game logic
	public boolean seedSower(Player a,Player b, int house){
		int moves = a.getHouse()[house-1].getSeeds();
		int lastSeed = moves - 1;
		int houseNumber;
		int checkHouse;
		int lap = 0;
		boolean repeatTurn = false;
		if (moves == 0){//if house empty tells player to choose again
			return true;
		}
		a.getHouse()[house-1].setSeeds(0);
		for (int i = 0; i < moves ;i++){
			houseNumber = i + house - lap * (numberOfHouses + 1);
			if (houseNumber == numberOfHouses ){//Planting seeds in store or crossing opponent store
				if (lap % 2 == 0) {
					a.getStores().setSeeds(a.getStores().getSeeds() + 1);
				}else{//When on opponent store, save the seed thus increment number of iterations
					moves++;
					lastSeed++;
				}
				if (i == lastSeed){
					repeatTurn = true;
				}
				lap++;
			}
			else if (lap % 2 == 1  ){//Planting seeds in opponent house
				b.getHouse()[houseNumber].setSeeds(b.getHouse()[houseNumber].getSeeds() + 1);
			}
			else{
				checkHouse = numberOfHouses	- houseNumber ;
				//Capture logic
				if (i == lastSeed && a.getHouse()[houseNumber].getSeeds() == 0 && b.getHouse()[checkHouse -1].getSeeds() != 0 ){
					a.getStores().setSeeds(a.getStores().getSeeds() + b.getHouse()[checkHouse - 1].getSeeds() +1);
					b.getHouse()[checkHouse -1].setSeeds(0);
				}else {//Plant seed in own houses travesrsed
					a.getHouse()[houseNumber].setSeeds(a.getHouse()[houseNumber].getSeeds() + 1);
				}
			}
		}
		return repeatTurn;
	}

	public int BMFRobot(Player p1, Player p2,Board b){
		int travelNumber = 0;
		for (int i = 0; i < numberOfHouses; i++){// checking if another turn can be acheived
			travelNumber = i + 1 + p2.getHouse()[i].getSeeds();
			int laps = travelNumber/(2*numberOfHouses+ 2);
			int houseNumber = travelNumber - laps*(2*numberOfHouses +1);
			if (p2.getHouse()[i].getSeeds() == 0 || houseNumber > numberOfHouses + 1){
				continue;
			} else if (houseNumber == numberOfHouses + 1 ){ // last seed placed in store
				b.BMFResponse(i+1,"leads to an extra move");
				return i+1;
			}
		}
		for (int i = 0; i < numberOfHouses;i++){// checking if capture can be made
			travelNumber = i + 1 + p2.getHouse()[i].getSeeds();
			int laps = travelNumber/(2*numberOfHouses+ 2);
			int houseNumber = travelNumber - laps*(2*numberOfHouses +1);
			int checkHouse = numberOfHouses - houseNumber;
			if (p2.getHouse()[i].getSeeds() == 0 || houseNumber > numberOfHouses + 1){
				continue;
			}else if (p2.getHouse()[houseNumber - 1].getSeeds() == 0 && (p1.getHouse()[checkHouse].getSeeds() + laps) != 0){
				b.BMFResponse(i+1,"leads to a capture");
				return i+1;
			}
		}
		for (int i = 0; i < numberOfHouses;i++){// checking if legal move can be made
			if (p2.getHouse()[i].getSeeds() != 0){
				b.BMFResponse(i+1,"is the first legal move");
				return i+1;
			}
		}
		return 0;
	}
	//Checks win conidition
	public boolean checkGameStatus(Player p){
		for (int i = 0; i < numberOfHouses; i++){
			if (p.getHouse()[i].getSeeds() != 0){
				return false;
			}
		}
		return true;
	}
}
