package commonGraphics.panels;

import java.awt.Color;

import org.jetbrains.annotations.Nullable;

//a horizontal panel with fixed height
public class FixedHorizontalPanel extends FixedSizePanel {
    //
    public FixedHorizontalPanel(@Nullable Color background, int height) {
        super(background, new int [] {Integer.MAX_VALUE, height});
    }
}