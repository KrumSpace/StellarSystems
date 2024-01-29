package testGraphics;

import org.jetbrains.annotations.Nullable;

//
public class AdvancedScaleScaledOffsetPainter extends ScaledOffsetPainter {
    private double defaultScale;

    //
    public AdvancedScaleScaledOffsetPainter(int @Nullable [] drawOffset, double defaultScale) {
        super(drawOffset, defaultScale);
        this.defaultScale = defaultScale;
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
        setScale(defaultScale);
    }
}