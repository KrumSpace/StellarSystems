package baryGraphics.painters;

import java.util.Collections;
import java.util.List;
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
        @NotNull List<@NotNull BasicBaryObject> unmodifiableList = Collections.unmodifiableList(container.getObjects());
        for (@NotNull BasicBaryObject object : unmodifiableList) {
            getGenericObjectPainter().paint(g, object, absoluteLocation);
        }
    }
}