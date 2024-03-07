package demo.graphics.panels.leftSidePanel;

import java.awt.Color;
import java.awt.LayoutManager;
import javax.swing.BoxLayout;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import baryModel.BaryUniverse;

import commonGraphics.panels.sidePanels.commonLeftSidePanel.CommonTopSection;
import baryGraphics.Observer;

//Necessary for bottom-fixed bottom section.
//Contains main sections.
final class TopSection extends CommonTopSection {
    private final @NotNull BaryUniverse universe;
    private final @NotNull Observer observer;
    private final boolean drawSectionBorders, drawSectionDiagonals;

    //
    TopSection(@NotNull BaryUniverse universe, @NotNull Observer observer,
               @Nullable Color borderColor, boolean drawSectionBorders,
               @Nullable Color diagonalColor, boolean drawSectionDiagonals) {
        super(borderColor, diagonalColor);
        this.universe = universe;
        this.observer = observer;
        this.drawSectionBorders = drawSectionBorders;
        this.drawSectionDiagonals = drawSectionDiagonals;
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
        add(new UniverseInfoSection(
                universe,
                borderColor, drawSectionBorders,
                diagonalColor, drawSectionDiagonals));
        add(new ObserverInfoSection(
                observer,
                borderColor, drawSectionBorders,
                diagonalColor, drawSectionDiagonals));
        add(new BlankSampleSection(
                borderColor, drawSectionBorders,
                diagonalColor, drawSectionDiagonals));
        // Add more sections here, if needed.
        revalidate();
    }
}