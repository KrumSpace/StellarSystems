package testGraphics;

import java.awt.Dimension;
import java.awt.Point;
import javax.swing.WindowConstants;
import javax.swing.JFrame;

import org.jetbrains.annotations.NotNull;

import baryModel.BaryUniverse;

//A graphical window for testing purposes.
public class TestWindow extends JFrame {
    private static final @NotNull Dimension WINDOW_SIZE = new Dimension(1100, 700);
    private static final @NotNull Point WINDOW_LOCATION = new Point(150, 100);
    private static final @NotNull String WINDOW_TITLE = "Bary window!";
    private final @NotNull BaryUniverse universe;

    //Creates a new window.
    public TestWindow(@NotNull BaryUniverse universe) {
        super();
        setWindowConfig();
        this.universe = universe;
        addPanels();
        setVisible(true);
    }

    private void setWindowConfig() {
        setSize(WINDOW_SIZE);
        setLocation(WINDOW_LOCATION);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle(WINDOW_TITLE);
    }

    private void addPanels() {
        add(new TestPanel(universe));
        //add more panels here
    }
}