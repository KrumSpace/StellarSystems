package demo;

import java.awt.Color;

import org.jetbrains.annotations.NotNull;

import utils.coordinates.Location;
import utils.coordinates.Velocity;
import utils.coordinates.Coordinates;

import baryModel.BaryObject;
import baryModel.SimpleBody;
import baryModel.BarySimpleObject;
import baryModel.BarySystem;
import baryModel.BaryUniverse;
import baryModel.BaryModel;

//
class TestModel extends BaryModel {
    //
    TestModel() {
        super();
        @NotNull BaryUniverse universe = getUniverse();
        @NotNull BaryObject independentObject = new BarySimpleObject(
                universe,
                new Coordinates(
                        new Location.LocationPolar(500, Math.PI),
                        new Velocity.VelocityRadial(0.3, 0)),
                new SimpleBody("object-1", 100, 50, Color.CYAN));
        universe.addObject(independentObject);

        @NotNull BarySystem system = new BarySystem(
                universe,
                new Coordinates(
                        new Location.LocationPolar(220, 0),
                        new Velocity.VelocityRadial(0.6, 0)),
                Color.MAGENTA);
        @NotNull BaryObject
                dependentObject1 = new BarySimpleObject(
                        universe,
                        new Coordinates(
                                new Location.LocationPolar(70, 0),
                                new Velocity.VelocityRadial(2, 0)),
                        new SimpleBody("object-2", 100, 50, Color.YELLOW)),
                dependentObject2 = new BarySimpleObject(
                        universe,
                        new Coordinates(
                                new Location.LocationPolar(100, Math.PI * 2 / 3),
                                new Velocity.VelocityRadial(1.5, 0)),
                        new SimpleBody("object-3", 100, 50, Color.ORANGE));
        system.addObject(dependentObject1);
        system.addObject(dependentObject2);
        universe.addObject(system);
    }
}