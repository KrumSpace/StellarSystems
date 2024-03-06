package planetTest.graphics.panels.leftSidePanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.LayoutManager;
import javax.swing.BoxLayout;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static commonGraphics.ColorUtils.TRANSPARENT_BLACK;
import commonGraphics.panels.MinimalPanel;
import commonGraphics.panels.sidePanels.SectionContainerInterface;
import planetTest.planetModel.PlanetContainer;

//
final class TopSection extends MinimalPanel implements SectionContainerInterface {
    private final @NotNull PlanetContainer planetContainer;
    private final @Nullable Color borderColor, diagonalColor;

    //
    TopSection(@NotNull PlanetContainer planetContainer,
               @Nullable Color borderColor, @Nullable Color diagonalColor) {
        super(
                TRANSPARENT_BLACK,
                borderColor, false,
                diagonalColor, false);
        this.borderColor = borderColor;
        this.diagonalColor = diagonalColor;
        this.planetContainer = planetContainer;
        addSections();
    }

    //
    @Override
    public void addSections() {
        LayoutManager layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        setLayout(layout);
        add(new FirstSectionPanel(borderColor, diagonalColor));
        add(new SecondSectionPanel(planetContainer, borderColor, diagonalColor));
        add(new ThirdSectionPanel(borderColor, diagonalColor));
        // Add more sections here, if needed.
        revalidate();
    }

    //
    @Override
    public void mainPaint(@NotNull Graphics g) {}
}