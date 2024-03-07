package planetTest.graphics.panels.leftSidePanel;

import java.awt.Color;
import java.awt.LayoutManager;
import javax.swing.BoxLayout;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import commonGraphics.panels.sidePanels.commonLeftSidePanel.CommonTopSection;
import planetTest.planetModel.PlanetContainer;

//Necessary for bottom-fixed bottom section;
final class TopSection extends CommonTopSection {
    private final @NotNull PlanetContainer planetContainer;

    //
    TopSection(@NotNull PlanetContainer planetContainer,
               @Nullable Color borderColor, @Nullable Color diagonalColor) {
        super(borderColor, diagonalColor);
        this.planetContainer = planetContainer;
        addSections();
    }

    //
    @Override
    public void addSections() {
        LayoutManager layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        setLayout(layout);
        @Nullable Color
                borderColor = getBorderColor(),
                diagonalColor = getDiagonalColor();
        add(new FirstSection(borderColor, diagonalColor));
        add(new SecondSection(planetContainer, borderColor, diagonalColor));
        add(new ThirdSection(borderColor, diagonalColor));
        // Add more sections here, if needed.
        revalidate();
    }
}