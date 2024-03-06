package demo.graphics.panels;

import java.awt.Color;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import baryModel.BaryUniverse;

import baryGraphics.Observer;
import baryGraphics.panels.UniverseDrawPanel;

//
public final class CentralPanel extends UniverseDrawPanel {
    private static final boolean DRAW_DIAGONALS = true;

    //
    public CentralPanel(@NotNull BaryUniverse universe, @NotNull Observer observer,
                        @Nullable Color borderColor, @Nullable Color diagonalColor) {
        super(
                universe, observer,
                borderColor, true,
                diagonalColor, DRAW_DIAGONALS);
    }
}