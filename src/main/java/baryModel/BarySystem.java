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
    public void addObject(@NotNull BaryObject object) {
        objects.add(object);
    }

    //
    public void removeObject(@NotNull BaryObject object) {
        objects.remove(object);
    }

    //
    public @NotNull List<@NotNull BaryObject> getObjects() {
        return objects;
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
    public void checkMeaninglessSystems() {
        double influenceRadius = getInfluenceRadius();
        for (int i = 0; i < objects.size(); i++) {
            BaryObject object = objects.get(i);

            //if it's a system, check also one level deeper
            if (object instanceof BarySystem) {
                ((BarySystem) object).checkMeaninglessSystems();
            }

            //if body exceeds parent's influence radius, move to upper level
            double distance = object.getCoordinates().getLocation().getRadial()[0];
            if (distance > influenceRadius) {
                try {
                    object.moveLevelUp();
                    i--;
                } catch (NullParentException ignored) {}
            }

        }

        if (getParent() != null && !(this instanceof BaryUniverse)) { //checks that the universe doesn't get dissolved
            //if system has 1 or less members
            if (objects.size() <= 1) {
                moveAllMembersUp(); //move all members to upper level
                //dissolve system
                getParent().removeObject(this);
            }
        }
        //new coordinates for object should be: old system coordinates + old relative coordinates
    }

    private void moveAllMembersUp() {
        for (int i = 0; i < objects.size(); i++) {
            BaryObject object = objects.get(i);
            try {
                object.moveLevelUp();
                //objects.remove(i);
                i--;
            } catch (NullParentException ignored) {}
        }
    }

    //
    @Override
    public final @NotNull Color getColor() {
        return color;
    }
}