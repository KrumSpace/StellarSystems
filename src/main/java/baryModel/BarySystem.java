package baryModel;

import java.util.List;
import java.util.ArrayList;
import java.awt.Color;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import utils.coordinates.Coordinates;

//
public class BarySystem extends BaryObject {
    private final @NotNull List<@NotNull BaryObject> objects;
    private final @NotNull Color color;

    //
    public BarySystem(@Nullable BarySystem parent, @NotNull Coordinates coordinates, @NotNull Color color) {
        super(parent, coordinates);
        objects = new ArrayList<>();
        this.color = color;
    }

    //
    @Override
    public void precalculate(double time) {
        super.precalculate(time);
        for (BaryObject object : objects) {
            object.precalculate(time);
        }
    }

    //
    @Override
    public void update() {
        super.update();
        for (BaryObject object : objects) {
            object.update();
        }
    }

    //
    public void addObject(@NotNull BaryObject object) {
        objects.add(object);
        object.setParent(this);
    }

    //
    public @NotNull List<@NotNull BaryObject> getObjects() {
        return objects;
    }

    //
    @Override
    public final @NotNull Color getColor() {
        return color;
    }
}