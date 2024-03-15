package commonGraphics;

import java.util.Objects;
import java.awt.Dimension;
import java.awt.Point;
import javax.swing.WindowConstants;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

//
public class WindowSettings {
    private static final @NotNull Dimension DEFAULT_WINDOW_SIZE = new Dimension(1200, 850);
    private static final @NotNull Point DEFAULT_WINDOW_LOCATION = new Point(50, 50);
    private static final int DEFAULT_CLOSE_OPERATION = WindowConstants.EXIT_ON_CLOSE;
    private static final @NotNull String DEFAULT_WINDOW_TITLE = "An unnamed window";
    private final @NotNull Dimension windowSize;
    private final @NotNull Point windowLocation;
    private final int closeOperation;
    private final @NotNull String windowTitle;

    //
    public WindowSettings(@Nullable Dimension size, @Nullable Point location, @Nullable String title) {
        windowSize = Objects.requireNonNullElse(size, DEFAULT_WINDOW_SIZE);
        windowLocation = Objects.requireNonNullElse(location, DEFAULT_WINDOW_LOCATION);
        closeOperation = DEFAULT_CLOSE_OPERATION;
        windowTitle = Objects.requireNonNullElse(title, DEFAULT_WINDOW_TITLE);
    }

    //
    public WindowSettings() {
        this(null, null, null);
    }

    //
    public final @NotNull Dimension getWindowSize() {
        return windowSize;
    }

    //
    public final @NotNull Point getWindowLocation() {
        return windowLocation;
    }

    //
    public final int getCloseOperation() {
        return closeOperation;
    }

    //
    public final @NotNull String getWindowTitle() {
        return windowTitle;
    }
}