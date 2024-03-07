package demo.graphics.panels.rightSidePanel;

import java.awt.Color;
import java.awt.LayoutManager;
import javax.swing.BoxLayout;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import baryModel.BaryUniverse;

import commonGraphics.panels.sidePanels.AbstractSidePanel;
import baryGraphics.Observer;

//
public final class RightSidePanel extends AbstractSidePanel {
    private static final int PANEL_WIDTH = 200;
    private static final boolean
            DRAW_SECTION_BORDERS = false,
            DRAW_SECTION_DIAGONALS = false;
    @SuppressWarnings({"unused", "FieldCanBeLocal"})
    private final @NotNull BaryUniverse universe;
    @SuppressWarnings({"unused", "FieldCanBeLocal"})
    private final @NotNull Observer observer;
    private final @Nullable Color sectionBorderColor, sectionDiagonalColor;

    //
    public RightSidePanel(@NotNull BaryUniverse universe, @NotNull Observer observer,
                          @Nullable Color background, @Nullable Color borderColor) {
        super(
                PANEL_WIDTH, background,
                borderColor, true,
                null, true);
        this.universe = universe;
        this.observer = observer;
        this.sectionBorderColor = borderColor;
        this.sectionDiagonalColor = borderColor;
        addSections();
    }

    //
    @Override
    public void addSections() {
        LayoutManager layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        setLayout(layout);
        add(new Section1(
                sectionBorderColor, DRAW_SECTION_BORDERS,
                sectionDiagonalColor, DRAW_SECTION_DIAGONALS));
        add(new Section2(
                sectionBorderColor, DRAW_SECTION_BORDERS,
                sectionDiagonalColor, DRAW_SECTION_DIAGONALS));
        revalidate();
    }
}