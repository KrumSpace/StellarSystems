package baryModel.basicModels;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import kinetics.Location;
import kinetics.Velocity;
import kinetics.Acceleration;

//an object with a mass and coordinates and the related stuff
public abstract class InfluentialObject extends BasicBaryObject {
    private final @NotNull InfluenceRadiusCalculator influenceRadiusCalculator;

    //
    public InfluentialObject(@Nullable Location location,
                      @Nullable Velocity velocity,
                      @Nullable Acceleration acceleration) {
        super(location, velocity, acceleration);
        influenceRadiusCalculator = new InfluenceRadiusCalculator(this);
    }

    //
    public final double getInfluenceRadius(@NotNull InfluentialObject parent) {
        return influenceRadiusCalculator.getInfluenceRadius(parent);
    }
}