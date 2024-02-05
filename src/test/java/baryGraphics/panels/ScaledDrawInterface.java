package baryGraphics.panels;

import org.jetbrains.annotations.NotNull;

//this class doesn't and shouldn't have a setter for the scale
public interface ScaledDrawInterface {
    //
    double getScale();

    /**
     * Scales a single value.
     *
     * @param value Value to scale.
     * @return Scaled value.
     */
    default double scaleValue(double value) {
        return value / getScale();
    }

    /**
     * Scales a 2D location.
     *
     * @param actual Actual location as a 2D double array.
     * @return Scaled location as a 2D double array.
     */
    default double @NotNull [] scaleLocation(double @NotNull [] actual) {
        return new double [] {scaleValue(actual[0]), scaleValue(actual[1])};
    }
}