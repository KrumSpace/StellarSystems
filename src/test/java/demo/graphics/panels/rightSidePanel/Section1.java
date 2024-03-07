package demo.graphics.panels.rightSidePanel;

import java.awt.Color;
import java.awt.Graphics;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static commonGraphics.ColorUtils.getGray;
import static commonGraphics.StringUtils.drawNumberedString;
import commonGraphics.panels.sidePanels.AbstractSectionPanel;

//
final class Section1 extends AbstractSectionPanel {
    private static final int PANEL_HEIGHT = 100;
    private static final int @NotNull [] TEXT_LOCATION = new int [] {10, 10};
    private static final @NotNull Color
            HEADING_COLOR = Color.white,
            TEXT_COLOR = getGray(170, 255);

    //
    Section1(@Nullable Color borderColor, boolean drawBorders,
             @Nullable Color diagonalColor, boolean drawDiagonals) {
        super(PANEL_HEIGHT, borderColor, drawBorders, diagonalColor, drawDiagonals);
    }

    @Override
    public void mainPaint(@NotNull Graphics g) {
        g.setColor(HEADING_COLOR);
        drawInfoLine(g, "Blank section 1", 1);
        g.setColor(TEXT_COLOR);
        drawInfoLine(g, "For future use", 2);
        // Paint more stuff here, if needed.
    }

    private void drawInfoLine(@NotNull Graphics g, @Nullable String line, int lineNumber) {
        drawNumberedString(g, line, TEXT_LOCATION, lineNumber);
    }
}