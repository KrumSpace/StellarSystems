package baryModel;

import java.util.List;
import java.awt.Color;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import kinetics.Location;
import kinetics.Velocity;
import baryModel.exceptions.TopLevelObjectException;
import baryModel.exceptions.ObjectRemovedException;
import baryModel.basicModels.BasicBaryObject;
import baryModel.basicModels.NonInfluentialObject;
import baryModel.basicModels.InfluentialObject;
import baryModel.simpleObjects.PhysicalBaryObject;
import baryModel.systems.AbstractBarySystem;
import baryModel.systems.BarySystem;

/**
 * Top-level bound object; a root system.
 * Needed for proper SOI calculations, and SOI for this object needs to be infinite.
 */
public class BaryUniverse extends AbstractBarySystem {
    private static final @NotNull Color TOP_OBJECT_COLOR = Color.white;

    //
    public BaryUniverse() {
        super(null, null, null, TOP_OBJECT_COLOR);
    }

    //
    @Override
    public final void setParent(@Nullable BaryObjectContainerInterface parent) throws TopLevelObjectException {
        throw new TopLevelObjectException();
    }

    //
    @Override
    public final double getInfluenceRadius() throws TopLevelObjectException {
        throw new TopLevelObjectException();
    }

    //this should not get called
    @Override
    public final void interactWith(@NotNull NonInfluentialObject object) {
        throw new RuntimeException(new TopLevelObjectException());
    }

    //this should not get called
    @Override
    public final void interactWith(@NotNull PhysicalBaryObject object) {
        throw new RuntimeException(new TopLevelObjectException());
    }

    //this should not get called
    @Override
    public final void interactWith(@NotNull AbstractBarySystem object) {
        throw new RuntimeException(new TopLevelObjectException());
    }

    //handles the dynamics for a single cycle
    final void handleDynamics(double time) {
        calculate(time);
        update();
        /* TODO: recalculate barycenters here, after coordinates' update
         *  * go through all objects
         *  * could combine this with coordinate normalization
         *      * location normalization, so that the top object is always at {0, 0}
         *      * normalization of angles
         */
        //updateCenter();
    }

    //doesn't calculate itself, only members
    @Override
    public final void calculate(double time) {
        calculateMembers(time);
    }

    //handles the dynamics for a single cycle
    final void handleStructure() {
        checkMeaninglessSystems();
        //barycenter recalculation shouldn't be necessary here, as they should be conserved

        checkChildNeighbors();
        /* TODO: recalculate barycenters, if system changes happen
         *  * if a new system is formed, the new barycenter will be created automatically at {0, 0}
         *  * if an object enters a system
         *      * only the target system's barycenter needs to be recalculated
         *      * parent's barycenter should be conserved
         *  * if a collision happens
         *      * there might be mass loss etc, so barycenters need to be recalculated all the way to the top
         *      * it would be easier to just check all members once, rather then checking all possibly multiple times
         */
        //updateCenter();
    }

    //goes through members, but doesn't check itself
    @Override
    public final void checkMeaninglessSystems() {
        @NotNull List<@NotNull BasicBaryObject> objects = getObjects();
        for (int i = 0; i < objects.size(); i++) {
            @NotNull BasicBaryObject object = objects.get(i);
            if (object instanceof @NotNull BaryObjectContainerInterface container) {
                try {
                    container.checkMeaninglessSystems();
                } catch (ObjectRemovedException ignored) {
                    i--;
                }
            }
        }
    }

    //not possible to exit the top-level system
    @Override
    public final void exitSystem() throws TopLevelObjectException {
        throw new TopLevelObjectException();
    }

    // Check child neighbors normally
    @Override
    public final void checkChildNeighbors() {
        super.checkChildNeighbors();
    }

    @Override
    public final void updateCenter() {
        @NotNull Location baryCenter = getBaryCenter();
        updateMemberCenters(baryCenter);
        resetUniverseMomentum();
    }

    private void resetUniverseMomentum() {
        double
                pxTotal = 0,
                pyTotal = 0,
                pzTotal = 0;
        for (@NotNull BasicBaryObject child : getObjects()) {
            if (child instanceof @NotNull InfluentialObject influentialChild) {
                @NotNull Velocity childVelocity = influentialChild.getVelocity();
                double childMass = influentialChild.getMass();
                pxTotal += childMass * childVelocity.getX();
                pyTotal += childMass * childVelocity.getY();
                pzTotal += childMass * childVelocity.getZ();
            }
        }
        double
                totalMass = getMass(),
                vx = pxTotal / totalMass,
                vy = pyTotal / totalMass,
                vz = pzTotal / totalMass;
        for (@NotNull BasicBaryObject child : getObjects()) {
            child.getVelocity().increaseCartesian(
                    -vx,
                    -vy,
                    -vz);
        }
    }

    // No neighbors to check for a top-bound object.
    @Override
    public final void checkNeighbors() throws TopLevelObjectException {
        throw new TopLevelObjectException();
    }

    // There shouldn't be any neighbors to enter. Throw an exception, if any is found.
    // Furthermore, top-bound object should always remain at top.
    @Override
    public final void enterNeighboringSystem(@NotNull BarySystem neighbor) {
        throw new RuntimeException(new TopLevelObjectException());
    }
}