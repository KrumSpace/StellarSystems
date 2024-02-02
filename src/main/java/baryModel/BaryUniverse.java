package baryModel;

import java.awt.Color;
import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import utils.coordinates.Coordinates;
import baryModel.exceptions.ObjectRemovedException;
import baryModel.exceptions.TopLevelObjectException;
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
        super(null, new Coordinates(), TOP_OBJECT_COLOR);
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

    //does a complete cycle, use this for performing calculations
    public final void iterateDynamicsAndStructure(double time) {
        handleDynamics(time);
        handleStructure();
    }

    private void handleDynamics(double time) {
        precalculate(time);
        update();
        /* TODO: recalculate barycenters here, after coordinates' update
         *  * go through all objects
         *  * could combine this with coordinate normalization
         *      * location normalization, so that the top object is always at {0, 0}
         *      * normalization of angles
         */
    }

    //doesn't precalculate itself, only members
    @Override
    public final void precalculate(double time) {
        precalculateMembers(time);
    }

    private void handleStructure() {
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
    }

    //goes through members, but doesn't check itself
    @Override
    public final void checkMeaninglessSystems() {
        @NotNull List<@NotNull BaryObject> objects = getObjects();
        for (int i = 0; i < objects.size(); i++) {
            @NotNull BaryObject object = objects.get(i);
            if (object instanceof BaryObjectContainerInterface container) {
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

    // No neighbors to check for a top-bound object.
    @Override
    public final void checkNeighbors() {}

    // No neighbors to check for a top-bound object.
    @Override
    public final void checkNeighbor(@NotNull BaryObject neighbor) {}

    // There shouldn't be any neighbors to enter. Throw an exception, if any is found.
    // Furthermore, top-bound object should always remain at top.
    @Override
    public final void enterNeighboringSystem(@NotNull BarySystem neighbor) throws TopLevelObjectException {
        throw new TopLevelObjectException();
    }
}