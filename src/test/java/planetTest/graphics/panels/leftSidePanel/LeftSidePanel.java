package planetTest.graphics.panels.leftSidePanel;

import java.awt.Color;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import commonGraphics.panels.sidePanels.commonLeftSidePanel.CommonTopSection;
import commonGraphics.panels.sidePanels.commonLeftSidePanel.CommonLeftSidePanel;
import commonGraphics.UpdatingWindow;
import planetTest.planetModel.PlanetContainer;

//
public final class LeftSidePanel extends CommonLeftSidePanel {
    private static final @Nullable Color DIAGONAL_COLOR = null;
    private final @NotNull PlanetContainer planetContainer;

    //uses default width
    public LeftSidePanel(@NotNull UpdatingWindow window, @NotNull PlanetContainer planetContainer,
                         @Nullable Color background, @Nullable Color borderColor) {
        super(
                window, background,
                borderColor, true, true,
                DIAGONAL_COLOR, false, false);
        this.planetContainer = planetContainer;
        addSections();
    }

    //
    @Override
    public @NotNull CommonTopSection getNewTopSection() {
        return new TopSection(planetContainer, getSectionBorderColor(), getSectionDiagonalColor());
    }
}