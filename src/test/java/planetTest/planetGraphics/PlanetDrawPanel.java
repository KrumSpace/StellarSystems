package planetTest.planetGraphics;

import java.awt.Color;
import java.awt.Graphics;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static commonGraphics.ColorUtils.getGray;
import commonGraphics.panels.graphicalPanels.CenteredDrawPanel;
import commonGraphics.panels.graphicalPanels.ScaledDrawInterface;
import planetTest.planetModel.Planet;
import planetTest.planetModel.PlanetContainer;

public class PlanetDrawPanel extends CenteredDrawPanel implements ScaledDrawInterface {
    private static final double DEFAULT_SCALE = 2.0;
    private static final @NotNull Color
            BACKGROUND = Color.black,
            TEXT_COLOR = Color.white,
            NULL_PLANET_COLOR = getGray(80, 255);
    private static final double NULL_PLANET_DIAMETER_PROPORTION = 0.4;
    @SuppressWarnings("FieldMayBeFinal")
    private double scale;
    private final @NotNull PlanetContainer planetContainer;

    //
    public PlanetDrawPanel(@NotNull PlanetContainer planetContainer,
                           @Nullable Color borderColor, boolean drawBorders,
                           @Nullable Color diagonalColor, boolean drawDiagonals) {
        super(
                BACKGROUND,
                borderColor, drawBorders,
                diagonalColor, drawDiagonals);
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
        if (planet == null) {
            paintNullPlanet(g);
        } else {
            paintPlanet(g, planet);
        }
        // Paint more stuff here, if needed.
    }

    private void paintNullPlanet(@NotNull Graphics g) {
        g.setColor(NULL_PLANET_COLOR);
        double
                minPanelSize = Math.min(getWidth(), getHeight()),
                diameter =  minPanelSize * NULL_PLANET_DIAMETER_PROPORTION,
                radius = diameter / 2;
        double @NotNull [] center = getPanelCenter();
        g.drawOval((int) (center[0] - radius), (int) (center[1] - radius), (int) diameter, (int) diameter);

        g.setColor(TEXT_COLOR);
        int @NotNull [] textOffset = new int [] {-28, 5};
        g.drawString("Null planet", (int) (center[0] + textOffset[0]), (int) (center[1] + textOffset[1]));
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