package testGraphics.universePainter;

import java.util.Collections;
import java.awt.Color;
import java.awt.Graphics;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import baryModel.*;
import baryModel.simpleObjects.*;

import testGraphics.generalPainters.ScaledOffsetPainter;
import testGraphics.generalPainters.AdvancedScaleScaledOffsetPainter;

//
public class UniversePainter extends AdvancedScaleScaledOffsetPainter {
    private static final double DEFAULT_SCALE = 20;
    private static final @NotNull Color UNIVERSE_CENTER_MARKER_COLOR = Color.white;
    private final @NotNull BaryUniverse universe;
    private final @NotNull GenericObjectPainter genericObjectPainter;
    private final @NotNull ObjectContainerPainter containerPainter;
    private final @NotNull SimpleObjectPainter simpleObjectPainter;

    //
    public UniversePainter(@NotNull BaryUniverse universe, int @Nullable [] drawOffset, double defaultScale) {
        super(drawOffset, defaultScale);
        this.universe = universe;
        double scale = getScale();
        genericObjectPainter = new GenericObjectPainter(drawOffset, scale, this);
        containerPainter = new ObjectContainerPainter(drawOffset, scale, this);
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
        CommonPainting.paintCenterMarker(g, centerDrawCenter, UNIVERSE_CENTER_MARKER_COLOR);
        containerPainter.paintMembers(g, universe, universeLocation);
    }

    //
    final @NotNull GenericObjectPainter getGenericObjectPainter() {
        return genericObjectPainter;
    }

    //
    final @NotNull ObjectContainerPainter getContainerPainter() {
        return containerPainter;
    }

    //
    final @NotNull SimpleObjectPainter getSimpleObjectPainter() {
        return simpleObjectPainter;
    }

    //
    @Override
    public void setScale(double scale) {
        super.setScale(scale);
        getGenericObjectPainter().setScale(scale);
        getContainerPainter().setScale(scale);
        getSimpleObjectPainter().setScale(scale);
    }
}

//
class GenericObjectPainter extends ScaledOffsetPainter {
    private static final boolean PAINT_SYSTEM_CONNECTIONS = true;
    private final @NotNull UniversePainter universePainter;

    //
    public GenericObjectPainter(int @Nullable [] drawOffset, double scale,
                                @NotNull UniversePainter universePainter) {
        super(drawOffset, scale);
        this.universePainter = universePainter;
    }

    //
    public void paintBaryObject(@NotNull Graphics g,
                                @NotNull BaryObject object,
                                double @NotNull [] parentLocation) throws UnrecognizedBaryObjectTypeException {
        double @NotNull [] absoluteLocation = CommonPainting.getAbsoluteLocationFromRelative(object, parentLocation);
        paintCommonBefore(g, object, absoluteLocation, parentLocation);
        if (object instanceof BarySimpleObject) {
            universePainter.getSimpleObjectPainter().paintSimpleBaryObject(g, (BarySimpleObject) object, absoluteLocation);
        } else if (object instanceof BarySystem) {
            universePainter.getContainerPainter().paintBarySystem(g, (BarySystem) object, absoluteLocation);
        } else {
            throw new UnrecognizedBaryObjectTypeException();
        }
        paintCommonAfter(g, object, absoluteLocation);
    }

    private void paintCommonBefore(@NotNull Graphics g,
                                   @NotNull BaryObject object,
                                   double @NotNull [] absoluteLocation,
                                   double @NotNull [] parentAbsoluteLocation) {
        if (PAINT_SYSTEM_CONNECTIONS && !(object.getParent() instanceof BaryUniverse)) {
            @NotNull Color color = object.getColor();
            double @NotNull []
                    scaledLocation = scaleLocation(absoluteLocation),
                    scaledParentLocation = scaleLocation(parentAbsoluteLocation);
            CommonPainting.paintConnection(g, universePainter, color, scaledLocation, scaledParentLocation);
            CommonPainting.paintOrbit(g, universePainter, object, scaledParentLocation);
        }
    }

    private void paintCommonAfter(@NotNull Graphics g,
                                  @NotNull BaryObject object,
                                  double @NotNull [] absoluteLocation) {
        double @NotNull []
                scaledLocation = scaleLocation(absoluteLocation),
                drawableCenter = getDrawableFromScaled(scaledLocation);
        CommonPainting.paintInfluenceRadius(g, universePainter, object, drawableCenter);
        CommonPainting.paintCenterMarker(g, drawableCenter, object.getColor());
        CommonPainting.paintObjectInfo(g, object, drawableCenter);
    }
}

//
class ObjectContainerPainter extends ScaledOffsetPainter {
    private final @NotNull UniversePainter universePainter;

    //
    public ObjectContainerPainter(int @Nullable [] drawOffset, double scale,
                                  @NotNull UniversePainter universePainter) {
        super(drawOffset, scale);
        this.universePainter = universePainter;
    }

    //
    public final void paintMembers(@NotNull Graphics g,
                                   @NotNull BaryObjectContainerInterface container,
                                   double @NotNull [] absoluteLocation) {
        for (@NotNull BaryObject object : Collections.unmodifiableList(container.getObjects())) {
            try {
                universePainter.getGenericObjectPainter().paintBaryObject(g, object, absoluteLocation);
            } catch (UnrecognizedBaryObjectTypeException e) {
                throw new RuntimeException(e); //TODO: needs better solution, such as not painting the object and logging to console
            }
        }
    }

    public void paintBarySystem(@NotNull Graphics g,
                                @NotNull BarySystem system,
                                double @NotNull [] absoluteLocation) {
        double @NotNull [] scaledLocation = scaleLocation(absoluteLocation);
        CommonPainting.paintCenterMarker(g, getDrawableFromScaled(scaledLocation), system.getColor());
        //TODO: paint some general system-wide data here
        paintMembers(g, system, absoluteLocation);
    }
}

//
class SimpleObjectPainter extends ScaledOffsetPainter {
    //
    public SimpleObjectPainter(int @Nullable [] drawOffset, double scale) {
        super(drawOffset, scale);
    }

    //
    public void paintSimpleBaryObject(@NotNull Graphics g,
                                      @NotNull BarySimpleObject simpleObject,
                                      double @NotNull [] absoluteLocation) {
        double @NotNull [] drawCenter = getDrawableFromScaled(scaleLocation(absoluteLocation));
        paintPhysicalBody(g, simpleObject.getSimpleBody(), drawCenter);
    }

    private void paintPhysicalBody(@NotNull Graphics g,
                                   @NotNull PhysicalBody body,
                                   double @NotNull [] drawCenter) {
        CommonPainting.drawCircleAtCenter(g, drawCenter, scaleValue(body.getRadius()), body.getColor(), true);
        //TODO: improve this eventually for more detail
    }
}