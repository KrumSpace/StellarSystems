package testGraphics.universePainter;

import java.util.Map;
import java.util.HashMap;
import java.awt.Color;
import java.awt.Graphics;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import baryModel.exceptions.UnrecognizedBaryObjectTypeException;
import baryModel.BaryObject;
import baryModel.BaryUniverse;
import baryModel.systems.AbstractBarySystem;
import baryModel.simpleObjects.BarySimpleObject;

import testGraphics.generalPainters.ScaledOffsetPainter;

//
final class GenericObjectPainter extends ScaledOffsetPainter implements BaryObjectPainterInterface<BaryObject> {
    private static final boolean PAINT_SYSTEM_CONNECTIONS = true;
    private static final @NotNull Map<@NotNull Class<? extends @NotNull BaryObject>, @NotNull FunctionalPainterInterface> PAINTER_MAP = new HashMap<>();
    static {
        PAINTER_MAP.put(BarySimpleObject.class, (g, obj, loc, painter) ->
                painter.getSimpleObjectPainter().paint(g, (BarySimpleObject) obj, loc));
        PAINTER_MAP.put(AbstractBarySystem.class, (g, obj, loc, painter) ->
                painter.getSystemPainter().paint(g, (AbstractBarySystem) obj, loc));
    }

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
        @NotNull Class<? extends @NotNull BaryObject> type = object.getClass();
        @Nullable FunctionalPainterInterface painter = getPainterForClass(type);
        if (painter == null) {
            throw new UnrecognizedBaryObjectTypeException();
        } else {
            painter.accept(g, object, absoluteLocation, universePainter);
        }
        // old type-check:
        // if (object instanceof BarySimpleObject) {
        //     universePainter.getSimpleObjectPainter().paint(g, (BarySimpleObject) object, absoluteLocation);
        // } else if (object instanceof AbstractBarySystem) {
        //     universePainter.getSystemPainter().paint(g, (AbstractBarySystem) object, absoluteLocation);
        // } else {
        //     throw new UnrecognizedBaryObjectTypeException();
        // }
    }

    @SuppressWarnings("unchecked")
    private @Nullable FunctionalPainterInterface getPainterForClass(@NotNull Class<? extends @NotNull BaryObject> type) {
        // Traverse the class hierarchy to find a matching painter
        while (type != null) {
            @Nullable FunctionalPainterInterface painter = PAINTER_MAP.get(type);
            if (painter != null) {
                return painter;
            }
            type = (Class<? extends BaryObject>) type.getSuperclass(); // Move up the class hierarchy
        }
        return null; // No matching painter found
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
            //CommonPainting.paintOrbit(g, universePainter, object, scaledParentLocation);
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

//
@FunctionalInterface
interface FunctionalPainterInterface {
    //
    void accept(@NotNull Graphics g, @NotNull BaryObject obj, double @NotNull [] absoluteLocation, @NotNull UniversePainter painter);
}