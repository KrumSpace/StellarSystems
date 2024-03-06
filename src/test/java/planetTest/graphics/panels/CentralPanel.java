package planetTest.graphics.panels;

import java.awt.Color;
import java.awt.Graphics;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import baryGraphics.panels.CenteredDrawPanel;
import baryGraphics.panels.ScaledDrawInterface;
import planetTest.planetModel.Planet;
import planetTest.planetModel.PlanetContainer;

//
public final class CentralPanel extends CenteredDrawPanel implements ScaledDrawInterface {
    private static final double DEFAULT_SCALE = 2.0;
    private static final @NotNull Color
            BACKGROUND = Color.black,
            TEXT_COLOR = Color.white;
    private double scale;
    private final @NotNull PlanetContainer planetContainer;

    //
    public CentralPanel(@NotNull PlanetContainer planetContainer,
                        @Nullable Color borderColor, @Nullable Color diagonalColor) {
        super(
                BACKGROUND,
                borderColor, true,
                diagonalColor, true);
        scale = DEFAULT_SCALE;
        this.planetContainer = planetContainer;
    }

    //
    @Override
    public double getScale() {
        return scale;
    }

    //
    @Override
    public void mainPaint(@NotNull Graphics g) {
        @Nullable Planet planet = planetContainer.getPlanet();
        if (planet != null) paintPlanet(g, planet);
        g.setColor(TEXT_COLOR);
        g.drawString("A planet test", 100, 100);
        // paint more stuff here, if needed
    }

    private void paintPlanet(@NotNull Graphics g, @NotNull Planet planet) {
        g.setColor(planet.getColor());
        double scaledRadius = scaleValue(planet.getRadius());
        double @NotNull []
                relativeLocation = new double[2], // relative to yet non-implemented observer
                drawLocation = applyDrawCenterOffset(scaleLocation(relativeLocation));
        int scaledIntegerDiameter = (int) (scaledRadius * 2);
        g.fillOval(
                (int) (drawLocation[0] - scaledRadius),
                (int) (drawLocation[1] - scaledRadius),
                scaledIntegerDiameter, scaledIntegerDiameter);
    }
}