package playerTest.graphics.panels.rightSidePanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.LayoutManager;
import javax.swing.BoxLayout;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static commonGraphics.ColorUtils.TRANSPARENT_BLACK;
import commonGraphics.panels.MinimalPanel;
import commonGraphics.panels.sidePanels.SectionContainerInterface;

//
final class BottomSection extends MinimalPanel implements SectionContainerInterface {
    private static final boolean
            DRAW_PANEL_BORDERS = false,
            DRAW_PANEL_DIAGONALS = false;
    private final @Nullable Color sectionBorderColor, sectionDiagonalColor;
    private final boolean drawSectionBorders, drawSectionDiagonals;

    //
    BottomSection(@Nullable Color borderColor, boolean drawSectionBorders,
                  @Nullable Color diagonalColor, boolean drawSectionDiagonals) {
        super(TRANSPARENT_BLACK, borderColor, DRAW_PANEL_BORDERS, diagonalColor, DRAW_PANEL_DIAGONALS);
        sectionBorderColor = borderColor;
        this.drawSectionBorders = drawSectionBorders;
        sectionDiagonalColor = diagonalColor;
        this.drawSectionDiagonals = drawSectionDiagonals;
        addSections();
    }

    //
    @Override
    public void addSections() {
        LayoutManager layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        setLayout(layout);
        add(new Section1(
                sectionBorderColor, drawSectionBorders,
                sectionDiagonalColor, drawSectionDiagonals));
        add(new Section2(
                sectionBorderColor, drawSectionBorders,
                sectionDiagonalColor, drawSectionDiagonals));
        revalidate();
    }

    //
    @Override
    public void mainPaint(@NotNull Graphics g) {}
}