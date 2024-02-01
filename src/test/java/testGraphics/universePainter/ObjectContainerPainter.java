package testGraphics.universePainter;

import java.util.Collections;
import java.awt.Graphics;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import baryModel.BaryObject;
import baryModel.BaryObjectContainerInterface;

import testGraphics.generalPainters.ScaledOffsetPainter;

//
abstract class ObjectContainerPainter<T extends BaryObject> extends ScaledOffsetPainter implements BaryObjectPainterInterface<T> {
    //
    public ObjectContainerPainter(int @Nullable [] drawOffset, double scale) {
        super(drawOffset, scale);
    }

    //
    public final void paintMembers(@NotNull Graphics g, @NotNull GenericObjectPainter genericObjectPainter,
                                   @NotNull BaryObjectContainerInterface container,
                                   double @NotNull [] absoluteLocation) {
        for (@NotNull BaryObject object : Collections.unmodifiableList(container.getObjects())) {
            genericObjectPainter.paint(g, object, absoluteLocation);
        }
    }
}