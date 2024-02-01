package testGraphics.generalPainters;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

//
public class ScaledOffsetPainter extends OffsetPainter {
    private double scale;

    //
    public ScaledOffsetPainter(int @Nullable [] drawOffset, double scale) {
        super(drawOffset);
        this.scale = scale;
    }

    //
    public final double getScale() {
        return scale;
    }

    //
    public void setScale(double scale) {
        this.scale = scale;
    }

    /**
     * Scales a single value.
     *
     * @param value Value to scale.
     * @return Scaled value.
     */
    public final double scaleValue(double value) {
        return value / getScale();
    }

    /**
     * Scales a 2D location.
     *
     * @param actual Actual location as a 2D double array.
     * @return Scaled location as a 2D double array.
     */
    public final double @NotNull [] scaleLocation(double @NotNull [] actual) {
        return new double [] {scaleValue(actual[0]), scaleValue(actual[1])};
    }

    /**
     * Offsets a scaled 2D location for drawing.
     *
     * @param scaledLocation Scaled location as a 2D double array.
     * @return Drawable location as a 2D double array.
     */
    public final double @NotNull [] getDrawableFromScaled(double @NotNull [] scaledLocation) {
        int @NotNull [] drawOffset = getDrawOffset();
        return new double [] {
                scaledLocation[0] + drawOffset[0],
                scaledLocation[1] + drawOffset[1]};
    }
}