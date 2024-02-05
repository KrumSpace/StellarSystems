package demo.graphics.panels;

import java.util.Objects;
import java.util.List;
import java.awt.Color;
import java.awt.Graphics;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

//
public interface TextPanelInterface {
    int TEXT_HEIGHT = 15;
    @NotNull Color DEFAULT_TEXT_COLOR = Color.white;

    //
    default int drawInfoLines(@NotNull Graphics g, int @NotNull [] location, @Nullable Color textColor,
                              @NotNull List<@Nullable String> lines, int startingLineNumber) {
        g.setColor(Objects.requireNonNullElse(textColor, DEFAULT_TEXT_COLOR));
        int currentLine = startingLineNumber;
        for (@Nullable String line : lines) {
            if (line != null) {
                g.drawString(line, location[0], location[1] + TEXT_HEIGHT * currentLine);
            }
            currentLine++;
        }
        return currentLine;
    }
}