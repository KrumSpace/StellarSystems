package commonGraphics.panels;

import java.awt.Color;

import org.jetbrains.annotations.Nullable;

//a horizontal panel with fixed height
public abstract class FixedHorizontalPanel extends FixedSizePanel {
    //
    public FixedHorizontalPanel(int height,
                                @Nullable Color background,
                                @Nullable Color borderColor, boolean drawBorders,
                                @Nullable Color diagonalColor, boolean drawDiagonals) {
        super(
                new int [] {Integer.MAX_VALUE, height},
                background, borderColor, drawBorders, diagonalColor, drawDiagonals);
    }
}