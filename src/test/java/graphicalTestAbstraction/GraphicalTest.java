package graphicalTestAbstraction;

import org.jetbrains.annotations.NotNull;

import commonGraphics.AbstractWindow;

//TODO: add graceful close/exit
public class GraphicalTest {
    private final @NotNull AbstractWindow window;

    //
    public GraphicalTest(@NotNull AbstractWindow window) {
        this.window = window;
    }

    //
    public final @NotNull AbstractWindow getWindow() {
        return window;
    }
}