package planetTest.graphics.panels.leftSidePanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.LayoutManager;
import java.awt.BorderLayout;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import commonGraphics.StringUtils;
import commonGraphics.panels.FixedVerticalPanel;
import planetTest.planetModel.Planet;
import planetTest.planetModel.PlanetContainer;

//
public final class LeftSidePanel extends FixedVerticalPanel implements SectionContainer {
    private static final int PANEL_WIDTH = 200;
    private static final int @NotNull [] TEXT_LOCATION = new int [] {10, 10};
    private static final @NotNull Color TEXT_COLOR = Color.white;
    private static final @Nullable Color DIAGONAL_COLOR = null;
    private final @NotNull PlanetContainer planetContainer;
    private final @Nullable Color sectionBorderColor, sectionDiagonalColor;

    //
    public LeftSidePanel(@NotNull PlanetContainer planetContainer,
                         @Nullable Color background, @Nullable Color borderColor) {
        super(
                PANEL_WIDTH, background,
                borderColor, true,
                DIAGONAL_COLOR, false);
        this.planetContainer = planetContainer;
        sectionBorderColor = borderColor;
        sectionDiagonalColor = DIAGONAL_COLOR;
        addSections();
    }

    //
    @Override
    public void addSections(){
        LayoutManager layout = new BorderLayout();
        setLayout(layout);
        add(new BottomSectionPanel(sectionBorderColor, sectionDiagonalColor), BorderLayout.SOUTH);
        add(new TopSection(sectionBorderColor, sectionDiagonalColor));
        revalidate();
    }

    //
    @Override
    public void mainPaint(@NotNull Graphics g) {
        g.setColor(TEXT_COLOR);
        drawInfoLine(g, "Planet info", 1);
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
        StringUtils.drawNumberedString(g, line, TEXT_LOCATION, lineNumber);
    }
}