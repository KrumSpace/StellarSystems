package commonGraphics.panels.sidePanels;

import java.awt.Color;

import org.jetbrains.annotations.Nullable;

import static commonGraphics.ColorUtils.TRANSPARENT_BLACK;
import commonGraphics.panels.FixedHorizontalPanel;

//
public abstract class AbstractSectionPanel extends FixedHorizontalPanel {
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