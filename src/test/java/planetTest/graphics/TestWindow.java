package planetTest.graphics;

import java.awt.Point;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.LayoutManager;
import javax.swing.BoxLayout;

import org.jetbrains.annotations.NotNull;

import commonGraphics.UpdatingWindow;
import planetTest.graphics.panels.leftSidePanel.LeftSidePanel;
import planetTest.graphics.panels.CenterPanel;
import planetTest.planetModel.PlanetContainer;

//A graphical window for planetary testing purposes.
public final class TestWindow extends UpdatingWindow {
    private static final @NotNull Dimension WINDOW_SIZE = new Dimension(700, 500);
    private static final @NotNull Point WINDOW_LOCATION = new Point(50, 50);
    private static final @NotNull String WINDOW_TITLE = "Planet test";
    private static final int
            MAIN_PANEL_BACKGROUND_BRIGHTNESS = 50,
            MAIN_PANEL_BORDER_BRIGHTNESS = 70;
    private static final @NotNull Color
            MAIN_PANEL_BACKGROUND_COLOR = new Color(
                    MAIN_PANEL_BACKGROUND_BRIGHTNESS,
                    MAIN_PANEL_BACKGROUND_BRIGHTNESS,
                    MAIN_PANEL_BACKGROUND_BRIGHTNESS),
            MAIN_PANEL_BORDER_COLOR = new Color(
                    MAIN_PANEL_BORDER_BRIGHTNESS,
                    MAIN_PANEL_BORDER_BRIGHTNESS,
                    MAIN_PANEL_BORDER_BRIGHTNESS);
    private final @NotNull PlanetContainer planetContainer;

    //
    public TestWindow(@NotNull PlanetContainer planetContainer) {
        super(WINDOW_SIZE, WINDOW_LOCATION, WINDOW_TITLE); //default frame rate
        this.planetContainer = planetContainer;
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
        add(new LeftSidePanel(planetContainer, MAIN_PANEL_BACKGROUND_COLOR, MAIN_PANEL_BORDER_COLOR));
        add(new CenterPanel(planetContainer, MAIN_PANEL_BORDER_COLOR, MAIN_PANEL_BORDER_COLOR));
        // Add more panels here, if needed.
    }
}