package planetTest.graphics.panels.leftSidePanel;

import java.awt.Color;
import java.awt.Graphics;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static commonGraphics.StringUtils.drawNumberedString;
import commonGraphics.panels.sidePanels.AbstractSectionPanel;
import commonGraphics.UpdatingWindow;

//
final class BottomSectionPanel extends AbstractSectionPanel {
    private static final int PANEL_HEIGHT = 55;
    private static final int @NotNull [] TEXT_LOCATION = new int [] {10, 10};
    private static final @NotNull Color TEXT_COLOR = Color.white;
    private final @NotNull UpdatingWindow window;

    //
    BottomSectionPanel(@NotNull UpdatingWindow window,
                       @Nullable Color borderColor, @Nullable Color diagonalColor) {
        super(
                PANEL_HEIGHT,
                borderColor, true,
                diagonalColor, false);
        this.window = window;
    }

    //
    @Override
    public void mainPaint(@NotNull Graphics g) {
        g.setColor(TEXT_COLOR);
        drawInfoLine(g, "Window info, etc", 1);
        drawInfoLine(g, "Preferred FPS: " + window.getPreferredFrameRate(), 2);
        // Paint more stuff here, if needed.
    }

    private void drawInfoLine(@NotNull Graphics g, @Nullable String line, int lineNumber) {
        drawNumberedString(g, line, TEXT_LOCATION, lineNumber);
    }
}