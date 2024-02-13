package baryModel.systems;

import java.util.List;
import java.util.ArrayList;
import java.awt.Color;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import kinetics.Location;
import kinetics.Velocity;
import baryModel.BaryObject;
import baryModel.BaryObjectContainerInterface;

//
public abstract class AbstractBarySystem extends BaryObject implements BaryObjectContainerInterface {
    static final boolean MERGE_ON_TOUCH = false;
    private final @NotNull List<@NotNull BaryObject> objects = new ArrayList<>();
    private final @NotNull SystemName name;
    private final @NotNull Color color;

    //
    public AbstractBarySystem(@Nullable BaryObjectContainerInterface parent,
                              @Nullable Location location,
                              @Nullable Velocity velocity,
                              @NotNull Color color) {
        super(parent, location, velocity, null);
        this.color = color;
        name = new SystemName(null);
    }

    //
    @Override
    public final @NotNull List<@NotNull BaryObject> getObjects() {
        return objects;
    }

    //
    @Override
    public final void addObject(@NotNull BaryObject object) {
        objects.add(object);
    }

    //
    @Override
    public final void removeObject(@NotNull BaryObject object) {
        objects.remove(object);
    }

    //
    public final void calculateMembers(double time) {
        for (@NotNull BaryObject object : objects) {
            object.calculate(time);
        }
    }

    //
    @Override
    public final void update() {
        super.update();
        updateMembers();
    }

    private void updateMembers() {
        for (@NotNull BaryObject object : objects) {
            object.update();
        }
    }

    public final void updateCenter() {
        @NotNull Location baryCenter = getBaryCenter();
        getLocation().copy(baryCenter);
        updateMemberCenters(baryCenter);
    }

    private void updateMemberCenters(@NotNull Location newCenter) {
        for (@NotNull BaryObject object : objects) {
            object.getLocation().increaseCartesian(
                    -(newCenter.getX()),
                    -(newCenter.getY()),
                    -(newCenter.getZ()));
            if (object instanceof @NotNull AbstractBarySystem system) {
                system.updateCenter();
            }
        }
    }

    //
    @Override
    public final double getMass() {
        return getMassWithout(null);
    }

    //
    public final double getMassWithout(@Nullable BaryObject object) {
        double mass = 0;
        for (@NotNull BaryObject object1 : objects) {
            if (object1 != object) {
                mass += object1.getMass();
            }
        }
        return mass;
    }

    //
    public final @NotNull Location getBaryCenter() {
        return getBaryCenterWithout(null);
    }

    //
    public final @NotNull Location getBaryCenterWithout(@Nullable BaryObject object) {
        double
                totalMass = getMassWithout(object),
                weightedX = 0,
                weightedY = 0,
                weightedZ = 0;
        for (@NotNull BaryObject object1 : objects) {
            if (object1 != object) {
                double mass = object1.getMass();
                weightedX += object1.getLocation().getX() * mass;
                weightedY += object1.getLocation().getY() * mass;
                weightedZ += object1.getLocation().getZ() * mass;
            }
        }
        return new Location(
                weightedX / totalMass,
                weightedY / totalMass,
                weightedZ / totalMass);
    }

    //
    @Override
    public final @NotNull String getName() {
        return name.name();
    }

    //
    @Override
    public final @NotNull Color getColor() {
        return color;
    }
}