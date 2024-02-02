package baryModel;

import org.jetbrains.annotations.NotNull;

import baryModel.exceptions.TopLevelObjectException;
import baryModel.systems.AbstractBarySystem;

//
final class InfluenceRadiusCalculator {
    private static final double
            MAX_INFLUENCE_RADIUS = 5000,
            MASS_RATIO_POWER = 0.4, // = 2 / 5
            INFLUENCE_RADIUS_MASS_COEFFICIENT_FOR_ROOT = 3;
    private final @NotNull BaryObject object;

    //
    InfluenceRadiusCalculator(@NotNull BaryObject object) {
        this.object = object;
    }

    //R_influence = R * (m / M) ^ (2 / 5)
    double getInfluenceRadius() throws TopLevelObjectException {
        double mass = object.getMass();
        @NotNull BaryObjectContainerInterface parent = object.getParent();
        double
                distanceToParent = object.getCoordinates().getLocation().getRadial()[0],
                parentMass = ((AbstractBarySystem) parent).getMass();
        return calculateInfluenceRadius(distanceToParent, mass, parentMass);
    }

    private static double calculateInfluenceRadius(double distance, double mass, double parentMass) {
        return Math.min(MAX_INFLUENCE_RADIUS, distance * Math.pow(mass / parentMass, MASS_RATIO_POWER));
    }

    private static double calculateInfluenceRadiusForRoot(double mass) {
        return Math.min(MAX_INFLUENCE_RADIUS, mass * INFLUENCE_RADIUS_MASS_COEFFICIENT_FOR_ROOT);
    }
}