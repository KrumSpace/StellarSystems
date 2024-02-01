package demo;

import java.awt.Color;

import org.jetbrains.annotations.NotNull;

import utils.coordinates.Location;
import utils.coordinates.Velocity;
import utils.coordinates.Coordinates;
import baryModel.BaryObject;
import baryModel.simpleObjects.PhysicalBody;
import baryModel.simpleObjects.BarySimpleObject;
import baryModel.systems.AbstractBarySystem;
import baryModel.systems.BarySystem;
import baryModel.BaryUniverse;

//
class TestUniverse1 extends BaryUniverse {
    //
    TestUniverse1() {
        super();
        @NotNull BaryObject independentObject = new BarySimpleObject(
                this,
                new Coordinates(
                        new Location.LocationPolar(500, Math.PI),
                        new Velocity.VelocityRadial(0.3, 0)),
                new PhysicalBody("object-1", 100, 50, Color.CYAN));
        addObject(independentObject);

        @NotNull AbstractBarySystem system = new BarySystem(
                this,
                new Coordinates(
                        new Location.LocationPolar(220, 0),
                        new Velocity.VelocityRadial(0.6, 0)),
                Color.MAGENTA);
        @NotNull BaryObject
                dependentObject1 = new BarySimpleObject(
                        system,
                        new Coordinates(
                                new Location.LocationPolar(70, 0),
                                new Velocity.VelocityRadial(2, 0)),
                        new PhysicalBody("object-2", 100, 50, Color.YELLOW)),
                dependentObject2 = new BarySimpleObject(
                        system,
                        new Coordinates(
                                new Location.LocationPolar(100, Math.PI * 2 / 3),
                                new Velocity.VelocityRadial(1.5, 0)),
                        new PhysicalBody("object-3", 100, 50, Color.ORANGE));
        system.addObject(dependentObject1);
        system.addObject(dependentObject2);
        addObject(system);
    }
}