package planetTest.graphics.panels.leftSidePanel;

import java.awt.Color;
import java.awt.Graphics;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static commonGraphics.ColorUtils.getGray;
import static commonGraphics.StringUtils.drawNumberedString;
import commonGraphics.panels.sidePanels.AbstractSectionPanel;
import planetTest.planetModel.Planet;
import planetTest.planetModel.PlanetContainer;

//
final class SecondSection extends AbstractSectionPanel {
    private static final int PANEL_HEIGHT = 100;
    private static final int @NotNull [] TEXT_LOCATION = new int [] {10, 10};
    private static final @NotNull Color
            HEADING_COLOR = Color.white,
            TEXT_COLOR = getGray(170, 255);
    private final @NotNull PlanetContainer planetContainer;

    //
    SecondSection(@NotNull PlanetContainer planetContainer,
                  @Nullable Color borderColor, @Nullable Color diagonalColor) {
        super(
                PANEL_HEIGHT,
                borderColor, true,
                diagonalColor, false);
        this.planetContainer = planetContainer;
    }

    //
    @Override
    public void mainPaint(@NotNull Graphics g) {
        g.setColor(HEADING_COLOR);
        drawInfoLine(g, "Planet info", 1);
        g.setColor(TEXT_COLOR);
        @Nullable Planet planet = planetContainer.getPlanet();
        if (planet == null) {
            drawInfoLine(g, "Null planet", 2);
        } else {
            drawPlanetInfo(g, planet);
        }
        // Paint more stuff here, if needed.
    }

    private void drawPlanetInfo(@NotNull Graphics g, @NotNull Planet planet) {
        drawInfoLine(g, "Mass: " + planet.getMass(), 2);
        drawInfoLine(g, "Radius: " + planet.getRadius(), 3);
        // Add more info lines here, if needed.
    }

    private void drawInfoLine(@NotNull Graphics g, @Nullable String line, int lineNumber) {
        drawNumberedString(g, line, TEXT_LOCATION, lineNumber);
    }
}