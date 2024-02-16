package baryModel.basicModels;

import org.jetbrains.annotations.Nullable;

import kinetics.Location;
import kinetics.Velocity;
import kinetics.Acceleration;

//
public abstract class NonInfluentialObject extends BasicBaryObject {
    //
    public NonInfluentialObject(@Nullable Location location,
                         @Nullable Velocity velocity,
                         @Nullable Acceleration acceleration) {
        super(location, velocity, acceleration);
    }
}