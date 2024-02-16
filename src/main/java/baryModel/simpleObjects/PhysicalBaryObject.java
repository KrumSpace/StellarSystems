package baryModel.simpleObjects;

import java.awt.Color;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static consoleUtils.SimplePrinting.printLine;

import kinetics.Location;
import kinetics.Velocity;
import kinetics.Acceleration;
import baryModel.exceptions.*;
import baryModel.basicModels.NonInfluentialObject;
import baryModel.BaryObject;
import baryModel.BaryObjectContainerInterface;
import baryModel.systems.AbstractBarySystem;

//
public class PhysicalBaryObject extends BaryObject {
    private final @NotNull PhysicalBody physicalBody;

    //
    public PhysicalBaryObject(@NotNull BaryObjectContainerInterface parent,
                              @Nullable Location location,
                              @Nullable Velocity velocity,
                              @Nullable Acceleration acceleration,
                              @NotNull PhysicalBody physicalBody) {
        super(parent, location, velocity, acceleration);
        this.physicalBody = physicalBody;
    }

    //
    @Override
    public final void setParent(@Nullable BaryObjectContainerInterface parent) {
        try {
            super.setParent(parent);
        } catch (@NotNull TopLevelObjectException ignored) {}
    }

    //
    @Override
    public final double getMass() {
        return physicalBody.getMass();
    }

    //
    @Override
    public final double getInfluenceRadius() {
        try {
            return super.getInfluenceRadius();
        } catch (@NotNull TopLevelObjectException e) {
            //probably won't ever happen, but good practice to check anyway
            throw new RuntimeException(e);
        }
    }

    //
    @Override
    public final @NotNull String getName() {
        return physicalBody.getName();
    }

    //for graphical purposes
    @Override
    public final @NotNull Color getColor() {
        return physicalBody.getColor();
    }

    //
    public final @NotNull PhysicalBody getBody() {
        return physicalBody;
    }

    //PhysicalObject-NonInfluential interaction
    @Override
    public final void interactWith(@NotNull NonInfluentialObject object)
            throws ObjectRemovedException, NeighborRemovedException {
        //TODO: define Physical-NonInfluential interaction here
    }

    //PhysicalObject-PhysicalObject interaction
    @Override
    public final void interactWith(@NotNull PhysicalBaryObject object)
            throws ObjectRemovedException, NeighborRemovedException {
        double distance = getDistanceTo(object.getLocation()).getRadius();
        @NotNull PhysicalBody neighborBody = object.getBody();
        double collisionDistance = (getBody().getRadius() + neighborBody.getRadius()) / 2;
        if (distance < collisionDistance) {
            //TODO: do collision depending on relative sizes
            printLine("Collision between " + getName() + " and " + object.getName());
        }
        if (distance < Math.max(getInfluenceRadius(), object.getInfluenceRadius()) && neighborMergeabiltyCheck()) {
            //TODO: form a new system of this and neighbor
            //printLine("A new system should be formed between " + getName() + " and " + neighbor.getName());
            formNewSystemWithNeighbor(object);
        }
    }

    //PhysicalObject-System interaction
    @Override
    public final void interactWith(@NotNull AbstractBarySystem object)
            throws ObjectRemovedException, NeighborRemovedException {
        double distance = getDistanceTo(object.getLocation()).getRadius(), neighborInfluenceRadius = 0;
        try {
            neighborInfluenceRadius = object.getInfluenceRadius();
        } catch (@NotNull TopLevelObjectException e) {
            throw new RuntimeException(e);
        }
        if (distance < neighborInfluenceRadius) {
            //TODO: this enters neighbor system
            printLine("Object " + getName() + " should enter system " + object.getName());
        } else if (distance < getInfluenceRadius() && neighborMergeabiltyCheck()) {
            //TODO: form a system of A and B
            //printLine("A new system should be formed between " + getName() + " and " + neighbor.getName());
            formNewSystemWithNeighbor(object);
        }
    }
}