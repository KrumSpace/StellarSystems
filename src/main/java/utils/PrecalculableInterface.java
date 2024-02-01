package utils;

import org.jetbrains.annotations.NotNull;

import utils.coordinates.Velocity;

//for precalculating values and then updating them
public interface PrecalculableInterface {
    //
    void update();

    //old, general-purpose interface
    interface BufferedValueInterface extends PrecalculableInterface {
        void precalculate(double time);
    }

    //
    interface BufferedLocationInterface extends PrecalculableInterface {
        //
        void precalculate(double time, @NotNull Velocity velocity);
    }

    //
    interface BufferedVelocityInterface extends PrecalculableInterface {
        //TODO: add acceleration here
        //void precalculate(double time, @NotNull Acceleration acceleration);
    }
}