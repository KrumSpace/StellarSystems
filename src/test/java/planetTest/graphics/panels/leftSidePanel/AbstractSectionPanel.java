package planetTest.graphics.panels.leftSidePanel;

import java.awt.Color;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import commonGraphics.panels.FixedHorizontalPanel;

//
public abstract class AbstractSectionPanel extends FixedHorizontalPanel {
    private static final @NotNull Color TRANSPARENT_BLACK = new Color(0, 0, 0, 0);

    //
    public AbstractSectionPanel(int height,
                                @Nullable Color borderColor, boolean drawBorders,
                                @Nullable Color diagonalColor, boolean drawDiagonals) {
        super(
                height, TRANSPARENT_BLACK,
                borderColor, drawBorders,
                diagonalColor, drawDiagonals);
    }
}