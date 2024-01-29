package baryModel.simpleObjects;

import java.awt.Color;

import org.jetbrains.annotations.NotNull;

import utils.coordinates.Coordinates;
import baryModel.*;

import static consoleUtils.SimplePrinting.printLine;

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
    public final double getMass() {
        return simpleBody.getMass();
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
        if (neighbor instanceof BarySimpleObject) {
            //simpleObject - simpleObject case
            @NotNull PhysicalBody neighborBody = ((BarySimpleObject) neighbor).getSimpleBody();
            double collisionDistance = getSimpleBody().getRadius() + neighborBody.getRadius();
            if (distance < collisionDistance) {
                //TODO: do collision depending on relative sizes
                printLine("Collision between " + getSimpleBody().getName() + " and " + neighborBody.getName());
            }
            if (distance < Math.max(getInfluenceRadius(), neighbor.getInfluenceRadius()) && neighborMergeabiltyCheck()) {
                //TODO: form a new system of this and neighbor
                printLine("A new system should be formed between " + getSimpleBody().getName() + " and " + neighborBody.getName());
            }
        } else if (neighbor instanceof BarySystem) {
            //simpleObject - system case
            if (distance < neighbor.getInfluenceRadius()) {
                //TODO: this enters neighbor system
                printLine("Object " + getSimpleBody().getName() + " should enter system " + neighbor);
            } else if (distance < getInfluenceRadius() && neighborMergeabiltyCheck()) {
                //TODO: form a system of A and B
                printLine("A new system should be formed between " + getSimpleBody().getName() + " and " + neighbor);
            }
        } else {
            throw new UnrecognizedBaryObjectTypeException();
        }
    }
}