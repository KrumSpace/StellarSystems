package baryGraphics.panels;

import java.util.Objects;
import java.util.List;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Graphics;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static consoleUtils.stringTools.NumberFormatter.doubleToString;

import kinetics.Location;
import kinetics.Velocity;
import kinetics.SimpleVector;
import baryModel.exceptions.TopLevelObjectException;
import baryModel.basicModels.BasicBaryObject;
import baryModel.basicModels.InfluentialObject;

import baryGraphics.CommonPainting;

//
public final class UniversePaintUtilities {
    private static final int CENTER_MARKER_SIZE = 20;
    private final @NotNull UniverseDrawPanel panel;

    //
    UniversePaintUtilities(@NotNull UniverseDrawPanel panel) {
        this.panel = panel;
    }

    //
    public void paintConnection(@NotNull Graphics g, @NotNull Color color,
                                double @NotNull [] absoluteLocation, double @NotNull [] parentAbsoluteLocation) {
        double @NotNull []
                drawLocation = panel.getDrawableFromAbsolute(absoluteLocation),
                parentDrawLocation = panel.getDrawableFromAbsolute(parentAbsoluteLocation);
        g.setColor(color);
        g.drawLine(
                (int) drawLocation[0], (int) drawLocation[1],
                (int) parentDrawLocation[0], (int) parentDrawLocation[1]);
    }

    //
    public void paintOrbit(@NotNull Graphics g, @NotNull BasicBaryObject object, double @NotNull [] parentAbsoluteLocation) {
        double @NotNull [] drawCenter = panel.getDrawableFromAbsolute(parentAbsoluteLocation);
        double
                distance = object.getLocation().getRadius(),
                scaledDistance = panel.scaleValue(distance);
        @NotNull Color orbitColor = object.getColor();
        CommonPainting.drawCircleAtCenter(g, drawCenter, scaledDistance * 2, orbitColor, false);
    }

    //
    public void paintInfluenceRadius(@NotNull Graphics g, @NotNull InfluentialObject object,
                                     double @NotNull [] drawableCenter) throws TopLevelObjectException {
        double scaledInfluenceRadius = panel.scaleValue(object.getInfluenceRadius());
        CommonPainting.drawCircleAtCenter(g, drawableCenter, scaledInfluenceRadius * 2, object.getColor(), false);
    }

    //
    public void paintCenterMarker(@NotNull Graphics g, double @NotNull [] drawCenter, @NotNull Color color) {
        CommonPainting.drawCross(g, drawCenter, CENTER_MARKER_SIZE, color);
    }

    //
    public void paintVelocity(@NotNull Graphics g, @NotNull BasicBaryObject object,
                              double @NotNull [] drawCenter) {
        paintVector(g, object.getVelocity(), drawCenter, 4, new Color(255, 50, 0));
    }

    //
    public void paintAcceleration(@NotNull Graphics g, @NotNull BasicBaryObject object,
                                  double @NotNull [] drawCenter) {
        paintVector(g, object.getAcceleration(), drawCenter, 100, Color.cyan);
    }

    private void paintVector(@NotNull Graphics g, @NotNull SimpleVector vector,
                             double @NotNull [] drawCenter, double extraScale, @NotNull Color color) {
        double
                axScaled = panel.scaleValue(vector.getX()) * extraScale,
                ayScaled = panel.scaleValue(vector.getY()) * extraScale,
                azScaled = panel.scaleValue(vector.getZ()) * extraScale; //currently unused
        g.setColor(color);
        g.drawLine(
                (int) drawCenter[0], (int) drawCenter[1],
                (int) (drawCenter[0] + axScaled), (int) (drawCenter[1] + ayScaled));
    }

    //
    public void paintObjectInfo(@NotNull Graphics g, @NotNull BasicBaryObject object,
                                double @NotNull [] drawCenter, @NotNull Color color) {
        int @NotNull [] textOffset = new int [] {-20, 30};
        int lineHeight = 15;
        g.setColor(color);
        paintObjectInfoLines(g, drawCenter, textOffset, lineHeight, getObjectInfoLines(object));
    }

    @SuppressWarnings("CommentedOutCode")
    private @NotNull List<@Nullable String> getObjectInfoLines(@NotNull BasicBaryObject object) {
        return new ArrayList<>() {{
            add(object.getName());
            @NotNull StringBuilder massAndSOIStringBuilder = new StringBuilder();
            massAndSOIStringBuilder.append("M: ").append(doubleToString(object.getMass(), 0));
            if (object instanceof @NotNull InfluentialObject influentialObject) {
                try {
                    double influenceRadius = influentialObject.getInfluenceRadius();
                    massAndSOIStringBuilder.append(", ").append("SOI: ").append(doubleToString(influenceRadius, 0));
                } catch (@NotNull TopLevelObjectException ignored) {}
            }
            add(massAndSOIStringBuilder.toString());
            @NotNull Location location = object.getLocation();
            /*add("XYZ: " + commonDoubleArrayString(new double [] {
                    location.getX(),
                    location.getY(),
                    location.getZ()}));*/
            add("Polar: " + commonDoubleArrayString(new double [] {
                    location.getRadius(),
                    location.getHorizontalAngle(),
                    location.getVerticalAngle()}));
            @NotNull Velocity velocity = object.getVelocity();
            /*add("Vel: " + commonDoubleArrayString(new double [] {
                    velocity.getX(),
                    velocity.getY(),
                    velocity.getZ()}));*/
            add("Vel (pol): " + commonDoubleArrayString(new double [] {
                    velocity.getRadius(),
                    velocity.getHorizontalAngle(),
                    velocity.getVerticalAngle()}));
        }};
    }

    private @NotNull String commonDoubleArrayString(double @NotNull [] array) {
        return doubleArrayString(array, 1, ", ");
    }

    @SuppressWarnings("SameParameterValue")
    private @NotNull String doubleArrayString(double @NotNull [] array, int decimalPlaces, @NotNull String separator) {
        @NotNull StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            if (i > 0) {
                stringBuilder.append(separator);
            }
            stringBuilder.append(doubleToString(array[i], decimalPlaces));
        }
        return stringBuilder.toString();
    }

    private void paintObjectInfoLines(@NotNull Graphics g, double @NotNull [] drawCenter,
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