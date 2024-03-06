package test2.graphics;

import java.awt.Color;
import java.awt.Graphics;

import org.jetbrains.annotations.NotNull;

import commonGraphics.panels.MinimalPanel;

//
final class Panel extends MinimalPanel {
    private static final @NotNull Color
            BACKGROUND = Color.black,
            BORDERS_AND_DIAGONALS = Color.orange,
            TEXT_COLOR = Color.white;

    //
    Panel() {
        super(
                BACKGROUND,
                BORDERS_AND_DIAGONALS, true,
                BORDERS_AND_DIAGONALS, true);
    }

    //
    @Override
    public void mainPaint(@NotNull Graphics g) {
        g.setColor(TEXT_COLOR);
        g.drawString("A test window for test 2", 50, 50);
        // paint more stuff here, if needed
    }
}