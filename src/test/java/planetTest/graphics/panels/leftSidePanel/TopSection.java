package planetTest.graphics.panels.leftSidePanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.LayoutManager;
import javax.swing.BoxLayout;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import commonGraphics.panels.MinimalPanel;

//
public final class TopSection extends MinimalPanel implements SectionContainer {
    private static final @NotNull Color TRANSPARENT_BLACK = new Color(0, 0, 0, 0);
    private final @Nullable Color borderColor, diagonalColor;

    //
    public TopSection(@Nullable Color borderColor, @Nullable Color diagonalColor) {
        super(
                TRANSPARENT_BLACK,
                borderColor, false,
                diagonalColor, false);
        this.borderColor = borderColor;
        this.diagonalColor = diagonalColor;
        addSections();
    }

    //
    @Override
    public void addSections() {
        LayoutManager layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        setLayout(layout);
        add(new FirstSectionPanel(borderColor, diagonalColor));
        add(new SecondSectionPanel(borderColor, diagonalColor));
        add(new ThirdSectionPanel(borderColor, diagonalColor));
        // Add more sections here, if needed.
        revalidate();
    }

    //
    @Override
    public void mainPaint(@NotNull Graphics g) {}
}