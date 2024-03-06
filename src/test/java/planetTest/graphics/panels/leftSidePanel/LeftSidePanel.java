package planetTest.graphics.panels.leftSidePanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.LayoutManager;
import java.awt.BorderLayout;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import commonGraphics.panels.FixedVerticalPanel;
import commonGraphics.UpdatingWindow;
import planetTest.planetModel.PlanetContainer;

//
public final class LeftSidePanel extends FixedVerticalPanel implements SectionContainer {
    private static final int PANEL_WIDTH = 200;
    private static final @Nullable Color DIAGONAL_COLOR = null;
    private final @NotNull UpdatingWindow window;
    private final @NotNull PlanetContainer planetContainer;
    private final @Nullable Color sectionBorderColor, sectionDiagonalColor;

    //
    public LeftSidePanel(@NotNull UpdatingWindow window, @NotNull PlanetContainer planetContainer,
                         @Nullable Color background, @Nullable Color borderColor) {
        super(
                PANEL_WIDTH, background,
                borderColor, true,
                DIAGONAL_COLOR, false);
        this.window = window;
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
        add(new BottomSectionPanel(window, sectionBorderColor, sectionDiagonalColor), BorderLayout.SOUTH);
        add(new TopSection(planetContainer, sectionBorderColor, sectionDiagonalColor));
        revalidate();
    }

    //
    @Override
    public void mainPaint(@NotNull Graphics g) {}
}