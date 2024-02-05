package baryGraphics.panels;

import java.awt.Color;
import java.awt.Graphics;

import org.jetbrains.annotations.NotNull;

import baryModel.BaryUniverse;
import baryGraphics.Observer;
import baryGraphics.painters.PainterContainer;

//
public class UniverseDrawPanel extends ObserverDrawPanel {
    private static final @NotNull Color BACKGROUND_COLOR = Color.black;
    private final @NotNull BaryUniverse universe;
    private final @NotNull UniversePaintUtilities paintUtilities;
    private final @NotNull PainterContainer painters;

    //
    public UniverseDrawPanel(@NotNull BaryUniverse universe, @NotNull Observer observer) {
        super(observer, BACKGROUND_COLOR);
        this.universe = universe;
        paintUtilities = new UniversePaintUtilities(this);
        painters = new PainterContainer(this);
    }

    //
    public final @NotNull BaryUniverse getUniverse() {
        return universe;
    }

    //
    public @NotNull UniversePaintUtilities getPaintUtilities() {
        return paintUtilities;
    }

    //
    public @NotNull PainterContainer getPainters() {
        return painters;
    }

    //
    @Override
    protected void paintComponent(@NotNull Graphics g) {
        super.paintComponent(g);
        drawPanelDiagonals(g);
        painters.paintUniverse(g, universe);
        //paint more stuff here, if needed
    }
}