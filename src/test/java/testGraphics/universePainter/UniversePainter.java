package testGraphics.universePainter;

import java.util.Collections;
import java.awt.Color;
import java.awt.Graphics;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import baryModel.*;
import baryModel.simpleObjects.BarySimpleObject;
import baryModel.simpleObjects.PhysicalBody;

import testGraphics.AdvancedScaleScaledOffsetPainter;

//
public class UniversePainter extends AdvancedScaleScaledOffsetPainter {
    private static final double DEFAULT_SCALE = 20;
    private static final @NotNull Color UNIVERSE_CENTER_MARKER_COLOR = Color.white;
    private static final boolean PAINT_SYSTEM_CONNECTIONS = true;
    private final @NotNull BaryUniverse universe;

    //
    public UniversePainter(@NotNull BaryUniverse universe, int @Nullable [] drawOffset, double defaultScale) {
        super(drawOffset, defaultScale);
        this.universe = universe;
    }

    //
    public UniversePainter(@NotNull BaryUniverse universe) {
        this(universe, null, DEFAULT_SCALE);
    }

    //
    public void paint(@NotNull Graphics g) {
        double @NotNull [] universeLocation = new double [2];
        paintCenterMarker(g, universeLocation, UNIVERSE_CENTER_MARKER_COLOR);
        paintMembers(g, universe, universeLocation);
    }

    private void paintCenterMarker(@NotNull Graphics g,
                                   double @NotNull [] scaledLocation,
                                   @NotNull Color color) {
        int @NotNull [] drawOffset = getDrawOffset();
        int centerMarkerSize = 20;
        g.setColor(color);
        g.drawLine( //horizontal line
                (int) (scaledLocation[0] + drawOffset[0] - centerMarkerSize / 2),
                (int) (scaledLocation[1] + drawOffset[1]),
                (int) (scaledLocation[0] + drawOffset[0] + centerMarkerSize / 2),
                (int) (scaledLocation[1] + drawOffset[1]));
        g.drawLine( //vertical line
                (int) (scaledLocation[0] + drawOffset[0]),
                (int) (scaledLocation[1] + drawOffset[1] - centerMarkerSize / 2),
                (int) (scaledLocation[0] + drawOffset[0]),
                (int) (scaledLocation[1] + drawOffset[1] + centerMarkerSize / 2));
    }

    private void paintMembers(@NotNull Graphics g,
                              @NotNull BaryObjectContainerInterface container,
                              double @NotNull [] absoluteLocation) {
        for (@NotNull BaryObject object : Collections.unmodifiableList(container.getObjects())) {
            try {
                paintBaryObject(g, object, absoluteLocation);
            } catch (UnrecognizedBaryObjectTypeException e) {
                throw new RuntimeException(e); //TODO: needs better solution, such as not painting the object and logging to console
            }
        }
    }

    private void paintBaryObject(@NotNull Graphics g,
                                 @NotNull BaryObject object,
                                 double @NotNull [] parentLocation) throws UnrecognizedBaryObjectTypeException {
        double @NotNull [] absoluteLocation = getAbsoluteLocation(object, parentLocation);
        paintCommonBefore(g, object, absoluteLocation, parentLocation);
        if (object instanceof BarySimpleObject) {
            paintSimpleBaryObject(g, (BarySimpleObject) object, absoluteLocation);
        } else if (object instanceof BarySystem) {
            paintBarySystem(g, (BarySystem) object, absoluteLocation);
        } else {
            throw new UnrecognizedBaryObjectTypeException();
        }
        paintCommonAfter(g, object, absoluteLocation);
    }

    private double @NotNull [] getAbsoluteLocation(@NotNull BaryObject object,
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
        if (PAINT_SYSTEM_CONNECTIONS && !(object.getParent() instanceof BaryUniverse)) {
            @NotNull Color color = object.getColor();
            double @NotNull []
                    scaledLocation = scaleLocation(absoluteLocation),
                    scaledParentLocation = scaleLocation(parentAbsoluteLocation);
            paintConnection(g, color, scaledLocation, scaledParentLocation);
            paintOrbit(g, object, scaledParentLocation);
        }
    }

