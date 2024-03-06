package commonGraphics;

import java.awt.Graphics;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

//String utilities.
public final class StringUtils {
    public static final int DEFAULT_TEXT_HEIGHT = 15;

    //paints a string, taking into account the line number and default text height.
    //doesn't set the color
    public static void drawNumberedString(@NotNull Graphics g, @Nullable String line,
                                          int @NotNull [] textLocation, int lineNumber) {
        if (line != null) {
            g.drawString(line, textLocation[0], textLocation[1] + DEFAULT_TEXT_HEIGHT * lineNumber);
        }
    }
}