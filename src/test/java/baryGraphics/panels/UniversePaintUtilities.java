package baryGraphics.panels;

import java.util.Objects;
import java.util.List;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Graphics;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import baryModel.BaryObject;
import baryModel.exceptions.TopLevelObjectException;
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
    public void paintCenterMarker(@NotNull Graphics g, double @NotNull [] drawCenter, @NotNull Color color) {
        CommonPainting.drawCross(g, drawCenter, CENTER_MARKER_SIZE, color);
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
    public void paintOrbit(@NotNull Graphics g, @NotNull BaryObject object, double @NotNull [] parentAbsoluteLocation) {
        double @NotNull [] drawCenter = panel.getDrawableFromAbsolute(parentAbsoluteLocation);
        double
                distance = object.getCoordinates().getLocation().getRadial()[0],
                scaledDistance = panel.scaleValue(distance);
        @NotNull Color orbitColor = object.getColor();
        CommonPainting.drawCircleAtCenter(g, drawCenter, scaledDistance * 2, orbitColor, false);
    }

    //
    public void paintInfluenceRadius(@NotNull Graphics g, @NotNull BaryObject object,
                                     double @NotNull [] drawableCenter) throws TopLevelObjectException {
        double scaledInfluenceRadius = panel.scaleValue(object.getInfluenceRadius());
        CommonPainting.drawCircleAtCenter(g, drawableCenter, scaledInfluenceRadius * 2, object.getColor(), false);
    }

    //
    public void paintObjectInfo(@NotNull Graphics g, @NotNull BaryObject object, double @NotNull [] drawCenter) {
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