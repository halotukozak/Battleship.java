package battleship.ships;


public class Ship {
    private final String name;
    private final int length;

    private int lives;

    public Ship(String name, int length) {
        this.name = name;
        this.length = length;
        this.lives = length;
    }

    public String getName() {
        return this.name;
    }


    public int getLength() {
        return this.length;
    }


    static public Ship AircraftCarrier() {
        return new Ship(BRAND.AircraftCarrier.name, BRAND.AircraftCarrier.length);
    }

    static public Ship Battleship() {
        return new Ship(BRAND.Battleship.name, BRAND.Battleship.length);
    }

    static public Ship Cruiser() {
        return new Ship(BRAND.Cruiser.name, BRAND.Cruiser.length);
    }


    static public Ship Submarine() {
        return new Ship(BRAND.Submarine.name, BRAND.Submarine.length);
    }

    static public Ship Destroyer() {
        return new Ship(BRAND.Destroyer.name, BRAND.Destroyer.length);
    }

    public STATE hit() {
        this.lives--;
        return lives == 0 ? STATE.SUNK : STATE.DOWN;
    }

    public enum STATE {
        MISSED, DOWN, SUNK, ALL_SUNK
    }

    private enum BRAND {
        AircraftCarrier("Aircraft Carrier", 5),

        Battleship("Battleship", 4),

        Cruiser("Cruiser", 3),


        Submarine("Submarine", 3),

        Destroyer("Destroyer", 2);


        final String name;
        final int length;

        BRAND(String s, int i) {
            this.name = s;
            this.length = i;
        }
    }
}