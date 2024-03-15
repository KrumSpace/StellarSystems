package commonGraphics.panels.sidePanels;

import java.awt.Color;
import java.awt.Graphics;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import commonGraphics.panels.FixedVerticalPanel;

//an abstract vertically-fixed side-panel, intended to be used in the main panel
public abstract class AbstractSidePanel extends FixedVerticalPanel implements SectionContainerInterface {
    //
    public AbstractSidePanel(int width, @Nullable Color background,
                             @Nullable Color borderColor, boolean drawBorders,
                             @Nullable Color diagonalColor, boolean drawDiagonals) {
        super(width, background, borderColor, drawBorders, diagonalColor, drawDiagonals);
    }

    //
    @Override
    public void mainPaint(@NotNull Graphics g) {}
}