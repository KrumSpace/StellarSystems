package testGraphics;

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
    public double getScale() {
        return scale;
    }

    //
    public void setScale(double scale) {
        this.scale = scale;
    }
}