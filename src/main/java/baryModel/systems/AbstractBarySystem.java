package baryModel.systems;

import java.util.List;
import java.util.ArrayList;
import java.awt.Color;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import kinetics.Location;
import kinetics.Velocity;
import baryModel.basicModels.BasicBaryObject;
import baryModel.BaryObject;
import baryModel.BaryObjectContainerInterface;

//
public abstract class AbstractBarySystem extends BaryObject implements BaryObjectContainerInterface {
    private final @NotNull List<@NotNull BasicBaryObject> objects = new ArrayList<>();
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
    public final @NotNull List<@NotNull BasicBaryObject> getObjects() {
        return objects;
    }

    //
    @Override
    public final void addObject(@NotNull BasicBaryObject object) {
        objects.add(object);
    }

    //
    @Override
    public final void removeObject(@NotNull BasicBaryObject object) {
        objects.remove(object);
    }

    //
    public final void calculateMembers(double time) {
        for (@NotNull BasicBaryObject object : objects) {
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
        for (@NotNull BasicBaryObject object : objects) {
            object.update();
        }
    }

    //
    public void updateBaryCenter(boolean updateThis, boolean updateAll) {
        @NotNull Location baryCenter = getBaryCenter();
        if (updateThis) {
            getLocation().increaseCartesian(baryCenter);
        }
        updateBaryCentersForChildren(baryCenter, updateAll);
    }

    //assumes previous center at {0, 0, 0}
    private void updateBaryCentersForChildren(@NotNull Location newCenter, boolean updateAll) {
        for (@NotNull BasicBaryObject object : objects) {
            if (updateAll && object instanceof @NotNull AbstractBarySystem system) {
                system.updateBaryCenter(true, true);
            }
            object.getLocation().increaseCartesian(
                    -(newCenter.getX()),
                    -(newCenter.getY()),
                    -(newCenter.getZ()));
        }
    }

    //
    @Override
    public final double getMass() {
        return getMassWithout(null);
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