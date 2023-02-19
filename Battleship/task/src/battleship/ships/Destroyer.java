package battleship.ships;

public class Destroyer extends Ship {
    static String name = "Destroyer";
    static int length = 2;

    public Destroyer() {
        super(name, length);
    }

    public Destroyer(String owner) {
        super(name, length, owner);
    }

    @Override
    public Ship copy() {
       return new Destroyer("computer");
    }
}
