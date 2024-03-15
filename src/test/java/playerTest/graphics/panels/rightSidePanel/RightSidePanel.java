package playerTest.graphics.panels.rightSidePanel;

import java.awt.Color;
import java.awt.LayoutManager;
import javax.swing.BoxLayout;

import org.jetbrains.annotations.Nullable;

import commonGraphics.panels.sidePanels.AbstractSidePanel;

//
public class RightSidePanel extends AbstractSidePanel {
    private static final int PANEL_WIDTH = 200;
    private static final boolean
            DRAW_SECTION_BORDERS = true,
            DRAW_SECTION_DIAGONALS = true;
    private final @Nullable Color sectionBorderColor, sectionDiagonalColor;

    //
    public RightSidePanel(@Nullable Color background, @Nullable Color borderColor) {
        super(
                PANEL_WIDTH, background,
                borderColor, true,
                null, false);
        this.sectionBorderColor = borderColor;
        this.sectionDiagonalColor = borderColor;
        addSections();
    }

    //
    @Override
    public void addSections() {
        LayoutManager layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        setLayout(layout);
        add(new TopSection(
                getTopSectionHeight(),
                sectionBorderColor, DRAW_SECTION_BORDERS,
                sectionDiagonalColor, DRAW_SECTION_DIAGONALS));
        add(new BottomSection(
                sectionBorderColor, DRAW_SECTION_BORDERS,
                sectionDiagonalColor, DRAW_SECTION_DIAGONALS));
        revalidate();
    }

    @SuppressWarnings("SuspiciousNameCombination")
    private int getTopSectionHeight() {
        return PANEL_WIDTH; //for a square panel
    }
}