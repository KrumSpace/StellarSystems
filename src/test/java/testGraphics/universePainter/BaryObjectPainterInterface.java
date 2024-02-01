package testGraphics.universePainter;

import java.awt.Graphics;

import org.jetbrains.annotations.NotNull;

import baryModel.BaryObject;

//
public interface BaryObjectPainterInterface<T extends BaryObject> {
    //
    void paint(@NotNull Graphics g,
               @NotNull T object,
               double @NotNull [] absoluteLocation);
}