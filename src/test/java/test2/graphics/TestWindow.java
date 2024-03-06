package test2.graphics;

import java.awt.Point;
import java.awt.Dimension;
import java.awt.LayoutManager;
import javax.swing.BoxLayout;

import org.jetbrains.annotations.NotNull;

import baryModel.BaryUniverse;

import commonGraphics.UpdatingWindow;

//A graphical window for testing purposes.
public final class TestWindow extends UpdatingWindow {
    private static final @NotNull Dimension WINDOW_SIZE = new Dimension(400, 300);
    private static final @NotNull Point WINDOW_LOCATION = new Point(50, 50);
    private static final @NotNull String WINDOW_TITLE = "Test 2";
    private final @NotNull BaryUniverse universe;

    //
    public TestWindow(@NotNull BaryUniverse universe) {
        super(WINDOW_SIZE, WINDOW_LOCATION, WINDOW_TITLE); //default frame rate
        this.universe = universe;
        //observer = new Observer();
        addPanels();
        //addKeyListener(new DemoKeyListener(observer));
        revalidate();
        startUpdating();
    }

    //
    @Override
    public void addPanels() {
        LayoutManager layout = new BoxLayout(getContentPane(), BoxLayout.X_AXIS);
        getContentPane().setLayout(layout);
        add(new Panel());
        // Add more panels here, if needed.
    }
}