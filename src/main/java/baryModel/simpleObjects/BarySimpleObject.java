package baryModel.simpleObjects;

import java.awt.Color;

import org.jetbrains.annotations.NotNull;

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
            /**
             * TODO:
             *  collisionDistance = A.radius + B.radius
             *  if (distance < collisionDistance)
             *      do collision depending on relative sizes
             *  if (distance < MAX(A.influence, B.influence) AND neighborMergeabiltyCheck())
             *      form a system of A and B
             */
        } else if (neighbor instanceof BarySystem) {
            //simpleObject - system case
            /**
             * TODO:
             *  if (distance < B.influence)
             *      A enters B system, regardless of mass
             *  else if (distance < A.influence AND neighborMergeabiltyCheck())
             *      form a system of A and B
             */
        } else {
            throw new UnrecognizedBaryObjectTypeException();
        }
    }
}