package baryGraphics.painters;

import java.awt.Graphics;

import org.jetbrains.annotations.NotNull;

import baryModel.systems.AbstractBarySystem;
import baryGraphics.panels.UniverseDrawPanel;

//
final class BarySystemPainter implements ObjectPainterInterface<AbstractBarySystem>, ObjectContainerPainterInterface {
    private final @NotNull UniverseDrawPanel universePanel;

    //
    BarySystemPainter(@NotNull UniverseDrawPanel universePanel) {
        this.universePanel = universePanel;
    }

    //
    @Override
    public void paint(@NotNull Graphics g,
                      @NotNull AbstractBarySystem system,
                      double @NotNull [] absoluteLocation) {
        paintMembers(g, system, absoluteLocation);
        double @NotNull [] drawLocation = universePanel.getDrawableFromAbsolute(absoluteLocation);
        /*
        TODO: paint some general system-wide data here, such as:
            * member count
            * etc
        */
        universePanel.getPaintUtilities().paintCenterMarker(g, drawLocation, system.getColor());
    }

    //
    @Override
    public @NotNull GenericObjectPainter getGenericObjectPainter() {
        return universePanel.getPainters().getGenericObjectPainter();
    }
}