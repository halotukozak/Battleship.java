package battleship.boards;

import battleship.ships.Ship;

import java.util.*;

public class Board {
    private final List<Row> board = new ArrayList<>(10);
    private boolean user = false;

    //    public <T extends Row> Board(Class<T> c) {
//        try {
//            for (int i = 1; i <= 10; i++) {
//                board.add(c.getDeclaredConstructor().newInstance());
//            }
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
    public Board() {
        try {
            for (int i = 1; i <= 10; i++) {
                board.add(new Row());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Board(boolean user) {
        this();
        this.user = user;
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        output.append("  1 2 3 4 5 6 7 8 9 10");
        output.append(System.getProperty("line.separator"));
        for (int i = 0; i < 10; i++) {
            output.append(board.get(i));
            output.append(System.getProperty("line.separator"));
        }
        return output.toString();
    }

    public void placeShip(Ship ship, String[] coordinates) throws Exception {
        if (coordinates.length != 2) throw new Exception("Number of coordinates does not equal 2!");

        List<Coordinate> allCoordinates = getCoordinatesBetween(coordinates[0], coordinates[1]);
        if (allCoordinates.size() != ship.getLength())
            throw new Exception("Wrong length of the " + ship.getName() + "!");
        checkPlace(allCoordinates);
        allCoordinates.forEach(c -> setCell(c, ship, user));
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

    private Row getRow(char row) {
        return board.get(row - 'A');
    }

    private void setCell(Coordinate coordinate, Ship ship, boolean user) {
        this.getRow(coordinate.row).put(coordinate.col, ship, user);
    }


    static class Coordinate {
        private final char row; // A-J
        private final char col; // 0-9


        public Coordinate(String c) throws Exception {
            if (!isValidCoordinateRegex(c)) throw new Exception("Coordinate doesn't match pattern!");
            this.row = c.charAt(0);
            this.col = (char) (Integer.parseInt(c.substring(1)) + '0');
        }

        public Coordinate(char row, char col) {
            this.row = row;
            this.col = (char) (col - 1);
        }

        private boolean isValidCoordinateRegex(String c) {
            return c.matches("^[A-J]([1-9]|10)$");
        }
    }


    static class Row {
        char letter;
        static char lastLetter = '@';
        List<Cell> content = new ArrayList<>(Collections.nCopies(10, null));

        public Row() {
            this.letter = ++lastLetter;
        }

        @Override
        public String toString() {
            StringBuilder output = new StringBuilder();
            output.append(letter);
            for (Cell cell : content) {
                output.append(" ");
                output.append(cell != null ? cell : '~');
            }
            return output.toString();
        }

        Cell getCell(char col) {
            return content.get(col - '0');
        }

        void put(char col, Ship ship, boolean user) {
            Cell cell = new Cell(ship, user);
            content.set(col - '0', cell);
        }


        static class Cell {
            private final Ship content;
            private boolean isTouched = false;

            protected Cell(Ship ship) {
                this.content = ship;
            }

            protected Cell(Ship ship, boolean isTouched) {
                this(ship);
                if (isTouched) this.touch();
            }

            public void touch() {
                this.isTouched = true;
            }

            @Override
            public String toString() {
                if (!isTouched) return "~";
                if (content == null) return "M";
                return content.toString();
            }
        }
    }
}

