package battleship;

import battleship.boards.Board;
import battleship.ships.*;

import java.util.*;

public class Game {

    public Game() {
        this.run();
    }

    Map<Integer, Board> boards = new HashMap<>();

    Scanner scanner = new Scanner(System.in);


    private void run() {
        this.init();
        System.out.println("The game starts!");
        int currPlayer = 1;
        while (true) {
            System.out.println("Press Enter and pass the move to another player");
            scanner.nextLine();
            System.out.println("Player " + currPlayer + ", it's your turn:");
            Ship.STATE result = move(currPlayer);
            if (result == Ship.STATE.ALL_SUNK) return;
            currPlayer = opponent(currPlayer);
        }
    }

    private Ship.STATE move(int player) {
        Board board = boards.get(player);
        Board opponentBoard = boards.get(opponent(player));

        printBoard(opponentBoard, player);
        System.out.println("---------------------");
        printBoard(board, player);
        while (true) {
            try {
                String shot = scanner.nextLine();
                Ship.STATE result = opponentBoard.shoot(shot);
                String output = switch (result) {
                    case DOWN -> "You hit a ship!";
                    case SUNK -> "You sank a ship!";
                    case MISSED -> "You missed!";
                    case ALL_SUNK -> "You sank the last ship. You won. Congratulations!";
                };
                System.out.println(output);
                return result;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static int opponent(int player) {
        return player == 1 ? 2 : 1;
    }

    private void init() {
        this.placeUserShips(1);
        System.out.println("Press Enter and pass the move to another player\n" + "...");
        scanner.nextLine();
        this.placeUserShips(2);
        System.out.println("...");
    }

    private void placeUserShips(int player) {
        List<Ship> shipsToSet = new ArrayList<>(List.of(Ship.AircraftCarrier(), Ship.Battleship(), Ship.Submarine(), Ship.Cruiser(), Ship.Destroyer()));
        System.out.printf("Player %d, place your ships on the game field\n", player);
        Board board = new Board(player);
        printBoard(board, player);
        while (!shipsToSet.isEmpty()) {
            Ship ship = shipsToSet.get(0);
            System.out.printf("Enter the coordinates of the %s (%d cells):\n", ship.getName(), ship.getLength());
            try {
                String[] coordinates = scanner.nextLine().split(" ");
                board.placeShip(ship, coordinates);
                shipsToSet.remove(ship);
                System.out.println();
                printBoard(board, player);
            } catch (Exception e) {
                System.out.println("Error! " + e.getMessage() + " Try again:");
            }
        }
        board.complete();
        boards.put(player, board);
    }

    private void printBoard(Board board, int player) {
        System.out.println(board.print(player));
    }
}
