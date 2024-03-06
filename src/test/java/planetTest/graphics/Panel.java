package planetTest.graphics;

import java.awt.Color;
import java.awt.Graphics;

import org.jetbrains.annotations.NotNull;

import commonGraphics.panels.MinimalPanel;

//
final class Panel extends MinimalPanel {
    private static final @NotNull Color
            BACKGROUND = Color.black,
            BORDERS_AND_DIAGONALS = Color.darkGray,
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
        g.drawString("A planet test", 100, 100);
        // paint more stuff here, if needed
    }
}