package baryModel.basicModels;

import org.jetbrains.annotations.NotNull;

//
final class InfluenceRadiusCalculator {
    private static final double
            MAX_INFLUENCE_RADIUS = 6000, //should be as big as possible; kept minimal for easily visible testing; TODO: remove or increase this
            MASS_RATIO_POWER = 0.4; //two fifths for mass ratio power
    private final @NotNull InfluentialObject object;

    //
    InfluenceRadiusCalculator(@NotNull InfluentialObject object) {
        this.object = object;
    }

    //R_influence = R * (m / M) ^ (2 / 5), check notes for more info
    double getInfluenceRadius(@NotNull InfluentialObject parent) {
        double mass = object.getMass();
        double
                distanceToParent = object.getLocation().getRadius(),
                parentMass = parent.getMass() - mass; //only parent, without this object
        return calculateInfluenceRadius(distanceToParent, mass, parentMass);
    }

    private static double calculateInfluenceRadius(double distance, double mass, double parentMass) {
        return Math.min(MAX_INFLUENCE_RADIUS, distance * Math.pow(mass / parentMass, MASS_RATIO_POWER));
    }
}