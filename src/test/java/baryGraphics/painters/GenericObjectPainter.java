package baryGraphics.painters;

import java.awt.Color;
import java.awt.Graphics;

import org.jetbrains.annotations.NotNull;

import kinetics.Location;
import baryModel.exceptions.UnrecognizedBaryObjectTypeException;
import baryModel.exceptions.TopLevelObjectException;
import baryModel.basicModels.BasicBaryObject;
import baryModel.basicModels.InfluentialObject;

import baryGraphics.panels.UniverseDrawPanel;
import baryGraphics.panels.UniversePaintUtilities;

//
final class GenericObjectPainter implements ObjectPainterInterface<BasicBaryObject> {
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
                      @NotNull BasicBaryObject object,
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

    private static double @NotNull [] getChildAbsoluteLocation(@NotNull BasicBaryObject object,
                                                               double @NotNull [] parentLocation) {
        @NotNull Location relativeLocation = object.getLocation();
        return new double [] {
                parentLocation[0] + relativeLocation.getX(),
                parentLocation[1] + relativeLocation.getY()};
    }

    private void paintCommonBefore(@NotNull Graphics g,
                                   @NotNull BasicBaryObject object,
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
                                  @NotNull BasicBaryObject object,
                                  double @NotNull [] absoluteLocation) {
        double @NotNull [] drawableCenter = universePanel.getDrawableFromAbsolute(absoluteLocation);
        @NotNull UniversePaintUtilities paintUtilities = universePanel.getPaintUtilities();
        if (object instanceof @NotNull InfluentialObject influentialObject) {
            try {
                paintUtilities.paintInfluenceRadius(g, influentialObject, drawableCenter);
            } catch (@NotNull TopLevelObjectException ignored) {}
        }
        //paintUtilities.paintCenterMarker(g, drawableCenter, object.getColor());
        paintUtilities.paintVelocity(g, object, drawableCenter);
        paintUtilities.paintAcceleration(g, object, drawableCenter);
        paintUtilities.paintObjectInfo(g, object, drawableCenter, object.getColor());
    }
}