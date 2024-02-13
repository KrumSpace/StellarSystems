package baryGraphics.painters;

import java.awt.Graphics;

import org.jetbrains.annotations.NotNull;

import kinetics.Location;
import baryModel.BaryUniverse;

import baryGraphics.panels.UniverseDrawPanel;

//
public final class PainterContainer {
    private final @NotNull GenericObjectPainter genericObjectPainter;
    private final @NotNull BarySystemPainter systemPainter;
    private final @NotNull SimpleObjectPainter simpleObjectPainter;

    //
    public PainterContainer(@NotNull UniverseDrawPanel universePanel) {
        genericObjectPainter = new GenericObjectPainter(universePanel, this);
        systemPainter = new BarySystemPainter(universePanel);
        simpleObjectPainter = new SimpleObjectPainter(universePanel);
    }

    //
    @NotNull GenericObjectPainter getGenericObjectPainter() {
        return genericObjectPainter;
    }

    //
    @NotNull BarySystemPainter getSystemPainter() {
        return systemPainter;
    }

    //
    @NotNull SimpleObjectPainter getSimpleObjectPainter() {
        return simpleObjectPainter;
    }

    //
    public void paintUniverse(@NotNull Graphics g, @NotNull BaryUniverse universe) {
        @NotNull Location location = universe.getLocation();
        double @NotNull [] locationArray = new double[3];/*new double [] {
                location.getX(),
                location.getY(),
                location.getZ()};*/
        genericObjectPainter.paint(g, universe, locationArray);
    }
}