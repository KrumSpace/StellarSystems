package commonGraphics.panels;

import java.awt.Dimension;
import java.awt.Color;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

//
public abstract class FixedSizePanel extends MinimalPanel {
    //
    public FixedSizePanel(int @NotNull [] size,
                          @Nullable Color background,
                          @Nullable Color borderColor, boolean drawBorders,
                          @Nullable Color diagonalColor, boolean drawDiagonals) {
        super(background, borderColor, drawBorders, diagonalColor, drawDiagonals);
        setSizes(size);
    }

    private void setSizes(int @NotNull [] size) {
        @NotNull Dimension dimension = new Dimension(size[0], size[1]);
        setPreferredSize(dimension);
        setMinimumSize(dimension);
        setMaximumSize(dimension);
    }
}