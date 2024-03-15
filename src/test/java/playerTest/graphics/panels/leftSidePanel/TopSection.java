package playerTest.graphics.panels.leftSidePanel;

import javax.swing.BoxLayout;
import java.awt.Color;
import java.awt.LayoutManager;

import org.jetbrains.annotations.Nullable;

import commonGraphics.panels.sidePanels.commonLeftSidePanel.CommonTopSection;

//Necessary for bottom-fixed bottom section;
final class TopSection extends CommonTopSection {
    //
    TopSection(@Nullable Color borderColor, @Nullable Color diagonalColor) {
        super(borderColor, diagonalColor);
        addSections();
    }

    //
    @Override
    public void addSections() {
        LayoutManager layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        setLayout(layout);
        @Nullable Color
                borderColor = getBorderColor(),
                diagonalColor = getDiagonalColor();
        add(new FirstSection(borderColor, diagonalColor));
        add(new SecondSection(borderColor, diagonalColor));
        add(new ThirdSection(borderColor, diagonalColor));
        // Add more sections here, if needed.
        revalidate();
    }
}