package testGraphics;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

import org.jetbrains.annotations.NotNull;

import baryModel.BaryUniverse;
import testGraphics.universePainter.UniversePainter;

//
class TestPanel extends JPanel {
    private static final @NotNull Color BACKGROUND_COLOR = Color.black;
    private final @NotNull UniversePainter universePainter;

    //
    protected TestPanel(@NotNull BaryUniverse universe) {
        super();
        setBackground(BACKGROUND_COLOR);
        universePainter = new UniversePainter(universe);
    }

    //
    @Override
    protected void paintComponent(@NotNull Graphics g) {
        super.paintComponent(g);
        universePainter.paint(g);
        //TODO: paint something else here
    }
}