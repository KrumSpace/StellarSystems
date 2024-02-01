package testGraphics.universePainter;

import java.awt.Color;
import java.awt.Graphics;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import baryModel.BaryObject;
import baryModel.BaryUniverse;

//
public final class UniversePainter extends ObjectContainerPainter<BaryObject> {
    private static final double DEFAULT_SCALE = 20;
    private static final @NotNull Color UNIVERSE_CENTER_MARKER_COLOR = Color.white;
    private final @NotNull BaryUniverse universe;
    private final @NotNull GenericObjectPainter genericObjectPainter;
    private final @NotNull BarySystemPainter systemPainter;
    private final @NotNull SimpleObjectPainter simpleObjectPainter;

    //
    public UniversePainter(@NotNull BaryUniverse universe, int @Nullable [] drawOffset, double defaultScale) {
        super(drawOffset, defaultScale);
        this.universe = universe;
        double scale = getScale();
        genericObjectPainter = new GenericObjectPainter(drawOffset, scale, this);
        systemPainter = new BarySystemPainter(drawOffset, scale, this);
        simpleObjectPainter = new SimpleObjectPainter(drawOffset, scale);
    }

    //
    public UniversePainter(@NotNull BaryUniverse universe) {
        this(universe, null, DEFAULT_SCALE);
    }

    //
    public void paint(@NotNull Graphics g) {
        double @NotNull []
                universeLocation = new double [2],
                centerDrawCenter = getDrawableFromScaled(scaleLocation(universeLocation));
        paint(g, null, universeLocation);
        CommonPainting.paintCenterMarker(g, centerDrawCenter, UNIVERSE_CENTER_MARKER_COLOR);
        paintMembers(g, getGenericObjectPainter(), universe, universeLocation);
    }

    //
    @Override
    public void paint(@NotNull Graphics g, @Nullable BaryObject object, double @NotNull [] absoluteLocation) {
        double @NotNull [] centerDrawCenter = getDrawableFromScaled(scaleLocation(absoluteLocation));
        CommonPainting.paintCenterMarker(g, centerDrawCenter, UNIVERSE_CENTER_MARKER_COLOR);
        paintMembers(g, getGenericObjectPainter(), universe, absoluteLocation);
    }

    //
    @NotNull GenericObjectPainter getGenericObjectPainter() {
        return genericObjectPainter;
    }

    //
    @NotNull BarySystemPainter getSystemPainter() {
        return systemPainter;
    }

    //
    @NotNull SimpleObjectPainter getSimpleObjectPainter() {
        return simpleObjectPainter;
    }

    //
    @Override
    public void setScale(double scale) {
        super.setScale(scale);
        getGenericObjectPainter().setScale(scale);
        getSystemPainter().setScale(scale);
        getSimpleObjectPainter().setScale(scale);
    }
}