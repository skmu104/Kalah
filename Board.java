package kalah;
import com.qualitascorpus.testsupport.IO;
import com.qualitascorpus.testsupport.MockIO;

public class Board {
    Board(IO io, int numberOfHouses)
    {
        setIo(io);
        setNumberOfHouses(numberOfHouses);
    }
    public int numberOfHouses;
    public IO io;
    public final static String orientation = "h";

    //displays the board with the current number of seeds in each house and store
    public void display(Player p1,Player p2){
        if (orientation.equals("h")) {
            horizontalBoard(p1,p2);
        }
        else if (orientation.equals("v")){
            verticalBoard(p1,p2);
        }
    }

    public void horizontalBoard (Player p1, Player p2){
        String Seeds;
        int seeds;
        //****VIEW****//
        //Top row
        io.print("+----+");
        for (int i = 0; i < numberOfHouses; i++) {
            io.print("-------+");
        }
        io.println("----+");
        io.print("| P2 ");
        for (int i = numberOfHouses; i > 0; i--) {
            io.print("| ");
            seeds = p2.getHouse()[i - 1].getSeeds();
            Seeds = Integer.toString(seeds);
            if (seeds > 9) {
                io.print(i + "[" + Seeds + "] ");
            } else {
                io.print(i + "[ " + Seeds + "] ");
            }
        }
        seeds = p1.getStores().getSeeds();
        if (seeds > 9) {
            io.println("| " + p1.getStores().getSeeds() + " |");
        } else {
            io.println("|  " + p1.getStores().getSeeds() + " |");
        }
        io.print("|    |");
        for (int i = 0; i < numberOfHouses - 1; i++) {
            io.print("-------+");
        }
        io.println("-------|    |");
        //Bottom row
        seeds = p2.getStores().getSeeds();
        if (seeds > 9) {
            io.print("| " + p2.getStores().getSeeds() + " ");
        } else {
            io.print("|  " + p2.getStores().getSeeds() + " ");
        }
        for (int i = 1; i < numberOfHouses + 1; i++) {
            io.print("| ");
            seeds = p1.getHouse()[i - 1].getSeeds();
            Seeds = Integer.toString(seeds);
            if (seeds > 9) {
                io.print(i + "[" + Seeds + "] ");
            } else {
                io.print(i + "[ " + Seeds + "] ");
            }
        }
        io.println("| P1 |");
        io.print("+----+");
        for (int i = 0; i < numberOfHouses; i++) {
            io.print("-------+");
        }
        io.println("----+");
    }

    public void verticalBoard (Player p1, Player p2){
        //VIEW
        io.println("+---------------+");
        if(p2.getStores().getSeeds() > 9){
            io.println("|       | P2 " +p2.getStores().getSeeds()+ " |" );
        }else{io.println("|       | P2  " +p2.getStores().getSeeds()+ " |" );}
        io.println("+-------+-------+");
        for (int i = 1; i <= numberOfHouses;i++){
            if (p1.getHouse()[i-1].getSeeds() > 9) {
                io.print("| " + i + "["+ p1.getHouse()[i-1].getSeeds() + "] | ");
            }else{io.print("| " + i + "[ "+ p1.getHouse()[i-1].getSeeds() + "] | ");}
            String iter = Integer.toString(numberOfHouses - i + 1);
            if (p2.getHouse()[numberOfHouses - i].getSeeds() > 9){
                io.println(iter + "[" +p2.getHouse()[numberOfHouses - i].getSeeds() + "] |");
            }else{io.println(iter + "[ " +p2.getHouse()[numberOfHouses - i].getSeeds() + "] |");}
        }

        io.println("+-------+-------+");
        if (p1.getStores().getSeeds() > 9){
            io.println("| P1 " +p1.getStores().getSeeds()+  " |       |");
        }else{io.println("| P1  " +p1.getStores().getSeeds()+  " |       |");}
        io.println("+---------------+");
    }

    public void gameScore(Player p1, Player p2){
        int p1Score = p1.sumSeeds();
        int p2Score = p2.sumSeeds();
        io.println("\tplayer 1:"+ p1Score);
        io.println("\tplayer 2:"+ p2Score);
        if (p1Score > p2Score){
            io.println("Player 1 wins!");
        }
        else if (p1Score < p2Score){
            io.println("Player 2 wins!");
        }
        else{
            io.println("A tie!");
        }
    }

    public int userResponse (String turn){
       return io.readInteger("Player " + turn + "'s turn - Specify house number or 'q' to quit: ", 1, numberOfHouses, 0, "q");
    }

    public void BMFResponse(int houseChosen,String reason){ io.println("Player P2 (Robot) chooses house #"+ houseChosen+ " because it " + reason );}

    public void gameOver(){
        io.println("Game over");
    }

    public void invalidMove(){
        io.println("House is empty. Move again.");
    }

    public void setNumberOfHouses(int numberOfHouses){
        this.numberOfHouses = numberOfHouses;
    }

    public void setIo(IO io) {
        this.io = io;
    }
}
