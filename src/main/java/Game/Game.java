package Game;

import Units.Mage;
import Units.Shop;
import Units.Warrior;

public class Game {

    public static void main(String[] args) {
        Environment environment = Environment.getEnvironment();
        // ===== BUY PHASE =======

        Shop shop = new Shop();

        Player[] players = new Player[2];
        players[0] = environment.createPlayer();
        players[1] = environment.createPlayer();

        players[0].buyUnits(shop);
        players[1].buyUnits(shop);

        // ===== GAME PHASE =======



        int playerTurn = 0;
        while(true /*Both teams are alive*/) {
            System.out.println("It's player " + playerTurn + "'s turn");
            System.out.println(players[playerTurn]);

            players[playerTurn].playTurn();



            playerTurn++;
            playerTurn %= 2;
        }

    }
}
