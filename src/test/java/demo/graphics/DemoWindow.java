package demo.graphics;

import java.awt.Point;
import java.awt.Dimension;
import java.awt.LayoutManager;
import javax.swing.BoxLayout;

import org.jetbrains.annotations.NotNull;

import baryModel.BaryUniverse;
import baryGraphics.Observer;
import commonGraphics.UpdatingWindow;
import demo.graphics.panels.LeftSidePanel;
import demo.graphics.panels.CentralPanel;
import demo.graphics.panels.RightSidePanel;

//A graphical window for demo.
public final class DemoWindow extends UpdatingWindow {
    private static final @NotNull Dimension WINDOW_SIZE = new Dimension(1200, 850);
    private static final @NotNull Point WINDOW_LOCATION = new Point(50, 50);
    private static final @NotNull String WINDOW_TITLE = "Bary window!";
    private static final long FRAME_RATE = 60;
    private final @NotNull BaryUniverse universe;
    private final @NotNull Observer observer;

    //Creates a new window.
    public DemoWindow(@NotNull BaryUniverse universe) {
        super(WINDOW_SIZE, WINDOW_LOCATION, WINDOW_TITLE, FRAME_RATE);
        this.universe = universe;
        observer = new Observer();
        addPanels();
        addKeyListener(new DemoKeyListener(observer));
        revalidate();
        startUpdating();
    }

    //
    @Override
    public void addPanels() {
        LayoutManager layout = new BoxLayout(getContentPane(), BoxLayout.X_AXIS);
        getContentPane().setLayout(layout);
        add(new LeftSidePanel(universe, observer));
        add(new CentralPanel(universe, observer));
        add(new RightSidePanel(universe, observer));
        //add more panels here
    }
}