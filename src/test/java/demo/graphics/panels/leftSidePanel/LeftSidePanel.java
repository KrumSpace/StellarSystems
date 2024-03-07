package demo.graphics.panels.leftSidePanel;

import java.awt.Color;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import baryModel.BaryUniverse;

import commonGraphics.panels.sidePanels.commonLeftSidePanel.CommonTopSection;
import commonGraphics.panels.sidePanels.commonLeftSidePanel.CommonLeftSidePanel;
import commonGraphics.UpdatingWindow;
import baryGraphics.Observer;

//
public final class LeftSidePanel extends CommonLeftSidePanel {
    private static final boolean
            DRAW_SECTION_BORDERS = false,
            DRAW_SECTION_DIAGONALS = false;
    private final @NotNull BaryUniverse universe;
    private final @NotNull Observer observer;

    //
    public LeftSidePanel(@NotNull BaryUniverse universe, @NotNull Observer observer,
                         @NotNull UpdatingWindow window,
                         @Nullable Color background, @Nullable Color borderColor) {
        super(
                window, background,
                borderColor, true, DRAW_SECTION_BORDERS,
                null, false, DRAW_SECTION_DIAGONALS);
        this.universe = universe;
        this.observer = observer;
        addSections();
    }

    //
    @Override
    public @NotNull CommonTopSection getNewTopSection() {
        return new TopSection(
                universe, observer,
                getSectionBorderColor(), DRAW_SECTION_BORDERS,
                getSectionDiagonalColor(), DRAW_SECTION_DIAGONALS);
    }
}