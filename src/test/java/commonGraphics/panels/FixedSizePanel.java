package commonGraphics.panels;

import java.awt.Dimension;
import java.awt.Color;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

//
public class FixedSizePanel extends MinimalPanel {
    //
    public FixedSizePanel(@Nullable Color background, int @NotNull [] size) {
        super(background);
        setSizes(size);
    }

    private void setSizes(int @NotNull [] size) {
        @NotNull Dimension dimension = new Dimension(size[0], size[1]);
        setPreferredSize(dimension);
        setMinimumSize(dimension);
        setMaximumSize(dimension);
    }
}