package testGraphics;

import java.awt.Color;
import java.awt.Graphics;

import org.jetbrains.annotations.NotNull;

import baryModel.BaryObject;
import baryModel.SimpleBody;
import baryModel.BarySimpleObject;
import baryModel.BarySystem;
import baryModel.BaryModel;

//
class BaryPainter {
    private static final int @NotNull [] DEFAULT_DRAW_OFFSET = new int [] {400, 300};
    private static final double SCALE = 2.0;
    private final @NotNull BaryModel model;
    private int @NotNull [] drawOffset;

    //
    protected BaryPainter(@NotNull BaryModel model, int @NotNull [] drawOffset) {
        this.model = model;
        this.drawOffset = drawOffset;
    }

    //
    protected BaryPainter(@NotNull BaryModel model) {
        this(model, DEFAULT_DRAW_OFFSET);
    }

    //
    public void setDrawOffset(int @NotNull [] offset) {
        this.drawOffset = offset;
    }

    //
    protected void paint(@NotNull Graphics g) {
        double @NotNull [] universeParentLocation = new double [2];
        paintBaryObject(g, model.getUniverse(), universeParentLocation);
    }

    private void paintBaryObject(@NotNull Graphics g,
                                 @NotNull BaryObject object,
                                 double @NotNull [] parentLocation) {
        double @NotNull []
                relativeLocation = object.getCoordinates().getLocation().getCartesian(),
                absoluteLocation = new double [] {
                        parentLocation[0] + relativeLocation[0],
                        parentLocation[1] + relativeLocation[1]};
        if (object instanceof BarySimpleObject) {
            paintSimpleBaryObject(g, (BarySimpleObject) object, absoluteLocation);
        } else if (object instanceof BarySystem) {
            paintBarySystem(g, (BarySystem) object, absoluteLocation);
        } else {
            throw new RuntimeException("BaryObject not of a recognized type!");
        }
        paintInfluenceRadius(g, object, absoluteLocation);
    }

    private void paintSimpleBaryObject(@NotNull Graphics g,
                                       @NotNull BarySimpleObject simpleObject,
                                       double @NotNull [] absoluteLocation) {
        double @NotNull []
                scaledLocation = scaleLocation(absoluteLocation),
                drawCenter = getDrawableFromScaled(scaledLocation);
        @NotNull SimpleBody simpleBody = simpleObject.getSimpleBody();
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
        paintSystemCenter(g, system, scaledLocation);
        //TODO: paint some general system-wide data here

        for (BaryObject object : system.getObjects()) {
            paintOrbit(g, object, scaledLocation);
            paintBaryObject(g, object, absoluteLocation);
        }
    }

    private void paintOrbit(@NotNull Graphics g,
                            @NotNull BaryObject object,
                            double @NotNull [] scaledLocation) {
        double @NotNull [] drawCenter = getDrawableFromScaled(scaledLocation);
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

    private void paintSystemCenter(@NotNull Graphics g,
                                   @NotNull BarySystem system,
                                   double @NotNull [] scaledLocation) {
        int centerMarkerSize = 10;
        g.setColor(system.getColor());
        g.fillOval(
                (int) (scaledLocation[0] - centerMarkerSize / 2 + drawOffset[0]),
                (int) (scaledLocation[1] - centerMarkerSize / 2 + drawOffset[1]),
                centerMarkerSize, centerMarkerSize);
    }

    private void paintInfluenceRadius(@NotNull Graphics g,
                                      @NotNull BaryObject object,
                                      double @NotNull [] absoluteLocation) {
        double @NotNull[] drawableCenter = getDrawableFromScaled(scaleLocation(absoluteLocation));
        double scaledInfluenceRadius = scaleValue(object.getInfluenceRadius());
        g.setColor(object.getColor());
        g.drawOval(
                (int) (drawableCenter[0] - scaledInfluenceRadius / 2),
                (int) (drawableCenter[1] - scaledInfluenceRadius / 2),
                (int) scaledInfluenceRadius, (int) scaledInfluenceRadius);
    }

    private static double scaleValue(double value) {
        return value / SCALE;
    }

    private static double @NotNull [] scaleLocation(double @NotNull [] actual) {
        return new double [] {scaleValue(actual[0]), scaleValue(actual[1])};
    }

    private double @NotNull [] getDrawableFromScaled(double @NotNull [] scaledLocation) {
        return new double [] {
                scaledLocation[0] + drawOffset[0],
                scaledLocation[1] + drawOffset[1]};
    }
}