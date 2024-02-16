package baryModel.basicModels;

import org.jetbrains.annotations.Nullable;

import kinetics.Location;
import kinetics.Velocity;
import kinetics.Acceleration;
import kinetics.MassiveKineticObject;

//
public abstract class BasicBaryObject extends MassiveKineticObject {
    //
    BasicBaryObject(@Nullable Location location,
                           @Nullable Velocity velocity,
                           @Nullable Acceleration acceleration) {
        super(location, velocity, acceleration);
    }
}