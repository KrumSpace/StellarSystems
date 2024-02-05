package baryGraphics.painters;

import java.awt.Graphics;

import org.jetbrains.annotations.NotNull;

import baryModel.BaryObject;
import baryModel.exceptions.UnrecognizedBaryObjectTypeException;

//
interface ObjectPainterInterface<T extends BaryObject> {
    //
    void paint(@NotNull Graphics g, @NotNull T object, double @NotNull [] absoluteLocation)
            throws UnrecognizedBaryObjectTypeException;
}