package baryModel;

import org.jetbrains.annotations.NotNull;

//
final class InfluenceRadiusCalculator {
    private static final double
            MAX_INFLUENCE_RADIUS = 2000,
            MASS_RATIO_POWER = 0.4, // = 2 / 5
            INFLUENCE_RADIUS_MASS_COEFFICIENT_FOR_ROOT = 5;
    private final @NotNull BaryObject object;

    //
    InfluenceRadiusCalculator(@NotNull BaryObject object) {
        this.object = object;
    }

    //R_influence = R * (m / M) ^ (2 / 5)
    double getInfluenceRadius() {
        double mass = object.getMass();
        @NotNull BaryObjectContainerInterface parent = object.getParent();
        try {
            if (parent instanceof BarySystem) {
                double
                        distanceToParent = object.getCoordinates().getLocation().getRadial()[0],
                        parentMass = ((BarySystem) parent).getMass();
                return calculateInfluenceRadius(distanceToParent, mass, parentMass);
            } else {
                if (!(parent instanceof BaryUniverse)) {
                    //parent is not the universe nor a system - unrecognized parent type
                    throw new UnrecognizedBaryObjectTypeException();
                }
                return calculateInfluenceRadiusForRoot(mass);
            }
        } catch (UnrecognizedBaryObjectTypeException e) {
            throw new RuntimeException(e);
        }
    }

    private static double calculateInfluenceRadius(double distance, double mass, double parentMass) {
        return Math.min(MAX_INFLUENCE_RADIUS, distance * Math.pow(mass / parentMass, MASS_RATIO_POWER));
    }

    private static double calculateInfluenceRadiusForRoot(double mass) {
        return Math.min(MAX_INFLUENCE_RADIUS, mass * INFLUENCE_RADIUS_MASS_COEFFICIENT_FOR_ROOT);
    }
}