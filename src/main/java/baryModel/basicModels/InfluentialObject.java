package baryModel.basicModels;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import kinetics.Location;
import kinetics.Velocity;
import kinetics.Acceleration;
import baryModel.exceptions.TopLevelObjectException;
import baryModel.BaryObjectContainerInterface;

//an object with a mass and coordinates and the related stuff
public abstract class InfluentialObject extends BasicBaryObject {
    private final @NotNull InfluenceRadiusCalculator influenceRadiusCalculator;

    //
    public InfluentialObject(@Nullable BaryObjectContainerInterface parent,
                             @Nullable Location location,
                             @Nullable Velocity velocity,
                             @Nullable Acceleration acceleration) {
        super(parent, location, velocity, acceleration);
        influenceRadiusCalculator = new InfluenceRadiusCalculator(this);
    }

    //
    public double getInfluenceRadius() throws TopLevelObjectException {
        return influenceRadiusCalculator.getInfluenceRadius((InfluentialObject) getParent());
    }
}