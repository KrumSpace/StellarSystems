package testGraphics.universePainter;

import java.awt.Graphics;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import baryModel.systems.AbstractBarySystem;

//
final class BarySystemPainter extends ObjectContainerPainter<AbstractBarySystem> {
    private final @NotNull UniversePainter universePainter;

    //
    public BarySystemPainter(int @Nullable [] drawOffset, double scale,
                             @NotNull UniversePainter universePainter) {
        super(drawOffset, scale);
        this.universePainter = universePainter;
    }

    //
    @Override
    public void paint(@NotNull Graphics g,
                      @NotNull AbstractBarySystem system,
                      double @NotNull [] absoluteLocation) {
        double @NotNull [] scaledLocation = scaleLocation(absoluteLocation);
        paintMembers(g, universePainter.getGenericObjectPainter(), system, absoluteLocation);
        /*
        TODO: paint some general system-wide data here, such as:
            * member count
            * etc
        */
        CommonPainting.paintCenterMarker(g, getDrawableFromScaled(scaledLocation), system.getColor());
    }
}