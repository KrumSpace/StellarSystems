package demo.graphics.panels.leftSidePanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.LayoutManager;
import javax.swing.BoxLayout;

import baryGraphics.Observer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import baryModel.BaryUniverse;

import static commonGraphics.ColorUtils.TRANSPARENT_BLACK;
import commonGraphics.panels.MinimalPanel;
import commonGraphics.panels.sidePanels.SectionContainerInterface;

//
final class TopSection extends MinimalPanel implements SectionContainerInterface {
    private final @NotNull BaryUniverse universe;
    private final @NotNull Observer observer;
    private final @Nullable Color borderColor, diagonalColor;
    private final boolean drawSectionBorders, drawSectionDiagonals;

    //
    TopSection(@NotNull BaryUniverse universe, @NotNull Observer observer,
               @Nullable Color borderColor, boolean drawSectionBorders,
               @Nullable Color diagonalColor, boolean drawSectionDiagonals) {
        super(
                TRANSPARENT_BLACK,
                borderColor, false,
                diagonalColor, false);
        this.universe = universe;
        this.observer = observer;
        this.borderColor = borderColor;
        this.drawSectionBorders = drawSectionBorders;
        this.diagonalColor = diagonalColor;
        this.drawSectionDiagonals = drawSectionDiagonals;
        addSections();
    }

    //
    @Override
    public void addSections() {
        LayoutManager layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        setLayout(layout);
        add(new FirstSectionPanel(
                universe,
                borderColor, drawSectionBorders,
                diagonalColor, drawSectionDiagonals));
        add(new SecondSectionPanel(
                observer,
                borderColor, drawSectionBorders,
                diagonalColor, drawSectionDiagonals));
        add(new ThirdSectionPanel(
                borderColor, drawSectionBorders,
                diagonalColor, drawSectionDiagonals));
        // Add more sections here, if needed.
        revalidate();
    }

    //
    @Override
    public void mainPaint(@NotNull Graphics g) {}
}