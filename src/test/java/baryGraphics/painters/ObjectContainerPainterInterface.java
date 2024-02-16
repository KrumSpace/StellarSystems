package baryGraphics.painters;

import java.util.Collections;
import java.awt.Graphics;

import org.jetbrains.annotations.NotNull;

import baryModel.basicModels.BasicBaryObject;
import baryModel.BaryObjectContainerInterface;

//
interface ObjectContainerPainterInterface {
    //
    @NotNull GenericObjectPainter getGenericObjectPainter();

    //
    default void paintMembers(@NotNull Graphics g,
                              @NotNull BaryObjectContainerInterface container,
                              double @NotNull [] absoluteLocation) {
        for (@NotNull BasicBaryObject object : Collections.unmodifiableList(container.getObjects())) {
            getGenericObjectPainter().paint(g, object, absoluteLocation);
        }
    }
}