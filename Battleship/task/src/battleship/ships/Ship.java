package battleship.ships;

public abstract class Ship {
    private final String name;
    private final int length;

    private final String owner;

    public Ship(String name, int length, String owner) {
        this.name = name;
        this.length = length;
        this.owner = owner;
    }

    public Ship(String name, int length) {
        this(name, length, "player");
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return owner.equals("computer") ? "X" : "O";
    }

    public int getLength() {
        return this.length;
    }
}
