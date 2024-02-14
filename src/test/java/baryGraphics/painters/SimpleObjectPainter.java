package baryGraphics.painters;

import java.awt.Graphics;

import org.jetbrains.annotations.NotNull;

import baryModel.simpleObjects.PhysicalBody;
import baryModel.simpleObjects.PhysicalBaryObject;

import baryGraphics.CommonPainting;
import baryGraphics.panels.UniverseDrawPanel;

//
final class SimpleObjectPainter implements ObjectPainterInterface<PhysicalBaryObject> {
    private final @NotNull UniverseDrawPanel universePanel;

    //
    SimpleObjectPainter(@NotNull UniverseDrawPanel universePanel) {
        this.universePanel = universePanel;
    }

    //
    @Override
    public void paint(@NotNull Graphics g,
                      @NotNull PhysicalBaryObject simpleObject,
                      double @NotNull [] absoluteLocation) {
        paintPhysicalBody(g, simpleObject.getSimpleBody(), universePanel.getDrawableFromAbsolute(absoluteLocation));
    }

    private void paintPhysicalBody(@NotNull Graphics g,
                                   @NotNull PhysicalBody body,
                                   double @NotNull [] drawCenter) {
        CommonPainting.drawCircleAtCenter(g, drawCenter, universePanel.scaleValue(body.getRadius()), body.getColor(), true);
        //TODO: improve this eventually for more detail
    }
}