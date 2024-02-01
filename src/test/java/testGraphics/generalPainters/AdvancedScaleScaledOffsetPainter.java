package testGraphics.generalPainters;

import org.jetbrains.annotations.Nullable;

//currently unused functionality, intended for zoom reset-ability
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