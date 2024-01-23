package baryModel;

import java.util.List;
import java.awt.Color;

import utils.MathUtils;
import utils.coordinates.Location;
import utils.coordinates.Velocity;
import utils.coordinates.Coordinates;

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

    @Override
    public void update() {
        super.update();
        checkMeaninglessSystems();

        List<BaryObject> objects = getObjects();
        //make new systems
        for (int i = 0; objects.size() > 2 && i < objects.size(); i++) {
            BaryObject object1 = objects.get(i);
            double [] cartesian1 = object1.getCoordinates().getLocation().getCartesian();
            double influenceRadius1 = object1.getInfluenceRadius();
            //check all neighbors
            for (int j = 0; j < objects.size(); j++) {
                if (j != i) {
                    BaryObject object2 = objects.get(j);
                    double []
                            cartesian2 = object2.getCoordinates().getLocation().getCartesian(),
                            cartesianDelta = new double[] {
                                    cartesian1[0] - cartesian2[0],
                                    cartesian1[1] - cartesian2[1]};
                    double distance = Math.hypot(cartesianDelta[0], cartesianDelta[1]);
                    double influenceRadius2 = object2.getInfluenceRadius();
                    //check influence radiuses
                    if (distance < Math.max(influenceRadius1, influenceRadius2)) {
                        double
                                mass1 = object1.getMass(),
                                mass2 = object2.getMass(),
                                massRatio = mass1 / mass2;
                        double []
                                weightedCartesianDelta = new double [] {
                                        cartesianDelta[0] / (1 + massRatio),
                                        cartesianDelta[1] / (1 + massRatio)},
                                initialVelocity1 = object1.getCoordinates().getVelocity().getCartesian(),
                                vxy1 = new double [] {
                                        initialVelocity1[0] * Math.cos(initialVelocity1[1]),
                                        initialVelocity1[0] * Math.sin(initialVelocity1[1])},
                                initialVelocity2 = object2.getCoordinates().getVelocity().getCartesian(),
                                vxy2 = new double [] {
                                        initialVelocity2[0] * Math.cos(initialVelocity2[1]),
                                        initialVelocity2[0] * Math.sin(initialVelocity2[1])},
                                systemVelocity = new double [] {
                                        (vxy1[0] * mass1 + vxy2[0] * mass2) / (mass1 + mass2),
                                        (vxy1[1] * mass1 + vxy2[1] * mass2) / (mass1 + mass2)};
                        Coordinates systemCoordinates = new Coordinates(
                                new Location.LocationCartesian(
                                        cartesian1[0] - weightedCartesianDelta[0],
                                        cartesian1[1] - weightedCartesianDelta[1]),
                                new Velocity.VelocityCartesian(
                                        Math.hypot(systemVelocity[0], systemVelocity[1]),
                                        MathUtils.getAngle(systemVelocity[0], systemVelocity[1])));
                        Color systemColor = Color.red;
                        BarySystem system = new BarySystem(this, systemCoordinates, systemColor);
                        this.addObject(system);
                        double [] newVelocityProjections1 = new double [] {
                                vxy1[0] - systemVelocity[0],
                                vxy1[1] - systemVelocity[1]};
                        object1.setCoordinates(new Coordinates(
                                new Location.LocationCartesian(
                                        cartesian1[0] - systemCoordinates.getLocation().getCartesian()[0],
                                        cartesian1[1] - systemCoordinates.getLocation().getCartesian()[1]),
                                new Velocity.VelocityCartesian(
                                        Math.hypot(newVelocityProjections1[0], newVelocityProjections1[1]),
                                        MathUtils.getAngle(newVelocityProjections1[0], newVelocityProjections1[1]))));
                        system.addObject(object1);
                        object1.setParent(system);
                        removeObject(object1);
                        i--;
                        double [] newVelocityProjections2 = new double [] {
                                vxy2[0] - systemVelocity[0],
                                vxy2[1] - systemVelocity[1]};
                        object2.setCoordinates(new Coordinates(
                                new Location.LocationCartesian(
                                        cartesian2[0] - systemCoordinates.getLocation().getCartesian()[0],
                                        cartesian2[1] - systemCoordinates.getLocation().getCartesian()[1]),
                                new Velocity.VelocityCartesian(
                                        Math.hypot(newVelocityProjections2[0], newVelocityProjections2[1]),
                                        MathUtils.getAngle(newVelocityProjections2[0], newVelocityProjections2[1]))));
                        system.addObject(object2);
                        object2.setParent(system);
                        removeObject(object2);
                        break;
                    }
                }
            }
        }


        //check collisions?
            //check all neighbors, if some radiuses overlap (only applies to simple objects)
    }
}