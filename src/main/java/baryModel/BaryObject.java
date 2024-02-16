package baryModel;

import java.awt.Color;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import kinetics.Location;
import kinetics.Velocity;
import kinetics.Acceleration;
import baryModel.exceptions.TopLevelObjectException;
import baryModel.exceptions.DifferentParentException;
import baryModel.exceptions.ObjectRemovedException;
import baryModel.exceptions.NeighborRemovedException;
import baryModel.basicModels.InfluentialObject;
import baryModel.systems.AbstractBarySystem;
import baryModel.systems.BarySystem;

//
public abstract class BaryObject extends InfluentialObject {

    //
    public BaryObject(@Nullable BaryObjectContainerInterface parent,
                      @Nullable Location location,
                      @Nullable Velocity velocity,
                      @Nullable Acceleration acceleration) {
        super(parent, location, velocity, acceleration);
    }

    //
    @Override
    public void exitSystem() throws TopLevelObjectException {
        @Nullable BaryObjectContainerInterface parent = getParent();
        if (parent instanceof BaryUniverse) {
            throw new TopLevelObjectException();
        } else {
            //find new parent
            if (!(parent instanceof BaryChildInterface)) {
                throw new RuntimeException("Parent is not a child, therefore does not not have a parent. Unable to move an object up!");
            } else {
                @NotNull BaryObjectContainerInterface grandparent = ((BaryChildInterface) parent).getParent();

                //remove from old list
                parent.removeObject(this);

                //calculate and set new coordinates
                if (parent instanceof AbstractBarySystem) {
                    setNewCoordinatesWhenExiting((AbstractBarySystem) parent);
                } else {
                    throw new RuntimeException("Parent is not a system, unable to get coordinates!");
                }

                //set new parent
                setParent(grandparent);

                //add to new list
                grandparent.addObject(this);
            }
        }
    }

    private void setNewCoordinatesWhenExiting(@NotNull AbstractBarySystem parentSystem) {
        @NotNull Location oldSystemCoordinates = parentSystem.getLocation();
        getLocation().increaseCartesian(
                oldSystemCoordinates.getX(),
                oldSystemCoordinates.getY(),
                oldSystemCoordinates.getZ());
        @NotNull Velocity oldSystemVelocity = parentSystem.getVelocity();
        getVelocity().increaseCartesian(
                oldSystemVelocity.getX(),
                oldSystemVelocity.getY(),
                oldSystemVelocity.getZ());
    }

    //unfinished, TODO: finish
    public void transfer() {
        //remove from previous system
        //add to new system
    }

    //
    @Override
    public void enterNeighboringSystem(@NotNull BarySystem neighbor) throws DifferentParentException, TopLevelObjectException {
        //TODO: finish this
    }

    //transfer this object from one system to another with precalculated kinetic parameters
    public final void transferPrecalculated(@NotNull BaryObjectContainerInterface oldParent,
                                            @NotNull BaryObjectContainerInterface newParent,
                                            @NotNull Location newLocation,
                                            @NotNull Velocity newVelocity,
                                            @NotNull Acceleration newAcceleration) {
        try {
            oldParent.removeObject(this);
            setParent(newParent);
            getLocation().copy(newLocation);
            getVelocity().copy(newVelocity);
            getAcceleration().copy(newAcceleration);
            newParent.addObject(this);
        } catch (@NotNull TopLevelObjectException e) {
            throw new RuntimeException(e);
        }
    }



    //checks if parent is either the universe or its child count is greater than 2
    public final boolean neighborMergeabiltyCheck() {
        try {
            @NotNull BaryObjectContainerInterface parent = getParent();
            return parent instanceof BaryUniverse || parent.getObjects().size() > 2;
        } catch (@NotNull TopLevelObjectException ignored) {
            return false;
        }
    }

    //forms a new system from this and a neighbor
    public void formNewSystemWithNeighbor(@NotNull BaryObject neighbor) throws ObjectRemovedException {
        @NotNull Color color = Color.yellow; //TODO: improve the color
        try {
            BarySystem.formNewSystem(this, neighbor, color);
            @NotNull ObjectRemovedException exception = new ObjectRemovedException();
            exception.addSuppressed(new NeighborRemovedException());
            throw exception;
        } catch (@NotNull DifferentParentException ignored) {} //TODO: yo, don't ignore this!
    }
}