package playerTest.graphics.panels.rightSidePanel;

import java.awt.Color;
import java.awt.Graphics;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static commonGraphics.ColorUtils.getGray;
import static commonGraphics.StringUtils.drawNumberedString;
import commonGraphics.panels.sidePanels.AbstractSectionPanel;

//
final class TopSection extends AbstractSectionPanel {
    private static final int @NotNull [] TEXT_LOCATION = new int [] {10, 10};
    private static final @NotNull Color
            HEADING_COLOR = Color.white,
            TEXT_COLOR = getGray(170, 255);

    //
    TopSection(int height,
               @Nullable Color borderColor, boolean drawBorders,
               @Nullable Color diagonalColor, boolean drawDiagonals) {
        super(height, borderColor, drawBorders, diagonalColor, drawDiagonals);
    }

    //
    @Override
    public void mainPaint(@NotNull Graphics g) {
        g.setColor(HEADING_COLOR);
        drawInfoLine(g, "Top section", 1);
        g.setColor(TEXT_COLOR);
        drawInfoLine(g, "Coming soon...", 2);
        // Paint more stuff here, if needed.
    }

    private void drawInfoLine(@NotNull Graphics g, @Nullable String line, int lineNumber) {
        drawNumberedString(g, line, TEXT_LOCATION, lineNumber);
    }
}