    private void paintConnection(@NotNull Graphics g,
                                 @NotNull Color color,
                                 double @NotNull [] scaledLocation,
                                 double @NotNull [] scaledParentLocation) {
        double @NotNull []
                drawLocation = getDrawableFromScaled(scaledLocation),
                parentDrawLocation = getDrawableFromScaled(scaledParentLocation);
        g.setColor(color);
        g.drawLine(
                (int) drawLocation[0], (int) drawLocation[1],
                (int) parentDrawLocation[0], (int) parentDrawLocation[1]);
    }

    private void paintOrbit(@NotNull Graphics g,
                            @NotNull BaryObject object,
                            double @NotNull [] scaledParentLocation) {
        double @NotNull [] drawCenter = getDrawableFromScaled(scaledParentLocation);
        double
                distance = object.getCoordinates().getLocation().getRadial()[0],
                scaledDistance = scaleValue(distance);
        @NotNull Color orbitColor = object.getColor();
        g.setColor(orbitColor);
        g.drawOval(
                (int) (drawCenter[0] - scaledDistance),
                (int) (drawCenter[1] - scaledDistance),
                (int) scaledDistance * 2, (int) scaledDistance * 2);
    }

    private void paintCommonAfter(@NotNull Graphics g,
                                  @NotNull BaryObject object,
                                  double @NotNull [] absoluteLocation) {
        paintInfluenceRadius(g, object, absoluteLocation);
        //paint name, size/mass, center
    }

    private void paintInfluenceRadius(@NotNull Graphics g,
                                      @NotNull BaryObject object,
                                      double @NotNull [] absoluteLocation) {
        double @NotNull[] drawableCenter = getDrawableFromScaled(scaleLocation(absoluteLocation));
        double scaledInfluenceRadius = scaleValue(object.getInfluenceRadius());
        g.setColor(object.getColor());
        g.drawOval(
                (int) (drawableCenter[0] - scaledInfluenceRadius),
                (int) (drawableCenter[1] - scaledInfluenceRadius),
                (int) scaledInfluenceRadius * 2, (int) scaledInfluenceRadius * 2);
    }

    private void paintSimpleBaryObject(@NotNull Graphics g,
                                       @NotNull BarySimpleObject simpleObject,
                                       double @NotNull [] absoluteLocation) {
        double @NotNull []
                scaledLocation = scaleLocation(absoluteLocation),
                drawCenter = getDrawableFromScaled(scaledLocation);
        @NotNull PhysicalBody simpleBody = simpleObject.getSimpleBody();
        double
                actualSize = simpleBody.getRadius(),
                scaledSize = scaleValue(actualSize);
        g.setColor(simpleObject.getColor());
        g.fillOval(
                (int) (drawCenter[0] - scaledSize / 2),
                (int) (drawCenter[1] - scaledSize / 2),
                (int) scaledSize, (int) scaledSize);
        @NotNull String name = simpleBody.getName();
        int @NotNull [] textOffset = new int [] {-20, 20};
        g.drawString(
                name,
                (int) (drawCenter[0] + textOffset[0]),
                (int) (drawCenter[1] + scaledSize / 2 + textOffset[1]));
    }

    private void paintBarySystem(@NotNull Graphics g,
                                 @NotNull BarySystem system,
                                 double @NotNull [] absoluteLocation) {
        double @NotNull [] scaledLocation = scaleLocation(absoluteLocation);
        paintCenterMarker(g, scaledLocation, system.getColor());
        //TODO: paint some general system-wide data here
        paintMembers(g, system, absoluteLocation);
    }

    private double scaleValue(double value) {
        return value / getScale();
    }

    private double @NotNull [] scaleLocation(double @NotNull [] actual) {
        return new double [] {scaleValue(actual[0]), scaleValue(actual[1])};
    }

    private double @NotNull [] getDrawableFromScaled(double @NotNull [] scaledLocation) {
        int @NotNull [] drawOffset = getDrawOffset();
        return new double [] {
                scaledLocation[0] + drawOffset[0],
                scaledLocation[1] + drawOffset[1]};
    }
}