package baryGraphics.painters;

import java.awt.Graphics;

import org.jetbrains.annotations.NotNull;

import baryModel.exceptions.UnrecognizedBaryObjectTypeException;
import baryModel.basicModels.BasicBaryObject;

//
interface ObjectPainterInterface<T extends BasicBaryObject> {
    //
    void paint(@NotNull Graphics g, @NotNull T object, double @NotNull [] absoluteLocation)
            throws UnrecognizedBaryObjectTypeException;
}