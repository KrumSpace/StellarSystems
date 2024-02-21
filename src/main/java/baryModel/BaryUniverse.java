package baryModel;

import java.util.List;
import java.awt.Color;

import org.jetbrains.annotations.NotNull;

import baryModel.exceptions.TopLevelObjectException;
import baryModel.exceptions.ObjectRemovedException;
import baryModel.basicModels.BasicBaryObject;
import baryModel.systems.BarySystem;

//
public class BaryUniverse extends TopLevelObject {
    private static final @NotNull Color TOP_OBJECT_COLOR = Color.white;

    //
    public BaryUniverse() {
        super(TOP_OBJECT_COLOR);
    }

    //handles the dynamics for a single cycle
    final void handleDynamics(double time) {
        calculate(time);
        update();
        /* TODO: recalculate barycenters here, after coordinates' update
         *  * go through all objects
         */
        updateBaryCenter();
        /*
         * TODO: normalize angles here
         *  * go through all objects
         */
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
        updateBaryCenter();
        //TODO: could normalize angles again here
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

    // Check child neighbors normally
    @Override
    public final void checkChildNeighbors() {
        super.checkChildNeighbors();
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