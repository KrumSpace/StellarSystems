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
    public void enterNeighboringSystem(@NotNull BarySystem neighbor) throws DifferentParentException {
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
        @NotNull Color color = new Color(20, 40, 150); //TODO: improve the color
        try {
            BarySystem.formNewSystem(this, neighbor, color);
            @NotNull ObjectRemovedException exception = new ObjectRemovedException();
            exception.addSuppressed(new NeighborRemovedException());
            throw exception;
        } catch (@NotNull DifferentParentException ignored) {} //TODO: yo, don't ignore this!
    }
}