package utils;

import java.awt.Color;

import org.jetbrains.annotations.NotNull;

//for graphical purposes
public interface Representable {
    //for graphical purposes
    @NotNull String getName();

    //for graphical purposes
    @NotNull Color getColor();
}