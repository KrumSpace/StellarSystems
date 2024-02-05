package commonGraphics;

import java.util.Objects;
import java.awt.Point;
import java.awt.Dimension;
import javax.swing.WindowConstants;
import javax.swing.JFrame;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

//
public abstract class AbstractWindow extends JFrame {
    private static final @NotNull Dimension DEFAULT_WINDOW_SIZE = new Dimension(1200, 850);
    private static final @NotNull Point DEFAULT_WINDOW_LOCATION = new Point(150, 50);
    private static final int DEFAULT_CLOSE_OPERATION = WindowConstants.EXIT_ON_CLOSE;
    private static final @NotNull String DEFAULT_WINDOW_TITLE = "An unnamed window";

    //Creates a new window.
    public AbstractWindow(@Nullable Dimension size, @Nullable Point location, @Nullable String title) {
        super();
        setWindowConfig(size, location, title);
        setVisible(true);
    }

    private void setWindowConfig(@Nullable Dimension desiredSize, @Nullable Point desiredLocation, @Nullable String desiredTitle) {
        setSize(Objects.requireNonNullElse(desiredSize, DEFAULT_WINDOW_SIZE));
        setLocation(Objects.requireNonNullElse(desiredLocation, DEFAULT_WINDOW_LOCATION));
        setDefaultCloseOperation(DEFAULT_CLOSE_OPERATION);
        setTitle(Objects.requireNonNullElse(desiredTitle, DEFAULT_WINDOW_TITLE));
    }

    //doesn't get called automatically by AbstractWindow
    public abstract void addPanels();
}