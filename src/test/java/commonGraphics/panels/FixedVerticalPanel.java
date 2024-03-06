package commonGraphics.panels;

import java.awt.Color;

import org.jetbrains.annotations.Nullable;

//a vertical panel with fixed width
public abstract class FixedVerticalPanel extends FixedSizePanel {
    //
    public FixedVerticalPanel(int width,
                              @Nullable Color background,
                              @Nullable Color borderColor, boolean drawBorders,
                              @Nullable Color diagonalColor, boolean drawDiagonals) {
        super(
                new int [] {width, Integer.MAX_VALUE},
                background, borderColor, drawBorders, diagonalColor, drawDiagonals);
    }
}