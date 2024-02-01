package testGraphics.universePainter;

import java.awt.Graphics;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import baryModel.simpleObjects.BarySimpleObject;
import baryModel.simpleObjects.PhysicalBody;

import testGraphics.generalPainters.ScaledOffsetPainter;

//
final class SimpleObjectPainter extends ScaledOffsetPainter implements BaryObjectPainterInterface<BarySimpleObject> {
    //
    public SimpleObjectPainter(int @Nullable [] drawOffset, double scale) {
        super(drawOffset, scale);
    }

    //
    @Override
    public void paint(@NotNull Graphics g,
                      @NotNull BarySimpleObject simpleObject,
                      double @NotNull [] absoluteLocation) {
        double @NotNull [] drawCenter = getDrawableFromScaled(scaleLocation(absoluteLocation));
        paintPhysicalBody(g, simpleObject.getSimpleBody(), drawCenter);
    }

    private void paintPhysicalBody(@NotNull Graphics g,
                                   @NotNull PhysicalBody body,
                                   double @NotNull [] drawCenter) {
        CommonPainting.drawCircleAtCenter(g, drawCenter, scaleValue(body.getRadius()), body.getColor(), true);
        //TODO: improve this eventually for more detail
    }
}