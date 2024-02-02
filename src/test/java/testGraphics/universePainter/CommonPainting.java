package testGraphics.universePainter;

import java.util.Objects;
import java.util.List;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Graphics;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import baryModel.exceptions.TopLevelObjectException;
import baryModel.BaryObject;

import testGraphics.generalPainters.ScaledOffsetPainter;

//common methods for painting
class CommonPainting {
    //
    static double @NotNull [] getAbsoluteLocationFromRelative(@NotNull BaryObject object,
                                                              double @NotNull [] parentLocation) {
        double @NotNull [] relativeLocation = object.getCoordinates().getLocation().getCartesian();
        return new double[]{
                parentLocation[0] + relativeLocation[0],
                parentLocation[1] + relativeLocation[1]};
    }

    //
    static void paintCenterMarker(@NotNull Graphics g,
                                  double @NotNull [] drawCenter,
                                  @NotNull Color color) {
        int centerMarkerSize = 20;
        drawCross(g, drawCenter, centerMarkerSize, color);
    }

    private static void drawCross(@NotNull Graphics g,
                                  double @NotNull [] drawCenter, int size,
                                  @NotNull Color color) {
        g.setColor(color);
        g.drawLine( //horizontal line
                (int) (drawCenter[0] - size / 2), (int) (drawCenter[1]),
                (int) (drawCenter[0] + size / 2), (int) (drawCenter[1]));
        g.drawLine( //vertical line
                (int) (drawCenter[0]), (int) (drawCenter[1] - size / 2),
                (int) (drawCenter[0]), (int) (drawCenter[1] + size / 2));
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
    static void drawCircleAtCenter(@NotNull Graphics g,
                                   double @NotNull [] drawCenter, double scaledSize,
                                   @NotNull Color color, boolean fill) {
        g.setColor(color);
        if (fill) {
            g.fillOval(
                    (int) (drawCenter[0] - scaledSize / 2),
                    (int) (drawCenter[1] - scaledSize / 2),
                    (int) scaledSize, (int) scaledSize);
        } else {
            g.drawOval(
                    (int) (drawCenter[0] - scaledSize / 2),
                    (int) (drawCenter[1] - scaledSize / 2),
                    (int) scaledSize, (int) scaledSize);
        }
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
        drawCircleAtCenter(g, drawCenter, scaledDistance * 2, orbitColor, false);
    }

    //
    static void paintInfluenceRadius(@NotNull Graphics g, @NotNull ScaledOffsetPainter painter,
                                     @NotNull BaryObject object,
                                     double @NotNull [] drawableCenter) throws TopLevelObjectException {
        double scaledInfluenceRadius = painter.scaleValue(object.getInfluenceRadius());
        drawCircleAtCenter(g, drawableCenter, scaledInfluenceRadius * 2, object.getColor(), false);
    }

    //
    static void paintObjectInfo(@NotNull Graphics g, @NotNull BaryObject object, double @NotNull [] drawCenter) {
        int @NotNull [] textOffset = new int [] {-20, 30};
        int lineHeight = 15;
        paintObjectInfoLines(g, drawCenter, textOffset, lineHeight, new ArrayList<>() {{
            add(object.getName());
            add("M: " + ((int) (object.getMass() * 10)) / 10.0);
            try {
                double influenceRadius = object.getInfluenceRadius();
                add("SOI: " + ((int) (influenceRadius * 10)) / 10.0);
            } catch (@NotNull TopLevelObjectException ignored) {}
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