package kinetics;

import org.jetbrains.annotations.Nullable;

//
public abstract class MassiveKineticObject extends KineticParameters {
    //
    public MassiveKineticObject(@Nullable Location location,
                                @Nullable Velocity velocity,
                                @Nullable Acceleration acceleration) {
        super(location, velocity, acceleration);
    }

    //
    public abstract double getMass();
}