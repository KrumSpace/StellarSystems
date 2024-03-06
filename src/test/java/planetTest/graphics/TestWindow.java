package planetTest.graphics;

import java.awt.Point;
import java.awt.Dimension;
import java.awt.LayoutManager;
import javax.swing.BoxLayout;

import org.jetbrains.annotations.NotNull;

import commonGraphics.UpdatingWindow;

//A graphical window for planetary testing purposes.
public final class TestWindow extends UpdatingWindow {
    private static final @NotNull Dimension WINDOW_SIZE = new Dimension(700, 500);
    private static final @NotNull Point WINDOW_LOCATION = new Point(50, 50);
    private static final @NotNull String WINDOW_TITLE = "Planet test";

    //
    public TestWindow() {
        super(WINDOW_SIZE, WINDOW_LOCATION, WINDOW_TITLE); //default frame rate
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
        //add more panels here
    }
}