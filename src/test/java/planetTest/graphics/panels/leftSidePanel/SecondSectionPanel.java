package planetTest.graphics.panels.leftSidePanel;

import java.awt.Color;
import java.awt.Graphics;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

//
public final class SecondSectionPanel extends AbstractSectionPanel {
    private static final int PANEL_HEIGHT = 100;

    //
    public SecondSectionPanel(@Nullable Color borderColor, @Nullable Color diagonalColor) {
        super(
                PANEL_HEIGHT,
                borderColor, true,
                diagonalColor, false);
    }

    //
    @Override
    public void mainPaint(@NotNull Graphics g) {
        //TODO: paint stuff here.
    }
}