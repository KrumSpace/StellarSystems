package test2.graphics;

import java.awt.Dimension;
import java.awt.Point;

import org.jetbrains.annotations.NotNull;

import commonGraphics.WindowSettings;

//
final class TestWindowSettings extends WindowSettings {
    private static final @NotNull Dimension WINDOW_SIZE = new Dimension(400, 300);
    private static final @NotNull Point WINDOW_LOCATION = new Point(50, 50);
    private static final @NotNull String WINDOW_TITLE = "Test 2";

    //
    TestWindowSettings() {
        super(WINDOW_SIZE, WINDOW_LOCATION, WINDOW_TITLE);
    }
}