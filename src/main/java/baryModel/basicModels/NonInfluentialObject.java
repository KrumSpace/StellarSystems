package baryModel.basicModels;

import org.jetbrains.annotations.Nullable;

import kinetics.Location;
import kinetics.Velocity;
import kinetics.Acceleration;
import baryModel.BaryObjectContainerInterface;

//
public abstract class NonInfluentialObject extends BasicBaryObject {
    //
    public NonInfluentialObject(@Nullable BaryObjectContainerInterface parent,
                                @Nullable Location location,
                                @Nullable Velocity velocity,
                                @Nullable Acceleration acceleration) {
        super(parent, location, velocity, acceleration);
    }
}