package playerTest.graphics;

import java.awt.Color;
import java.awt.LayoutManager;
import javax.swing.BoxLayout;

import org.jetbrains.annotations.NotNull;

import static commonGraphics.ColorUtils.getGray;
import commonGraphics.UpdatingWindow;
import playerTest.graphics.panels.leftSidePanel.LeftSidePanel;
import playerTest.graphics.panels.CentralPanel;
import playerTest.graphics.panels.rightSidePanel.RightSidePanel;

//
public final class TestWindow extends UpdatingWindow {
    private static final @NotNull Color
            MAIN_PANEL_BACKGROUND_COLOR = getGray(50, 255),
            MAIN_PANEL_BORDER_COLOR = getGray(40, 255);

    //
    public TestWindow() {
        super(new TestWindowSettings());
        //observer = new Observer();
        addPanels();
        //addKeyListener();
        revalidate();
        startUpdating();
    }

    //
    @Override
    public void addPanels() {
        LayoutManager layout = new BoxLayout(getContentPane(), BoxLayout.X_AXIS);
        getContentPane().setLayout(layout);
        add(new LeftSidePanel(this, MAIN_PANEL_BACKGROUND_COLOR, MAIN_PANEL_BORDER_COLOR));
        add(new CentralPanel(MAIN_PANEL_BORDER_COLOR, MAIN_PANEL_BORDER_COLOR));
        add(new RightSidePanel(MAIN_PANEL_BACKGROUND_COLOR, MAIN_PANEL_BORDER_COLOR));
        // Add more panels here, if needed.
    }
}