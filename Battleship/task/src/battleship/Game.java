package battleship;

import battleship.boards.Board;
import battleship.ships.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {

    public Game() {
        this.run();
    }

    List<Ship> ships = List.of(new AircraftCarrier(), new Battleship(), new Submarine(), new Cruiser(), new Destroyer());

    Board userBoard = new Board(true);
    Board computerBoard = new Board();

    private void run() {
        this.init();
    }

    private void init() {
        printUserBoard();
        this.placeUserShips();
    }

    private void placeUserShips() {
        Scanner scanner = new Scanner(System.in);
        List<Ship> shipsToSet = new ArrayList<>(ships);
        while (!shipsToSet.isEmpty()) {
            Ship ship = shipsToSet.get(0);
            System.out.printf("Enter the coordinates of the %s (%d cells):\n", ship.getName(), ship.getLength());
            try {
                String[] coordinates = scanner.nextLine().split(" ");
                userBoard.placeShip(ship, coordinates);
                shipsToSet.remove(ship);
                printUserBoard();
            } catch (Exception e) {
//                throw e;
                System.out.println("Error! " + e.getMessage() + " Try again:");
            }
        }
    }

    private void printUserBoard() {
        System.out.println(userBoard);
    }
}
