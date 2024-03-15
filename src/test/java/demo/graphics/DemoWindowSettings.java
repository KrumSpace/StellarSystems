package demo.graphics;

import java.awt.Dimension;
import java.awt.Point;

import org.jetbrains.annotations.NotNull;

import commonGraphics.WindowSettings;

//
public class DemoWindowSettings extends WindowSettings {
    private static final @NotNull Dimension WINDOW_SIZE = new Dimension(1200, 850);
    private static final @NotNull Point WINDOW_LOCATION = new Point(50, 50);
    private static final @NotNull String WINDOW_TITLE = "Bary window!";

    //
    DemoWindowSettings() {
        super(WINDOW_SIZE, WINDOW_LOCATION, WINDOW_TITLE);
    }
}