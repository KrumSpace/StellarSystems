package commonGraphics.panels;

import java.util.Objects;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static commonGraphics.ColorUtils.TRANSPARENT_BLACK;

//a minimal panel with most common features
public abstract class MinimalPanel extends JPanel {
    private static final @NotNull Color DEFAULT_BORDERS_AND_DIAGONALS_COLOR = Color.red;
    private final @NotNull Color borderColor, diagonalColor;
    private boolean drawBorders, drawDiagonals;

    //
    public MinimalPanel(@Nullable Color background,
                        @Nullable Color borderColor, boolean drawBorders,
                        @Nullable Color diagonalColor, boolean drawDiagonals) {
        super();
        setBackground(Objects.requireNonNullElse(background, TRANSPARENT_BLACK));
        this.borderColor = Objects.requireNonNullElse(borderColor, DEFAULT_BORDERS_AND_DIAGONALS_COLOR);
        this.drawBorders = drawBorders;
        this.diagonalColor = Objects.requireNonNullElse(diagonalColor, DEFAULT_BORDERS_AND_DIAGONALS_COLOR);
        this.drawDiagonals = drawDiagonals;
        setVisible(true);
    }

    //
    @SuppressWarnings("unused")
    public final void setDrawBorders(boolean drawBorders) {
        this.drawBorders = drawBorders;
    }

    //
    @SuppressWarnings("unused")
    public final void setDrawDiagonals(boolean drawDiagonals) {
        this.drawDiagonals = drawDiagonals;
    }

    //
    @Override
    protected void paintComponent(@NotNull Graphics g) {
        super.paintComponent(g);
        mainPaint(g);
        finalPaint(g);
    }

    //first and main stage of the painting
    public abstract void mainPaint(@NotNull Graphics g);

    //happens after mainPaint
    public void finalPaint(@NotNull Graphics g) {
        @NotNull Dimension panelSize = getSize();
        int
                width = (int) panelSize.getWidth(),
                height = (int) panelSize.getHeight();
        if (drawBorders) drawPanelBorders(g, width, height);
        if (drawDiagonals) drawPanelDiagonals(g, width, height);
    }

    private void drawPanelBorders(@NotNull Graphics g, int width, int height) {
        g.setColor(borderColor);
        g.drawRect(0, 0, width - 1, height - 1);
    }

    private void drawPanelDiagonals(@NotNull Graphics g, int width, int height) {
        g.setColor(diagonalColor);
        g.drawLine(0, 0, width, height);
        g.drawLine(0, height, width, 0);
    }
}