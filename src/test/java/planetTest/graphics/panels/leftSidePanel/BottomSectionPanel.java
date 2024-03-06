package planetTest.graphics.panels.leftSidePanel;

import java.awt.Color;
import java.awt.Graphics;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

//
public final class BottomSectionPanel extends AbstractSectionPanel {
    private static final int PANEL_HEIGHT = 100;

    //
    public BottomSectionPanel(@Nullable Color borderColor, @Nullable Color diagonalColor) {
        super(
                PANEL_HEIGHT,
                borderColor, true,
                diagonalColor, true);
    }

    //
    @Override
    public void mainPaint(@NotNull Graphics g) {
        //TODO: paint stuff here.
    }
}