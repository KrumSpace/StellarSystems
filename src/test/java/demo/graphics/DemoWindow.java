package demo.graphics;

import java.awt.Color;
import java.awt.LayoutManager;
import javax.swing.BoxLayout;

import org.jetbrains.annotations.NotNull;

import baryModel.BaryUniverse;

import static commonGraphics.ColorUtils.getGray;
import commonGraphics.UpdatingWindow;
import baryGraphics.Observer;
import demo.graphics.panels.leftSidePanel.LeftSidePanel;
import demo.graphics.panels.CentralPanel;
import demo.graphics.panels.rightSidePanel.RightSidePanel;

//A graphical window for demo.
public final class DemoWindow extends UpdatingWindow {
    private static final long FRAME_RATE = 60;
    private static final @NotNull Color
            MAIN_PANEL_BACKGROUND_COLOR = getGray(50, 255),
            MAIN_PANEL_BORDER_COLOR = getGray(40, 255),
            CENTRAL_PANEL_DIAGONAL_COLOR = MAIN_PANEL_BORDER_COLOR;
    private final @NotNull BaryUniverse universe;
    private final @NotNull Observer observer;

    //Creates a new window.
    public DemoWindow(@NotNull BaryUniverse universe) {
        super(new DemoWindowSettings(), FRAME_RATE);
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
        add(new LeftSidePanel(
                universe, observer, this,
                MAIN_PANEL_BACKGROUND_COLOR, MAIN_PANEL_BORDER_COLOR));
        add(new CentralPanel(
                universe, observer,
                MAIN_PANEL_BORDER_COLOR, CENTRAL_PANEL_DIAGONAL_COLOR));
        add(new RightSidePanel(
                universe, observer,
                MAIN_PANEL_BACKGROUND_COLOR, MAIN_PANEL_BORDER_COLOR));
        // Add more panels here, if needed.
    }
}