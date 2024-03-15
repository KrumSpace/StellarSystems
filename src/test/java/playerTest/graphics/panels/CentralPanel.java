package playerTest.graphics.panels;

import java.awt.Color;
import java.awt.Graphics;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import commonGraphics.panels.graphicalPanels.CenteredDrawPanel;
import commonGraphics.panels.graphicalPanels.ScaledDrawInterface;

//
public final class CentralPanel extends CenteredDrawPanel implements ScaledDrawInterface {
    private static final double DEFAULT_SCALE = 2.0;
    private static final @NotNull Color
            BACKGROUND = Color.black,
            TEXT_COLOR = Color.white;
    @SuppressWarnings("FieldMayBeFinal")
    private double scale;

    //
    public CentralPanel(@Nullable Color borderColor, @Nullable Color diagonalColor) {
        super(
                BACKGROUND,
                borderColor, false,
                diagonalColor, true);
        scale = DEFAULT_SCALE;
    }

    //
    @Override
    public double getScale() {
        return scale;
    }

    //
    @Override
    public void mainPaint(@NotNull Graphics g) {
        g.setColor(TEXT_COLOR);
        g.drawString("A player test", 100, 100);
        // Paint more stuff here, if needed.
    }
}