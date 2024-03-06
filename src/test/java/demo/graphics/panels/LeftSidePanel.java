package demo.graphics.panels;

import java.util.List;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Graphics;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static consoleUtils.stringTools.NumberFormatter.doubleToString;

import baryModel.BaryUniverse;
import commonGraphics.panels.FixedVerticalPanel;
import baryGraphics.Observer;

//
public final class LeftSidePanel extends FixedVerticalPanel implements TextPanelInterface {
    private static final @NotNull Color TEXT_COLOR = Color.white;
    private static final int PANEL_WIDTH = 200;
    private static final int @NotNull [] TEXT_LOCATION = new int [] {10, 10};
    private final @NotNull BaryUniverse universe;
    private final @NotNull Observer observer;

    //
    public LeftSidePanel(@NotNull BaryUniverse universe, @NotNull Observer observer,
                         @Nullable Color background, @Nullable Color borderColor) {
        super(
                PANEL_WIDTH, background,
                borderColor, true,
                null, false);
        this.universe = universe;
        this.observer = observer;
    }

    //
    @SuppressWarnings("UnusedAssignment")
    @Override
    public void mainPaint(@NotNull Graphics g) {
        int
                initialLineNumber = 1,
                lastLine = initialLineNumber;
        lastLine = drawUniverseInfo(g, TEXT_LOCATION, lastLine);
        lastLine = drawObserverInfo(g, TEXT_LOCATION, lastLine);
        lastLine = drawWindowInfo(g, TEXT_LOCATION, lastLine);
        // paint more stuff here, if needed
    }

    @SuppressWarnings("SameParameterValue")
    private int drawUniverseInfo(@NotNull Graphics g, int @NotNull [] location, int startingLineNumber) {
        @NotNull List<@Nullable String> lines = new ArrayList<>();
        lines.add("Universe info");
        lines.add("Universe info coming soon...");
        // add more universe info lines here
        lines.add(null);
        lines.add(null);
        return drawInfoLines(g, location, TEXT_COLOR, lines, startingLineNumber);
    }

    @SuppressWarnings("SameParameterValue")
    private int drawObserverInfo(@NotNull Graphics g, int @NotNull [] location, int startingLineNumber) {
        @NotNull List<@Nullable String> lines = new ArrayList<>();
        lines.add("Observer info");
        double @NotNull [] observerLocation = observer.getLocation();
        @NotNull String
                roundedX = doubleToString(observerLocation[0], 3),
                roundedY = doubleToString(observerLocation[1], 3);
        lines.add("Location: " + roundedX + ", " + roundedY);
        @NotNull String roundedScale = doubleToString(observer.getScale(), 3);
        lines.add("Scale: " + roundedScale);
        // add more observer info lines here
        lines.add(null);
        lines.add(null);
        return drawInfoLines(g, location, TEXT_COLOR, lines, startingLineNumber);
    }

    @SuppressWarnings("SameParameterValue")
    private int drawWindowInfo(@NotNull Graphics g, int @NotNull [] location, int startingLineNumber) {
        @NotNull List<@Nullable String> lines = new ArrayList<>();
        lines.add("Window info");
        lines.add("Window info coming soon...");
        lines.add(null);
        lines.add(null);
        return drawInfoLines(g, location, TEXT_COLOR, lines, startingLineNumber);
    }
}