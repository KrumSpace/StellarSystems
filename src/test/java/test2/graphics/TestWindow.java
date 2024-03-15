package test2.graphics;

import java.awt.LayoutManager;
import javax.swing.BoxLayout;

import org.jetbrains.annotations.NotNull;

import baryModel.BaryUniverse;

import commonGraphics.UpdatingWindow;

//A graphical window for testing purposes.
public final class TestWindow extends UpdatingWindow {
    private final @NotNull BaryUniverse universe;

    //
    public TestWindow(@NotNull BaryUniverse universe) {
        super(new TestWindowSettings()); //default frame rate
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