package commonGraphics;

import java.awt.Color;

import org.jetbrains.annotations.NotNull;

//Color utilities.
public final class ColorUtils {
    public static final @NotNull Color TRANSPARENT_BLACK = new Color(0, 0, 0, 0);

    //gets a gray color from brightness and alpha
    public static @NotNull Color getGray(int brightness, int alpha) {
        return new Color(brightness, brightness, brightness, alpha);
    }
}