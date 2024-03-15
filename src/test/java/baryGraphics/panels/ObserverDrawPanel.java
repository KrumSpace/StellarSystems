package baryGraphics.panels;

import java.awt.Color;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import commonGraphics.panels.graphicalPanels.CenteredDrawPanel;
import commonGraphics.panels.graphicalPanels.ScaledDrawInterface;
import baryGraphics.Observer;

//
public abstract class ObserverDrawPanel extends CenteredDrawPanel implements ScaledDrawInterface {
    private final @NotNull Observer observer;

    //
    public ObserverDrawPanel(@NotNull Observer observer,
                             @Nullable Color background,
                             @Nullable Color borderColor, boolean drawBorders,
                             @Nullable Color diagonalColor, boolean drawDiagonals) {
        super(background, borderColor, drawBorders, diagonalColor, drawDiagonals);
        this.observer = observer;
    }

    //
    @Override
    public final double getScale() {
        return observer.getScale();
    }

    //
    public final double @NotNull [] getObserverLocation() {
        return observer.getLocation();
    }

    //
    public final double @NotNull [] getLocationRelativeToObserver(double @NotNull [] location) {
        double @NotNull [] observerLocation = getObserverLocation();
        return new double [] {
                location[0] - observerLocation[0],
                location[1] - observerLocation[1]};
    }

    //
    public final double @NotNull [] getDrawableFromAbsolute(double @NotNull [] absoluteLocation) {
        double @NotNull []
                relativeLocation = getLocationRelativeToObserver(absoluteLocation),
                scaledLocation = scaleLocation(relativeLocation);
        return applyDrawCenterOffset(scaledLocation);
    }
}