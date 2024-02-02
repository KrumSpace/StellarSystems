package baryModel.simpleObjects;

import java.awt.Color;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static consoleUtils.SimplePrinting.printLine;

import utils.coordinates.Coordinates;
import baryModel.exceptions.*;
import baryModel.*;
import baryModel.systems.BarySystem;

//
public class BarySimpleObject extends BaryObject {
    private final @NotNull PhysicalBody simpleBody;

    //
    public BarySimpleObject(@NotNull BaryObjectContainerInterface parent,
                            @NotNull Coordinates coordinates,
                            @NotNull PhysicalBody simpleBody) {
        super(parent, coordinates);
        this.simpleBody = simpleBody;
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
        return simpleBody.getMass();
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
        return simpleBody.getName();
    }

    //for graphical purposes
    @Override
    public final @NotNull Color getColor() {
        return simpleBody.getColor();
    }

    //
    public final @NotNull PhysicalBody getSimpleBody() {
        return simpleBody;
    }

    //
    @Override
    public final void checkNeighbor(@NotNull BaryObject neighbor) throws
            UnrecognizedBaryObjectTypeException, ObjectRemovedException, NeighborRemovedException {
        double distance = getDistanceToNeighbor(neighbor);
        if (neighbor instanceof @NotNull BarySimpleObject neighborObject) {
            //simpleObject - simpleObject case
            @NotNull PhysicalBody neighborBody = neighborObject.getSimpleBody();
            double collisionDistance = (getSimpleBody().getRadius() + neighborBody.getRadius()) / 2;
            if (distance < collisionDistance) {
                //TODO: do collision depending on relative sizes
                printLine("Collision between " + getName() + " and " + neighborObject.getName());
            }
            if (distance < Math.max(getInfluenceRadius(), neighborObject.getInfluenceRadius()) && neighborMergeabiltyCheck()) {
                //TODO: form a new system of this and neighbor
                //printLine("A new system should be formed between " + getName() + " and " + neighbor.getName());
                formNewSystemWithNeighbor(neighborObject);
            }
        } else if (neighbor instanceof @NotNull BarySystem neighborSystem) {

            //simpleObject - system case
            if (distance < neighborSystem.getInfluenceRadius()) {
                //TODO: this enters neighbor system
                printLine("Object " + getName() + " should enter system " + neighborSystem.getName());
            } else if (distance < getInfluenceRadius() && neighborMergeabiltyCheck()) {
                //TODO: form a system of A and B
                //printLine("A new system should be formed between " + getName() + " and " + neighbor.getName());
                formNewSystemWithNeighbor(neighborSystem);
            }
        } else {
            throw new UnrecognizedBaryObjectTypeException();
        }
    }
}