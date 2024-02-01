package baryModel.systems;

import java.util.List;
import java.util.ArrayList;
import java.awt.Color;

import org.jetbrains.annotations.NotNull;

import utils.coordinates.Coordinates;
import baryModel.BaryObject;
import baryModel.BaryObjectContainerInterface;

//
public abstract class AbstractBarySystem extends BaryObject implements BaryObjectContainerInterface {
    static final boolean MERGE_ON_TOUCH = false;
    private final @NotNull List<@NotNull BaryObject> objects = new ArrayList<>();
    private final @NotNull SystemName name;
    private final @NotNull Color color;

    //
    public AbstractBarySystem(@NotNull BaryObjectContainerInterface parent,
                              @NotNull Coordinates coordinates,
                              @NotNull Color color) {
        super(parent, coordinates);
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

    //TODO: improve this
    @Override
    public final double getMass() {
        double mass = 0;
        for (BaryObject object : objects) {
            mass += object.getMass();
        }
        return mass;
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

    //
    @Override
    public void precalculate(double time) {
        super.precalculate(time);
        precalculateMembers(time);
    }

    //
    final void precalculateMembers(double time) {
        for (@NotNull BaryObject object : objects) {
            object.precalculate(time);
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
}