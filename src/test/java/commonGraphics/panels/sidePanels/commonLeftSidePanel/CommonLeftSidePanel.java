package commonGraphics.panels.sidePanels.commonLeftSidePanel;

import java.awt.Color;
import java.awt.LayoutManager;
import java.awt.BorderLayout;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import commonGraphics.panels.sidePanels.AbstractSidePanel;
import commonGraphics.UpdatingWindow;

//a common left side-panel
public abstract class CommonLeftSidePanel extends AbstractSidePanel {
    private static final int DEFAULT_PANEL_WIDTH = 200;
    private static final @Nullable Color DIAGONAL_COLOR = null;
    private final @NotNull UpdatingWindow window;
    private final @Nullable Color sectionBorderColor, sectionDiagonalColor;
    private final boolean drawSectionBorders, drawSectionDiagonals;

    //with custom width
    public CommonLeftSidePanel(@NotNull UpdatingWindow window, int width, @Nullable Color background,
                               @Nullable Color borderColor, boolean drawBorders, boolean drawSectionBorders,
                               @Nullable Color diagonalColor, boolean drawDiagonals, boolean drawSectionDiagonals) {
        super(width, background, borderColor, drawBorders, diagonalColor, drawDiagonals);
        this.window = window;
        sectionBorderColor = borderColor;
        this.drawSectionBorders = drawSectionBorders;
        sectionDiagonalColor = DIAGONAL_COLOR;
        this.drawSectionDiagonals = drawSectionDiagonals;
    }

    //with default width
    public CommonLeftSidePanel(@NotNull UpdatingWindow window, @Nullable Color background,
                               @Nullable Color borderColor, boolean drawBorders, boolean drawSectionBorders,
                               @Nullable Color diagonalColor, boolean drawDiagonals, boolean drawSectionDiagonals) {
        this(
                window, DEFAULT_PANEL_WIDTH, background,
                borderColor, drawBorders, drawSectionBorders,
                diagonalColor, drawDiagonals, drawSectionDiagonals);
    }

    //doesn't get called by the constructor; has to be called manually
    @Override
    public void addSections(){
        LayoutManager layout = new BorderLayout();
        setLayout(layout);
        add(getNewBottomSection(), BorderLayout.SOUTH);
        add(getNewTopSection());
        revalidate();
    }

    private @NotNull CommonBottomSection getNewBottomSection() {
        return new CommonBottomSection(
                window,
                sectionBorderColor, drawSectionBorders,
                sectionDiagonalColor, drawSectionDiagonals);
    }

    //
    public abstract @Nullable CommonTopSection getNewTopSection();

    //gets desired color
    public @Nullable Color getSectionBorderColor() {
        return sectionBorderColor;
    }

    //gets desired color
    public @Nullable Color getSectionDiagonalColor() {
        return sectionDiagonalColor;
    }
}