package commonGraphics;

import java.util.Objects;
import javax.swing.JFrame;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

//
public abstract class AbstractWindow extends JFrame {
    //Creates a new window.
    public AbstractWindow(@Nullable WindowSettings windowSettings) {
        super();
        setWindowConfig(Objects.requireNonNullElse(windowSettings, new WindowSettings()));
        setVisible(true);
    }

    @SuppressWarnings("MagicConstant")
    private void setWindowConfig(@NotNull WindowSettings windowSettings) {
        setSize(windowSettings.getWindowSize());
        setLocation(windowSettings.getWindowLocation());
        setDefaultCloseOperation(windowSettings.getCloseOperation());
        setTitle(windowSettings.getWindowTitle());
    }

    //doesn't get called automatically by AbstractWindow
    public abstract void addPanels();
}