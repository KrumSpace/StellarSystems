package commonGraphics.panels.sidePanels.commonLeftSidePanel;

import java.awt.Color;
import java.awt.Graphics;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static commonGraphics.ColorUtils.getGray;
import static commonGraphics.StringUtils.drawNumberedString;
import commonGraphics.panels.sidePanels.AbstractSectionPanel;
import commonGraphics.UpdatingWindow;

//
public class CommonBottomSection extends AbstractSectionPanel {
    private static final int PANEL_HEIGHT = 55;
    private static final int @NotNull [] TEXT_LOCATION = new int[]{10, 10};
    private static final @NotNull Color
            HEADING_COLOR = Color.white,
            TEXT_COLOR = getGray(170, 255);
    private final @NotNull UpdatingWindow window;

    //
    CommonBottomSection(@NotNull UpdatingWindow window,
                        @Nullable Color borderColor, boolean drawBorders,
                        @Nullable Color diagonalColor, boolean drawDiagonals) {
        super(
                PANEL_HEIGHT,
                borderColor, drawBorders,
                diagonalColor, drawDiagonals);
        this.window = window;
    }

    //
    @Override
    public void mainPaint(@NotNull Graphics g) {
        g.setColor(HEADING_COLOR);
        drawInfoLine(g, "Window info", 1);
        g.setColor(TEXT_COLOR);
        drawInfoLine(g, "Preferred FPS: " + window.getPreferredFrameRate(), 2);
        // Paint more stuff here, if needed.
    }

    private void drawInfoLine(@NotNull Graphics g, @Nullable String line, int lineNumber) {
        drawNumberedString(g, line, TEXT_LOCATION, lineNumber);
    }
}