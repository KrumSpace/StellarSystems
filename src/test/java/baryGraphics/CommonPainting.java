package baryGraphics;

import java.awt.Color;
import java.awt.Graphics;

import org.jetbrains.annotations.NotNull;

//common methods for painting
public class CommonPainting {
    //
    public static void drawCross(@NotNull Graphics g,
                                  double @NotNull [] drawCenter, int size,
                                  @NotNull Color color) {
        g.setColor(color);
        g.drawLine( //horizontal line
                (int) (drawCenter[0] - size / 2), (int) (drawCenter[1]),
                (int) (drawCenter[0] + size / 2), (int) (drawCenter[1]));
        g.drawLine( //vertical line
                (int) (drawCenter[0]), (int) (drawCenter[1] - size / 2),
                (int) (drawCenter[0]), (int) (drawCenter[1] + size / 2));
    }

    //
    public static void drawCircleAtCenter(@NotNull Graphics g,
                                          double @NotNull [] drawCenter, double drawSize,
                                          @NotNull Color color, boolean fill) {
        int drawSizeInt = (int) drawSize;
        g.setColor(color);
        if (fill) {
            g.fillOval(
                    (int) (drawCenter[0] - drawSize / 2),
                    (int) (drawCenter[1] - drawSize / 2),
                    drawSizeInt, drawSizeInt);
        } else {
            g.drawOval(
                    (int) (drawCenter[0] - drawSize / 2),
                    (int) (drawCenter[1] - drawSize / 2),
                    drawSizeInt, drawSizeInt);
        }
    }
}