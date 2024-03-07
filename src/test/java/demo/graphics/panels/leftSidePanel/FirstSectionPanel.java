package demo.graphics.panels.leftSidePanel;

import java.awt.Color;
import java.awt.Graphics;

import baryModel.BaryUniverse;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static commonGraphics.StringUtils.drawNumberedString;
import commonGraphics.panels.sidePanels.AbstractSectionPanel;

//
final class FirstSectionPanel extends AbstractSectionPanel {
    private static final int PANEL_HEIGHT = 100;
    private static final int @NotNull [] TEXT_LOCATION = new int [] {10, 10};
    private static final @NotNull Color TEXT_COLOR = Color.white;
    @SuppressWarnings({"unused", "FieldCanBeLocal"})
    private final @NotNull BaryUniverse universe;

    //
    FirstSectionPanel(@NotNull BaryUniverse universe,
                      @Nullable Color borderColor, boolean drawBorders,
                      @Nullable Color diagonalColor, boolean drawDiagonals) {
        super(
                PANEL_HEIGHT,
                borderColor, drawBorders,
                diagonalColor, drawDiagonals);
        this.universe = universe;
    }

    //
    @Override
    public void mainPaint(@NotNull Graphics g) {
        g.setColor(TEXT_COLOR);
        drawInfoLine(g, "Universe info", 1);
        drawInfoLine(g, "Universe info coming soon...", 2);
        // Paint more stuff here, if needed.
    }

    private void drawInfoLine(@NotNull Graphics g, @Nullable String line, int lineNumber) {
        drawNumberedString(g, line, TEXT_LOCATION, lineNumber);
    }
}