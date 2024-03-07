package demo.graphics.panels.leftSidePanel;

import java.awt.Color;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import baryModel.BaryUniverse;

import commonGraphics.panels.MinimalPanel;
import commonGraphics.panels.sidePanels.commonLeftSidePanel.CommonLeftSidePanel;
import commonGraphics.UpdatingWindow;
import baryGraphics.Observer;

//
public final class LeftSidePanel extends CommonLeftSidePanel {
    private final @NotNull BaryUniverse universe;
    private final @NotNull Observer observer;

    //
    public LeftSidePanel(@NotNull BaryUniverse universe, @NotNull Observer observer,
                         @NotNull UpdatingWindow window,
                         @Nullable Color background, @Nullable Color borderColor) {
        super(
                window, background,
                borderColor, true, false,
                null, false, false);
        this.universe = universe;
        this.observer = observer;
        addSections();
    }

    //
    @Override
    public @NotNull MinimalPanel getNewTopSection() {
        return new TopSection(
                universe, observer,
                getSectionBorderColor(), false,
                getSectionDiagonalColor(), false);
    }
}