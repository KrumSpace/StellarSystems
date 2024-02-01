package testGraphics.universePainter;

import java.util.Objects;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Graphics;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import baryModel.*;
import baryModel.simpleObjects.BarySimpleObject;

import testGraphics.generalPainters.ScaledOffsetPainter;
import testGraphics.generalPainters.AdvancedScaleScaledOffsetPainter;

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
        CommonPainting.paintCenterMarker(g, this, universeLocation, UNIVERSE_CENTER_MARKER_COLOR);
        paintMembers(g, universe, universeLocation);
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
        double @NotNull [] absoluteLocation = CommonPainting.getAbsoluteLocationFromRelative(object, parentLocation);
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

    private void paintCommonBefore(@NotNull Graphics g,
                                   @NotNull BaryObject object,
                                   double @NotNull [] absoluteLocation,
                                   double @NotNull [] parentAbsoluteLocation) {
        if (PAINT_SYSTEM_CONNECTIONS && !(object.getParent() instanceof BaryUniverse)) {
            @NotNull Color color = object.getColor();
            double @NotNull []
                    scaledLocation = scaleLocation(absoluteLocation),
                    scaledParentLocation = scaleLocation(parentAbsoluteLocation);
            CommonPainting.paintConnection(g, this, color, scaledLocation, scaledParentLocation);
            CommonPainting.paintOrbit(g, this, object, scaledParentLocation);
        }
    }

    private void paintCommonAfter(@NotNull Graphics g,
                                  @NotNull BaryObject object,
                                  double @NotNull [] absoluteLocation) {
        double @NotNull []
                scaledLocation = scaleLocation(absoluteLocation),
                drawableCenter = getDrawableFromScaled(scaledLocation);
        CommonPainting.paintInfluenceRadius(g, this, object, drawableCenter);
        CommonPainting.paintCenterMarker(g, this, scaledLocation, object.getColor());
        CommonPainting.paintObjectInfo(g, object, drawableCenter);
    }

    private void paintSimpleBaryObject(@NotNull Graphics g,
                                       @NotNull BarySimpleObject simpleObject,
                                       double @NotNull [] absoluteLocation) {
        double @NotNull [] drawCenter = getDrawableFromScaled(scaleLocation(absoluteLocation));
        double scaledSize = scaleValue(simpleObject.getSimpleBody().getRadius());
        g.setColor(simpleObject.getColor());
        g.fillOval(
                (int) (drawCenter[0] - scaledSize / 2),
                (int) (drawCenter[1] - scaledSize / 2),
                (int) scaledSize, (int) scaledSize);
    }

    private void paintBarySystem(@NotNull Graphics g,
                                 @NotNull BarySystem system,
                                 double @NotNull [] absoluteLocation) {
        double @NotNull [] scaledLocation = scaleLocation(absoluteLocation);
        CommonPainting.paintCenterMarker(g, this, scaledLocation, system.getColor());
        //TODO: paint some general system-wide data here
        paintMembers(g, system, absoluteLocation);
    }
}

//common method extraction ongoing
class CommonPainting {
    //
    static double @NotNull [] getAbsoluteLocationFromRelative(@NotNull BaryObject object,
                                                              double @NotNull [] parentLocation) {
        double @NotNull [] relativeLocation = object.getCoordinates().getLocation().getCartesian();
        return new double [] {
                parentLocation[0] + relativeLocation[0],
                parentLocation[1] + relativeLocation[1]};
    }

    //
    static void paintCenterMarker(@NotNull Graphics g, @NotNull ScaledOffsetPainter painter,
                                  double @NotNull [] scaledLocation,
                                  @NotNull Color color) {
        int @NotNull [] drawOffset = painter.getDrawOffset();
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

    //
    static void paintConnection(@NotNull Graphics g, @NotNull ScaledOffsetPainter painter,
                                @NotNull Color color,
                                double @NotNull [] scaledLocation,
                                double @NotNull [] scaledParentLocation) {
        double @NotNull []
                drawLocation = painter.getDrawableFromScaled(scaledLocation),
                parentDrawLocation = painter.getDrawableFromScaled(scaledParentLocation);
        g.setColor(color);
        g.drawLine(
                (int) drawLocation[0], (int) drawLocation[1],
                (int) parentDrawLocation[0], (int) parentDrawLocation[1]);
    }

    //
    static void paintOrbit(@NotNull Graphics g, @NotNull ScaledOffsetPainter painter,
                           @NotNull BaryObject object,
                           double @NotNull [] scaledParentLocation) {
        double @NotNull [] drawCenter = painter.getDrawableFromScaled(scaledParentLocation);
        double
                distance = object.getCoordinates().getLocation().getRadial()[0],
                scaledDistance = painter.scaleValue(distance);
        @NotNull Color orbitColor = object.getColor();
        g.setColor(orbitColor);
        g.drawOval(
                (int) (drawCenter[0] - scaledDistance),
                (int) (drawCenter[1] - scaledDistance),
                (int) scaledDistance * 2, (int) scaledDistance * 2);
    }

    //
    static void paintInfluenceRadius(@NotNull Graphics g, @NotNull ScaledOffsetPainter painter,
                                     @NotNull BaryObject object,
                                     double @NotNull [] drawableCenter) {
        double scaledInfluenceRadius = painter.scaleValue(object.getInfluenceRadius());
        g.setColor(object.getColor());
        g.drawOval(
                (int) (drawableCenter[0] - scaledInfluenceRadius),
                (int) (drawableCenter[1] - scaledInfluenceRadius),
                (int) scaledInfluenceRadius * 2, (int) scaledInfluenceRadius * 2);
    }

    //
    static void paintObjectInfo(@NotNull Graphics g, @NotNull BaryObject object, double @NotNull [] drawCenter) {
        int @NotNull [] textOffset = new int [] {-20, 30};
        int lineHeight = 15;
        paintObjectInfoLines(g, drawCenter, textOffset, lineHeight, new ArrayList<>() {{
            add(object.getName());
            add("M: " + ((int) (object.getMass() * 10)) / 10.0);
            add("SOI: " + ((int) (object.getInfluenceRadius() * 10)) / 10.0);
        }});
    }

    private static void paintObjectInfoLines(@NotNull Graphics g,
                                             double @NotNull [] drawCenter,
                                             int @NotNull [] textOffset, int lineHeight,
                                             @NotNull List<@Nullable String> lines) {
        for (int i = 0; i < lines.size(); i++) {
            g.drawString(
                    Objects.requireNonNullElse(lines.get(i), ""),
                    (int) (drawCenter[0] + textOffset[0]),
                    (int) (drawCenter[1] + textOffset[1] + lineHeight * i));
        }
    }
}