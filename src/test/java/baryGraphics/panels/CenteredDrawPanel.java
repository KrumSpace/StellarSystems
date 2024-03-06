package baryGraphics.panels;

import java.awt.Color;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import commonGraphics.panels.MinimalPanel;

//
public abstract class CenteredDrawPanel extends MinimalPanel {
    //
    public CenteredDrawPanel(@Nullable Color background,
                             @Nullable Color borderColor, boolean drawBorders,
                             @Nullable Color diagonalColor, boolean drawDiagonals) {
        super(background, borderColor, drawBorders, diagonalColor, drawDiagonals);
    }

    //
    public final double @NotNull [] getPanelCenter() {
        return new double [] {getWidth() / 2.0, getHeight() / 2.0};
    }

    //for having {0, 0} at the center of the panel
    public final double @NotNull [] applyDrawCenterOffset(double @NotNull [] scaledRelativeLocation) {
        double @NotNull [] panelCenter = getPanelCenter();
        return new double [] {
                scaledRelativeLocation[0] + panelCenter[0],
                scaledRelativeLocation[1] + panelCenter[1]};
    }
}