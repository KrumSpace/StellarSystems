package playerTest.graphics.panels.leftSidePanel;

import java.awt.Color;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import commonGraphics.UpdatingWindow;
import commonGraphics.panels.sidePanels.commonLeftSidePanel.CommonLeftSidePanel;
import commonGraphics.panels.sidePanels.commonLeftSidePanel.CommonTopSection;

//
public final class LeftSidePanel extends CommonLeftSidePanel {
    private static final @Nullable Color DIAGONAL_COLOR = null;

    //uses default width
    public LeftSidePanel(@NotNull UpdatingWindow window,
                         @Nullable Color background, @Nullable Color borderColor) {
        super(
                window, background,
                borderColor, true, true,
                DIAGONAL_COLOR, false, false);
        addSections();
    }

    //
    @Override
    public @NotNull CommonTopSection getNewTopSection() {
        return new TopSection(getSectionBorderColor(), getSectionDiagonalColor());
    }
}