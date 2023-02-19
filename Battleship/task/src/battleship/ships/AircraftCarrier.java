package battleship.ships;

public class AircraftCarrier extends Ship {
    private static final String name = "Aircraft Carrier";
    public static final int length = 5;

    public AircraftCarrier() {
        super(name, length);
    }
    public AircraftCarrier(String owner) {
        super(name, length, owner);
    }

    @Override
    public Ship copy() {
        return new AircraftCarrier("computer") {
        };
    }
}
