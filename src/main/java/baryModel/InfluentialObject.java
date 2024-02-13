package baryModel;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import kinetics.Location;
import kinetics.Velocity;
import kinetics.Acceleration;
import kinetics.MassiveKineticObject;

//an object with a mass and coordinates and the related stuff
public abstract class InfluentialObject extends MassiveKineticObject {
    private final @NotNull InfluenceRadiusCalculator influenceRadiusCalculator;

    //
    InfluentialObject(@Nullable Location location,
                      @Nullable Velocity velocity,
                      @Nullable Acceleration acceleration) {
        super(location, velocity, acceleration);
        influenceRadiusCalculator = new InfluenceRadiusCalculator(this);
    }

    //
    final double getInfluenceRadius(@NotNull InfluentialObject parent) {
        return influenceRadiusCalculator.getInfluenceRadius(parent);
    }
}