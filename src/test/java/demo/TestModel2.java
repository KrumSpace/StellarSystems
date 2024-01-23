package demo;

import java.awt.Color;

import org.jetbrains.annotations.NotNull;

import utils.coordinates.Coordinates;
import utils.coordinates.Location;
import utils.coordinates.Velocity;
import baryModel.*;

//
class TestModel2 extends BaryModel {
    //
    TestModel2() {
        super();
        @NotNull BaryUniverse universe = getUniverse();
        @NotNull BaryObject
                independentObject1 = new BarySimpleObject(
                        universe,
                        new Coordinates(
                                new Location.LocationCartesian(500, -200),
                                new Velocity.VelocityCartesian(60, Math.PI)),
                        new SimpleBody("object-1", 200, 50, Color.CYAN)),
                independentObject2 = new BarySimpleObject(
                        universe,
                        new Coordinates(
                                new Location.LocationCartesian(50, -100),
                                new Velocity.VelocityCartesian(20, 0)),
                        new SimpleBody("object-2", 100, 50, Color.MAGENTA)),
                independentObject3 = new BarySimpleObject(
                        universe,
                        new Coordinates(
                                new Location.LocationCartesian(-300, 50),
                                new Velocity.VelocityCartesian(40, 0)),
                        new SimpleBody("object-3", 150, 50, Color.YELLOW));
        universe.addObject(independentObject1);
        universe.addObject(independentObject2);
        universe.addObject(independentObject3);
    }
}