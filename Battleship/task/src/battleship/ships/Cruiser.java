package battleship.ships;

public class Cruiser extends Ship {
    static final String name = "Cruiser";
    static final int length = 3;

    public Cruiser() {
        super(name, length);
    }
    public Cruiser(String owner) {
        super(name, length, owner);
    }

    @Override
    public Ship copy() {
        return new Cruiser("computer");
    }
}
