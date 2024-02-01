package testGraphics.universePainter;

import java.awt.Color;
import java.awt.Graphics;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import baryModel.UnrecognizedBaryObjectTypeException;
import baryModel.BaryObject;
import baryModel.BarySystem;
import baryModel.BaryUniverse;
import baryModel.simpleObjects.BarySimpleObject;

import testGraphics.generalPainters.ScaledOffsetPainter;

//
final class GenericObjectPainter extends ScaledOffsetPainter implements BaryObjectPainterInterface<BaryObject> {
    private static final boolean PAINT_SYSTEM_CONNECTIONS = true;
    private final @NotNull UniversePainter universePainter;

    //
    public GenericObjectPainter(int @Nullable [] drawOffset, double scale,
                                @NotNull UniversePainter universePainter) {
        super(drawOffset, scale);
        this.universePainter = universePainter;
    }

    //absolute location here is the absolute location of the parent
    @Override
    public void paint(@NotNull Graphics g,
                      @NotNull BaryObject object,
                      double @NotNull [] parentLocation) {
        double @NotNull [] absoluteLocation = CommonPainting.getAbsoluteLocationFromRelative(object, parentLocation);
        paintCommonBefore(g, object, absoluteLocation, parentLocation);
        try {
            paintByObjectType(g, object, absoluteLocation);
        } catch (UnrecognizedBaryObjectTypeException e) {
            //TODO: needs better solution, such as just not painting the object and logging such event to console
            throw new RuntimeException(e);
        }
        paintCommonAfter(g, object, absoluteLocation);
    }

    private void paintByObjectType(@NotNull Graphics g,
                                   @NotNull BaryObject object,
                                   double @NotNull [] absoluteLocation) throws UnrecognizedBaryObjectTypeException {
        if (object instanceof BarySimpleObject) {
            universePainter.getSimpleObjectPainter().paint(g, (BarySimpleObject) object, absoluteLocation);
        } else if (object instanceof BarySystem) {
            universePainter.getSystemPainter().paint(g, (BarySystem) object, absoluteLocation);
        } else {
            throw new UnrecognizedBaryObjectTypeException();
        }
    }

    private void paintCommonBefore(@NotNull Graphics g,
                                   @NotNull BaryObject object,
                                   double @NotNull [] absoluteLocation,
                                   double @NotNull [] parentAbsoluteLocation) {
        if (PAINT_SYSTEM_CONNECTIONS && !(object.getParent() instanceof BaryUniverse)) {
            @NotNull Color color = object.getColor();
            double @NotNull []
                    scaledLocation = scaleLocation(absoluteLocation),
                    scaledParentLocation = scaleLocation(parentAbsoluteLocation);
            CommonPainting.paintConnection(g, universePainter, color, scaledLocation, scaledParentLocation);
            CommonPainting.paintOrbit(g, universePainter, object, scaledParentLocation);
        }
    }

    private void paintCommonAfter(@NotNull Graphics g,
                                  @NotNull BaryObject object,
                                  double @NotNull [] absoluteLocation) {
        double @NotNull []
                scaledLocation = scaleLocation(absoluteLocation),
                drawableCenter = getDrawableFromScaled(scaledLocation);
        CommonPainting.paintInfluenceRadius(g, universePainter, object, drawableCenter);
        CommonPainting.paintCenterMarker(g, drawableCenter, object.getColor());
        CommonPainting.paintObjectInfo(g, object, drawableCenter);
    }
}