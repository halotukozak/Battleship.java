package battleship.ships;

public class Submarine extends Ship {
    private static final String name = "Submarine";
    public static final int length = 3;
    public Submarine() {
        super(name, length);
    }

    public Submarine(String owner) {
        super(name, length, owner);
    }

    @Override
    public Ship copy() {
        return new Submarine("computer");
    }
}
