package baryModel.basicModels;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import kinetics.Location;
import kinetics.Velocity;
import kinetics.Acceleration;
import baryModel.exceptions.ObjectRemovedException;
import baryModel.exceptions.NeighborRemovedException;
import baryModel.simpleObjects.PhysicalBaryObject;
import baryModel.BaryObjectContainerInterface;
import baryModel.systems.AbstractBarySystem;

//
public abstract class NonInfluentialObject extends BasicBaryObject {
    //
    public NonInfluentialObject(@Nullable BaryObjectContainerInterface parent,
                                @Nullable Location location,
                                @Nullable Velocity velocity,
                                @Nullable Acceleration acceleration) {
        super(parent, location, velocity, acceleration);
    }

    //
    @Override
    public final void interactWith(@NotNull NonInfluentialObject object)
            throws ObjectRemovedException, NeighborRemovedException {
        //TODO: define NonInfluential-NonInfluential interaction here
    }

    //
    @Override
    public final void interactWith(@NotNull PhysicalBaryObject object)
            throws ObjectRemovedException, NeighborRemovedException {
        //TODO: define NonInfluential-Physical interaction here
    }

    //
    @Override
    public final  void interactWith(@NotNull AbstractBarySystem object)
            throws ObjectRemovedException, NeighborRemovedException {
        //TODO: define NonInfluential-System interaction here
    }
}