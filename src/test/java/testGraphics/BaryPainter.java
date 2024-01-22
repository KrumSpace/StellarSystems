package testGraphics;

import java.awt.Color;
import java.awt.Graphics;

import baryModel.BaryObject;
import baryModel.BarySimpleObject;
import baryModel.BarySystem;
import baryModel.BaryModel;

//
class BaryPainter {
    private static final int [] DEFAULT_DRAW_OFFSET = new int [] {400, 300};
    private static final double SCALE = 2.0;
    private final BaryModel model;
    private int [] drawOffset;

    //
    protected BaryPainter(BaryModel model, int [] drawOffset) {
        this.model = model;
        this.drawOffset = drawOffset;
    }

    //
    protected BaryPainter(BaryModel model) {
        this(model, DEFAULT_DRAW_OFFSET);
    }

    //
    public void setDrawOffset(int[] offset) {
        this.drawOffset = offset;
    }

    //
    protected void paint(Graphics g) {
        double [] universeParentLocation = new double [2];
        paintBaryObject(g, model.getUniverse(), universeParentLocation);
    }

    private void paintBaryObject(Graphics g,
                                 BaryObject object,
                                 double [] parentLocation) {
        double []
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
    }

    private void paintSimpleBaryObject(Graphics g,
                                       BarySimpleObject simpleObject,
                                       double [] absoluteLocation) {
        double [] scaledLocation = new double [] {
                absoluteLocation[0] / SCALE,
                absoluteLocation[1] / SCALE};
        double
                actualSize = 40,
                scaledSize = actualSize / SCALE;
        g.setColor(simpleObject.getColor());
        g.fillOval(
                (int) (scaledLocation[0] - scaledSize / 2 + drawOffset[0]),
                (int) (scaledLocation[1] - scaledSize / 2 + drawOffset[1]),
                (int) scaledSize, (int) scaledSize);
        //TODO: paint an individual simple body here
    }

    private void paintBarySystem(Graphics g,
                                 BarySystem system,
                                 double [] absoluteLocation) {

        g.setColor(system.getColor());
        int centerMarkerSize = 10;
        double [] scaledLocation = new double [] {
                absoluteLocation[0] / SCALE,
                absoluteLocation[1] / SCALE};
        g.fillOval(
                (int) (scaledLocation[0] - centerMarkerSize / 2 + drawOffset[0]),
                (int) (scaledLocation[1] - centerMarkerSize / 2 + drawOffset[1]),
                centerMarkerSize, centerMarkerSize);
        //TODO: paint some general system-wide data here

        double furthestDistance = 0;
        for (BaryObject object : system.getObjects()) {
            double distance = object.getCoordinates().getLocation().getRadial()[0];
            if (distance > furthestDistance) {
                furthestDistance = distance;
            }
            Color orbitColor = object.getColor();
            g.setColor(orbitColor);
            double scaledDistance = distance / SCALE;
            g.drawOval(
                    (int) (scaledLocation[0] - scaledDistance + drawOffset[0]),
                    (int) (scaledLocation[1] - scaledDistance + drawOffset[1]),
                    (int) scaledDistance * 2, (int) scaledDistance * 2);
            paintBaryObject(g, object, absoluteLocation);
        }
        g.setColor(system.getColor());
        double scaledFurthestDistance = furthestDistance / SCALE;
        g.drawOval(
                (int) (scaledLocation[0] - scaledFurthestDistance + drawOffset[0]),
                (int) (scaledLocation[1] - scaledFurthestDistance + drawOffset[1]),
                (int) scaledFurthestDistance * 2, (int) scaledFurthestDistance * 2);
    }
}