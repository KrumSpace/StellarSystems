package commonGraphics.panels;

import java.util.Objects;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

//a minimal panel with most common features
public class MinimalPanel extends JPanel {
    private static final @NotNull Color
            TRANSPARENT_BLACK = new Color(0, 0, 0, 0),
            BORDERS_AND_DIAGONALS = Color.darkGray;

    //
    public MinimalPanel(@Nullable Color background) {
        super();
        setBackground(Objects.requireNonNullElse(background, TRANSPARENT_BLACK));
        setVisible(true);
    }

    /**
     * Call this to draw a rectangle around this panel.
     * Intended to visibly test panel sizes.
     *
     * @param g Graphics to use.
     */
    @SuppressWarnings("unused")
    public final void drawPanelBorders(@NotNull Graphics g) {
        @NotNull Dimension panelSize = getSize();
        int
                width = (int) panelSize.getWidth(),
                height = (int) panelSize.getHeight();
        g.setColor(BORDERS_AND_DIAGONALS);
        g.drawRect(0, 0, width, height);
    }

    /**
     * Call this to draw the diagonals of this panel.
     * Intended to visibly test panel sizes and centering.
     *
     * @param g Graphics to use.
     */
    @SuppressWarnings("unused")
    public final void drawPanelDiagonals(@NotNull Graphics g) {
        @NotNull Dimension panelSize = getSize();
        int
                width = (int) panelSize.getWidth(),
                height = (int) panelSize.getHeight();
        g.setColor(BORDERS_AND_DIAGONALS);
        g.drawLine(0, 0, width, height);
        g.drawLine(0, height, width, 0);
    }
}