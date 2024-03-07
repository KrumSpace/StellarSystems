package commonGraphics.panels.sidePanels.commonLeftSidePanel;

import java.awt.Color;
import java.awt.Graphics;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static commonGraphics.ColorUtils.TRANSPARENT_BLACK;
import commonGraphics.panels.MinimalPanel;
import commonGraphics.panels.sidePanels.SectionContainerInterface;

//Necessary for bottom-fixed bottom section;
public abstract class CommonTopSection extends MinimalPanel implements SectionContainerInterface {
    private final @Nullable Color borderColor, diagonalColor;

    //
    public CommonTopSection(@Nullable Color borderColor, @Nullable Color diagonalColor) {
        super(
                TRANSPARENT_BLACK,
                borderColor, false,
                diagonalColor, false);
        this.borderColor = borderColor;
        this.diagonalColor = diagonalColor;
    }

    //
    @Override
    public void mainPaint(@NotNull Graphics g) {}

    //gets desired color
    public @Nullable Color getBorderColor() {
        return borderColor;
    }

    //gets desired color
    public @Nullable Color getDiagonalColor() {
        return diagonalColor;
    }
}