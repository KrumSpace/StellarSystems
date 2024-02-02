package baryModel;

import org.jetbrains.annotations.NotNull;

//
final class InfluenceRadiusCalculator {
    private static final double
            MAX_INFLUENCE_RADIUS = 5000,
            MASS_RATIO_POWER = 0.4; // = 2 / 5
    private final @NotNull MassiveCoordinatedObject object;

    //
    InfluenceRadiusCalculator(@NotNull MassiveCoordinatedObject object) {
        this.object = object;
    }

    //R_influence = R * (m / M) ^ (2 / 5), check notes for more info
    double getInfluenceRadius(@NotNull MassiveCoordinatedObject parent) {
        double mass = object.getMass();
        double
                distanceToParent = object.getCoordinates().getLocation().getRadial()[0],
                parentMass = parent.getMass() - mass; //only parent, without this object
        return calculateInfluenceRadius(distanceToParent, mass, parentMass);
    }

    private static double calculateInfluenceRadius(double distance, double mass, double parentMass) {
        return Math.min(MAX_INFLUENCE_RADIUS, distance * Math.pow(mass / parentMass, MASS_RATIO_POWER));
    }
}