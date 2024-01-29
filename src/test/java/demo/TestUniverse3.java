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
class TestUniverse3 extends BaryUniverse {
    //
    TestUniverse3() {
        super();
        addCommonObjects(2, 3500, new double [2]);
        addNewSimpleObject(
                5000, -1700,
                300, Math.PI,
                new PhysicalBody("object-1", 300, 100, Color.CYAN));
        addNewSimpleObject(
                5000, 1800,
                100, Math.PI,
                new PhysicalBody("object-2", 200, 100, Color.MAGENTA));
    }

    private void addNewSimpleObject(double x, double y, double speed, double direction, @NotNull PhysicalBody body) {
        addObject(new BarySimpleObject(
                this,
                new Coordinates(
                        new Location.LocationCartesian(x, y),
                        new Velocity.VelocityCartesian(speed, direction)),
                body));
    }

    private void addCommonObjects(int membersPerSide, double separation, double @NotNull [] clusterLocation) {
        double sideLength = (membersPerSide - 1) * separation;
        double @NotNull [] startingLocation = new double [] {
                clusterLocation[0] - sideLength / 2,
                clusterLocation[1] - sideLength / 2};
        for (int i = 0; i < membersPerSide; i++) {
            double x = startingLocation[0] + i * separation;
            for (int j = 0; j < membersPerSide; j++) {
                double y = startingLocation[1] + j * separation;
                @NotNull String nameSuffix = "-[" + i +"," + j + "]";
                addNewCommonObject(x, y, nameSuffix);
            }
        }
    }

    private void addNewCommonObject(double x, double y, @NotNull String nameSuffix) {
        @NotNull String name = "object" + nameSuffix;
        addNewSimpleObject(
                x, y,
                0, 0,
                new PhysicalBody(name, 100, 100, Color.green));
    }
}