package baryModel;

import utils.coordinates.Coordinates;
import utils.coordinates.Location;
import utils.coordinates.Velocity;

import java.awt.Color;

//
public class BaryUniverse extends BarySystem {
    //
    public BaryUniverse() {
        super(
                null,
                new Coordinates(
                        new Location.LocationCartesian(0, 0),
                        new Velocity.VelocityCartesian(0, 0)),
                Color.white);
    }
}