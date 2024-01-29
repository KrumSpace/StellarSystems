package demo;

import java.awt.Color;

import org.jetbrains.annotations.NotNull;

import utils.coordinates.Location;
import utils.coordinates.Velocity;
import utils.coordinates.Coordinates;
import baryModel.simpleObjects.PhysicalBody;
import baryModel.simpleObjects.BarySimpleObject;
import baryModel.BaryUniverse;

//
class TestUniverse2 extends BaryUniverse {
    //
    TestUniverse2() {
        super();
        addNewSimpleObject(
                500, -200,
                60, Math.PI,
                new PhysicalBody("object-1", 200, 50, Color.CYAN));
        addNewSimpleObject(
                50, -100,
                20, 0,
                new PhysicalBody("object-2", 100, 50, Color.MAGENTA));
        addNewSimpleObject(
                -300, 50,
                40, 0,
                new PhysicalBody("object-3", 150, 50, Color.YELLOW));
    }

    private void addNewSimpleObject(double x, double y, double speed, double direction, @NotNull PhysicalBody body) {
        addObject(new BarySimpleObject(
                this,
                new Coordinates(
                        new Location.LocationCartesian(x, y),
                        new Velocity.VelocityCartesian(speed, direction)),
                body));
    }
}