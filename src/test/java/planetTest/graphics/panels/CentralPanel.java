package planetTest.graphics.panels;

import java.awt.Color;
import java.awt.Graphics;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import planetTest.planetModel.PlanetContainer;
import planetTest.planetGraphics.PlanetDrawPanel;

//
public final class CentralPanel extends PlanetDrawPanel {
    private static final @NotNull Color TEXT_COLOR = Color.white;

    //
    public CentralPanel(@NotNull PlanetContainer planetContainer,
                        @Nullable Color borderColor, @Nullable Color diagonalColor) {
        super(
                planetContainer,
                borderColor, true,
                diagonalColor, true);
    }

    //
    @Override
    public void mainPaint(@NotNull Graphics g) {
        super.mainPaint(g);
        g.setColor(TEXT_COLOR);
        g.drawString("A planet test", 100, 100);
        // Paint more stuff here, if needed.
    }
}