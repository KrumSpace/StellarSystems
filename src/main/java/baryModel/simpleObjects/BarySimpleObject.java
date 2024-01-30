package baryModel.simpleObjects;

import java.awt.Color;

import org.jetbrains.annotations.NotNull;

import static consoleUtils.SimplePrinting.printLine;

import utils.coordinates.Coordinates;
import baryModel.*;

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
        if (neighbor instanceof BarySimpleObject) {
            //simpleObject - simpleObject case
            @NotNull PhysicalBody neighborBody = ((BarySimpleObject) neighbor).getSimpleBody();
            double collisionDistance = (getSimpleBody().getRadius() + neighborBody.getRadius()) / 2;
            if (distance < collisionDistance) {
                //TODO: do collision depending on relative sizes
                printLine("Collision between " + getName() + " and " + neighbor.getName());
            }
            if (distance < Math.max(getInfluenceRadius(), neighbor.getInfluenceRadius()) && neighborMergeabiltyCheck()) {
                //TODO: form a new system of this and neighbor
                //printLine("A new system should be formed between " + getName() + " and " + neighbor.getName());
                formNewSystem(neighbor);
            }
        } else if (neighbor instanceof BarySystem) {
            //simpleObject - system case
            if (distance < neighbor.getInfluenceRadius()) {
                //TODO: this enters neighbor system
                printLine("Object " + getName() + " should enter system " + neighbor.getName());
            } else if (distance < getInfluenceRadius() && neighborMergeabiltyCheck()) {
                //TODO: form a system of A and B
                //printLine("A new system should be formed between " + getName() + " and " + neighbor.getName());
                formNewSystem(neighbor);
            }
        } else {
            throw new UnrecognizedBaryObjectTypeException();
        }
    }

    //forms a new system from this and a neighbor
    private void formNewSystem(@NotNull BaryObject neighbor) throws ObjectRemovedException {
        @NotNull Color color = Color.yellow; //TODO: improve the color
        try {
            BarySystem.formNewSystem(this, neighbor, color);
            @NotNull ObjectRemovedException exception = new ObjectRemovedException();
            exception.addSuppressed(new NeighborRemovedException());
            throw exception;
        } catch (BarySystem.DifferentParentException ignored) {}
    }
}