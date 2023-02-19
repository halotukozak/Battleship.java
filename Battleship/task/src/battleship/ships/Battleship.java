package battleship.ships;

public class Battleship extends Ship {
    private static final String name = "Battleship";
    private static final int length = 4;

    public Battleship() {
        super(name, length);
    }

    public Battleship(String owner) {
        super(name, length, owner);
    }

    @Override
    public Ship copy() {
        return new Battleship("computer");
    }


}
