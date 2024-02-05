package baryGraphics.painters;

import java.awt.Color;
import java.awt.Graphics;

import org.jetbrains.annotations.NotNull;

import baryModel.exceptions.UnrecognizedBaryObjectTypeException;
import baryModel.exceptions.TopLevelObjectException;
import baryModel.BaryObject;
import baryGraphics.panels.UniverseDrawPanel;
import baryGraphics.panels.UniversePaintUtilities;

//
final class GenericObjectPainter implements ObjectPainterInterface<BaryObject> {
    private static final boolean
            PAINT_SYSTEM_CONNECTIONS = true,
            PAINT_ORBITS = false;
    private final @NotNull UniverseDrawPanel universePanel;
    private final @NotNull ObjectTypeSwitch objectTypeSwitch;

    //
    GenericObjectPainter(@NotNull UniverseDrawPanel universePanel, @NotNull PainterContainer painters) {
        this.universePanel = universePanel;
        objectTypeSwitch = new ObjectTypeSwitch(painters);
    }

    //absolute location parameter from super here is the absolute location of the parent
    @Override
    public void paint(@NotNull Graphics g,
                      @NotNull BaryObject object,
                      double @NotNull [] parentLocation) {
        double @NotNull [] absoluteLocation = getChildAbsoluteLocation(object, parentLocation);
        paintCommonBefore(g, object, absoluteLocation, parentLocation);
        try {
            objectTypeSwitch.paint(g, object, absoluteLocation);
        } catch (UnrecognizedBaryObjectTypeException e) {
            //TODO: needs better solution, such as just not painting the object and logging the event to console
            throw new RuntimeException(e);
        }
        paintCommonAfter(g, object, absoluteLocation);
    }

    private static double @NotNull [] getChildAbsoluteLocation(@NotNull BaryObject object,
                                                               double @NotNull [] parentLocation) {
        double @NotNull [] relativeLocation = object.getCoordinates().getLocation().getCartesian();
        return new double [] {
                parentLocation[0] + relativeLocation[0],
                parentLocation[1] + relativeLocation[1]};
    }

    private void paintCommonBefore(@NotNull Graphics g,
                                   @NotNull BaryObject object,
                                   double @NotNull [] absoluteLocation,
                                   double @NotNull [] parentAbsoluteLocation) {
        @NotNull Color color = object.getColor();
        if (PAINT_SYSTEM_CONNECTIONS) {
            universePanel.getPaintUtilities().paintConnection(g, color, absoluteLocation, parentAbsoluteLocation);
        }
        if (PAINT_ORBITS) {
            universePanel.getPaintUtilities().paintOrbit(g, object, parentAbsoluteLocation);
        }
    }

    private void paintCommonAfter(@NotNull Graphics g,
                                  @NotNull BaryObject object,
                                  double @NotNull [] absoluteLocation) {
        double @NotNull [] drawableCenter = universePanel.getDrawableFromAbsolute(absoluteLocation);
        @NotNull UniversePaintUtilities paintUtilities = universePanel.getPaintUtilities();
        try {
            paintUtilities.paintInfluenceRadius(g, object, drawableCenter);
        } catch (@NotNull TopLevelObjectException ignored) {}
        paintUtilities.paintCenterMarker(g, drawableCenter, object.getColor());
        paintUtilities.paintObjectInfo(g, object, drawableCenter);
    }
}