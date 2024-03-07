package demo.graphics.panels.leftSidePanel;

import java.awt.Color;
import java.awt.Graphics;

import baryGraphics.Observer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static consoleUtils.stringTools.NumberFormatter.doubleToString;

import static commonGraphics.StringUtils.drawNumberedString;
import commonGraphics.panels.sidePanels.AbstractSectionPanel;

//
final class SecondSectionPanel extends AbstractSectionPanel {
    private static final int PANEL_HEIGHT = 100;
    private static final int @NotNull [] TEXT_LOCATION = new int [] {10, 10};
    private static final @NotNull Color TEXT_COLOR = Color.white;
    private final @NotNull Observer observer;

    //
    SecondSectionPanel(@NotNull Observer observer,
                       @Nullable Color borderColor, boolean drawBorders,
                       @Nullable Color diagonalColor, boolean drawDiagonals) {
        super(
                PANEL_HEIGHT,
                borderColor, drawBorders,
                diagonalColor, drawDiagonals);
        this.observer = observer;
    }

    //
    @Override
    public void mainPaint(@NotNull Graphics g) {
        g.setColor(TEXT_COLOR);
        drawInfoLine(g, "Observer info", 1);
        double @NotNull [] observerLocation = observer.getLocation();
        @NotNull String
                roundedX = doubleToString(observerLocation[0], 3),
                roundedY = doubleToString(observerLocation[1], 3);
        drawInfoLine(g, "Location: " + roundedX + ", " + roundedY, 2);
        @NotNull String roundedScale = doubleToString(observer.getScale(), 3);
        drawInfoLine(g, "Scale: " + roundedScale, 3);
        // Paint more stuff here, if needed.
    }

    private void drawInfoLine(@NotNull Graphics g, @Nullable String line, int lineNumber) {
        drawNumberedString(g, line, TEXT_LOCATION, lineNumber);
    }
}