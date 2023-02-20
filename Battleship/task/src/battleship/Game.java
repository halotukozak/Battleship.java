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


    List<Ship> ships = List.of(Ship.AircraftCarrier(), Ship.Battleship(), Ship.Submarine(), Ship.Cruiser(), Ship.Destroyer());

    Board userBoard = new Board(true);
    Board computerBoard = new Board();
    Scanner scanner = new Scanner(System.in);


    private void run() {
        this.init();
        System.out.println("The game starts!");
        printBoard(computerBoard);
        System.out.println("Take a shot!");

        while (true) {
            String shot = scanner.nextLine();
            try {
                Ship.STATE result = computerBoard.shoot(shot);
                String output = switch (result) {
                    case DOWN, SUNK -> "You hit a ship!";
                    case MISSED -> "You missed!";
                    case ALL_SUNK -> "You sank the last ship. You won. Congratulations!";
                };
                printBoard(computerBoard);
                System.out.println(output);
                if (result == Ship.STATE.ALL_SUNK) break;
            } catch (Exception ignored) {

            }
        }
    }

    private void init() {
        printBoard(userBoard);
        this.placeUserShips();
        this.placeComputerShips();
    }

    private void placeUserShips() {
        List<Ship> shipsToSet = new ArrayList<>(ships);
        while (!shipsToSet.isEmpty()) {
            Ship ship = shipsToSet.get(0);
            System.out.printf("Enter the coordinates of the %s (%d cells):\n", ship.getName(), ship.getLength());
            try {
                String[] coordinates = scanner.nextLine().split(" ");
                userBoard.placeShip(ship, coordinates);
                Ship copiedShip = ship.copy();
                computerBoard.placeShip(copiedShip, coordinates);
                shipsToSet.remove(ship);
                printBoard(userBoard);
            } catch (Exception e) {
                System.out.println("Error! " + e.getMessage() + " Try again:");
            }
        }
        userBoard.complete();
        computerBoard.complete();

    }

    private void placeComputerShips() {
    }

    private void printBoard(Board board) {
        System.out.println(board);
    }
}
