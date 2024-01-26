package testGraphics;

import org.jetbrains.annotations.Nullable;

//
public class ScaledOffsetPainter extends OffsetPainter {
    private double defaultScale, scale;

    //
    public ScaledOffsetPainter(int @Nullable [] drawOffset, double defaultScale) {
        super(drawOffset);
        this.defaultScale = defaultScale;
        this.scale = defaultScale;
    }

    //
    public double getScale() {
        return scale;
    }

    //
    public void setScale(double scale) {
        this.scale = scale;
    }

    //
    public double getDefaultScale() {
        return defaultScale;
    }

    //
    public void setDefaultScale(double defaultScale) {
        this.defaultScale = defaultScale;
    }

    //
    public void resetScale() {
        scale = defaultScale;
    }
}