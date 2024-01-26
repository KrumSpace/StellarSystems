package demo;

import java.awt.Color;

import baryModel.simpleObjects.BarySimpleObject;
import baryModel.simpleObjects.PhysicalBody;
import org.jetbrains.annotations.NotNull;

import utils.coordinates.Coordinates;
import utils.coordinates.Location;
import utils.coordinates.Velocity;
import baryModel.*;

//
class TestUniverse2 extends BaryUniverse {
    //
    TestUniverse2() {
        super();
        @NotNull BaryObject
                independentObject1 = new BarySimpleObject(
                        this,
                        new Coordinates(
                                new Location.LocationCartesian(500, -200),
                                new Velocity.VelocityCartesian(60, Math.PI)),
                        new PhysicalBody("object-1", 200, 50, Color.CYAN)),
                independentObject2 = new BarySimpleObject(
                        this,
                        new Coordinates(
                                new Location.LocationCartesian(50, -100),
                                new Velocity.VelocityCartesian(20, 0)),
                        new PhysicalBody("object-2", 100, 50, Color.MAGENTA)),
                independentObject3 = new BarySimpleObject(
                        this,
                        new Coordinates(
                                new Location.LocationCartesian(-300, 50),
                                new Velocity.VelocityCartesian(40, 0)),
                        new PhysicalBody("object-3", 150, 50, Color.YELLOW));
        addObject(independentObject1);
        addObject(independentObject2);
        addObject(independentObject3);
    }
}