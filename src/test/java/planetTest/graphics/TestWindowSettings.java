package planetTest.graphics;

import java.awt.Dimension;
import java.awt.Point;

import org.jetbrains.annotations.NotNull;

import commonGraphics.WindowSettings;

//
public class TestWindowSettings extends WindowSettings {
    private static final @NotNull Dimension WINDOW_SIZE = new Dimension(700, 500);
    private static final @NotNull Point WINDOW_LOCATION = new Point(50, 50);
    private static final @NotNull String WINDOW_TITLE = "Planet test";

    //
    TestWindowSettings() {
        super(WINDOW_SIZE, WINDOW_LOCATION, WINDOW_TITLE);
    }
}