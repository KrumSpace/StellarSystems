package baryModel;

import org.jetbrains.annotations.NotNull;

//
final class InfluenceRadiusCalculator {
    private static final double MAX_INFLUENCE_RADIUS = 400;
    private final @NotNull BaryObject object;

    //
    InfluenceRadiusCalculator(@NotNull BaryObject object) {
        this.object = object;
    }

    //R_influence = R * (m / M) ^ (2 / 5)
    double getInfluenceRadius() {
        double
                distanceToParent = object.getCoordinates().getLocation().getRadial()[0],
                mass = object.getMass();
        @NotNull BaryObjectContainerInterface parent = object.getParent();
        if (parent instanceof BarySystem) {
            //parent is a system
            double parentMass = ((BarySystem) parent).getMass();
            return calculateInfluenceRadius(distanceToParent, mass, parentMass);
        } else if (parent instanceof BaryUniverse) {
            //parent is the universe
            return calculateInfluenceRadiusForRoot();
        } else {
            //unrecognized parent type
            throw new RuntimeException(new UnrecognizedBaryObjectTypeException());
        }
    }

    private static double calculateInfluenceRadius(double distance, double mass, double parentMass) {
        double
                massRatio = mass / parentMass,
                massRatioPower = 2.0 / 5;
        return Math.min(MAX_INFLUENCE_RADIUS, distance * Math.pow(massRatio, massRatioPower));
    }

    private static double calculateInfluenceRadiusForRoot() {
        return MAX_INFLUENCE_RADIUS;
    }

    private static final class UnrecognizedBaryObjectTypeException extends Exception {
        UnrecognizedBaryObjectTypeException() {
            super("Unrecognized BaryObject type!");
        }
    }
}