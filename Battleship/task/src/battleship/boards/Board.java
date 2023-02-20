package battleship.boards;

import battleship.ships.Ship;

import java.util.*;
import java.util.stream.Collectors;

public class Board {
    private final List<Row> board = new ArrayList<>(10);
    private final int owner;
    private int liveShips = 0;


    public Board(int user) {
        for (int i = 1; i <= 10; i++) {
            board.add(new Row());
        }
        this.owner = user;
    }

    @Override
    public String toString() {
        return this.print(owner);
    }

    public void placeShip(Ship ship, String[] coordinates) throws Exception {
        if (coordinates.length != 2) throw new Exception("Number of coordinates does not equal 2!");

        List<Coordinate> allCoordinates = getCoordinatesBetween(coordinates[0], coordinates[1]);
        if (allCoordinates.size() != ship.getLength())
            throw new Exception("Wrong length of the " + ship.getName() + "!");
        checkPlace(allCoordinates);
        allCoordinates.forEach(c -> setCell(c, ship, owner));
        liveShips++;
    }

    private void checkPlace(List<Coordinate> coordinates) throws Exception {
        for (Coordinate c : coordinates) {
            char row = c.row;
            char col = c.col;
            if (getCell(row, col) != null) throw new Exception("These cells are already occupied");
            if ((row != 'J' && getCell((char) (row + 1), col) != null) || (row != 'A' && getCell((char) (row - 1), col) != null) || (col != '9' && getCell(row, (char) (col + 1)) != null) || (col != '0' && getCell(row, (char) (col - 1)) != null))
                throw new Exception("You placed it too close to another one.");
        }
    }

    private List<Coordinate> getCoordinatesBetween(String c1, String c2) throws Exception {
        Coordinate start;
        Coordinate stop;
        if (c2.charAt(0) > c1.charAt(0) || c2.length() > c1.length() || (c2.length() == c1.length() && c2.charAt(1) > c1.charAt(1))) {
            start = new Coordinate(c1);
            stop = new Coordinate(c2);
        } else {
            start = new Coordinate(c2);
            stop = new Coordinate(c1);
        }

        List<Coordinate> output = new ArrayList<>();
        if (start.row == stop.row) {
            char row = start.row;
            char col = start.col;
            while (col <= stop.col) {
                output.add(new Coordinate(row, col));
                col++;
            }
        } else if (start.col == stop.col) {
            char row = start.row;
            char col = start.col;
            while (row <= stop.row) {
                output.add(new Coordinate(row, col));
                row++;
            }
        } else throw new Exception("Wrong ship location!");
        return output;


    }

    private Row.Cell getCell(char row, char col) {
        return this.getRow(row).getCell(col);
    }

    private Row.Cell getCell(String c) throws Exception {
        Coordinate coordinate = new Coordinate(c);
        return this.getCell(coordinate.row, coordinate.col);
    }

    private Row getRow(char row) {
        return board.get(row - 'A');
    }

    private void setCell(Coordinate coordinate, Ship ship, int player) {
        this.getRow(coordinate.row).put(coordinate.col, ship, player);
    }

    public Ship.STATE shoot(String shot) throws Exception {
        Row.Cell cell = getCell(shot);
        Ship.STATE result = cell.hit();
        if (result == Ship.STATE.SUNK) liveShips--;
        if (liveShips == 0) return Ship.STATE.ALL_SUNK;
        else return result;
    }

    public void complete() {
        board.forEach(row -> row.complete(owner));
    }

    public String print(int player) {
        StringBuilder output = new StringBuilder();
        output.append("  1 2 3 4 5 6 7 8 9 10");
        output.append(System.getProperty("line.separator"));
        char letter = 'A';
        for (int i = 0; i < 10; i++) {
            output.append(letter);
            output.append(board.get(i).print(player));
            letter++;
            output.append(System.getProperty("line.separator"));
        }
        return output.toString();
    }


    static class Coordinate {
        private final char row; // A-J
        private final char col; // 0-9


        public Coordinate(String c) throws Exception {
            if (!isValidCoordinateRegex(c)) throw new Exception("Coordinate doesn't match pattern!");
            this.row = c.charAt(0);
            this.col = (char) (Integer.parseInt(c.substring(1)) - 1 + '0');
        }

        public Coordinate(char row, char col) {
            this.row = row;
            this.col = col;
        }

        private boolean isValidCoordinateRegex(String c) {
            return c.matches("^[A-J]([1-9]|10)$");
        }
    }


    static class Row {
        List<Cell> content = new ArrayList<>(Collections.nCopies(10, null));


        Cell getCell(char col) {
            return content.get(col - '0');
        }

        void put(char col, Ship ship, int player) {
            Cell cell = new Cell(ship, player);
            content.set(col - '0', cell);
        }

        public void complete(int player) {
            for (int i = 0; i < 10; i++) {
                if (content.get(i) == null) {
                    content.set(i, new Cell(null, player));
                }
            }
        }

        public String print(Integer player) {
            return content.stream().map(cell -> cell != null ? (" " + cell.print(player)) : " ~").collect(Collectors.joining());
        }


        static class Cell {
            private final Ship content;
            private boolean isTouched = false;
            private final int owner;

            protected Cell(Ship ship, int player) {
                this.content = ship;
                this.owner = player;
            }

            public void touch() {
                this.isTouched = true;
            }


            public String print(int player) {
                if (player == owner) {
                    if (content == null) return "~";
                    if (isTouched) return "X";
                    return "O";
                } else {
                    if (!isTouched) return "~";
                    if (content == null) return "M";
                    return "X";
                }
            }

            public Ship.STATE hit() {
                if (!isTouched) {
                    this.touch();
                    if (content == null) return Ship.STATE.MISSED;
                    return content.hit();
                }
                return Ship.STATE.MISSED;
            }
        }
    }
}

