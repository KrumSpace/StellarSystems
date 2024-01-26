package utils;

import org.jetbrains.annotations.NotNull;

import utils.coordinates.Velocity;

//for calculating values bufferedly
public interface UpdatableValueInterface {
    //
    void update();

    //old, general-purpose interface
    interface BufferedValueInterface extends UpdatableValueInterface {
        void precalculate(double time);
    }

    //
    interface BufferedLocationInterface extends UpdatableValueInterface {
        //
        void precalculate(double time, @NotNull Velocity velocity);
    }

    //
    interface BufferedVelocityInterface extends UpdatableValueInterface {
        //TODO: add acceleration here
        //void precalculate(double time, @NotNull Acceleration acceleration);
    }
}