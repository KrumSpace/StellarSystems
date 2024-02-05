package commonGraphics.panels;

import java.awt.Color;

import org.jetbrains.annotations.Nullable;

//a vertical panel with fixed width
public class FixedVerticalPanel extends FixedSizePanel {
    //
    public FixedVerticalPanel(@Nullable Color background, int width) {
        super(background, new int [] {width, Integer.MAX_VALUE});
    }
}