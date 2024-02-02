package baryModel.systems;

import java.util.List;
import java.awt.Color;

import org.jetbrains.annotations.NotNull;

import utils.coordinates.Coordinates;
import baryModel.exceptions.TopLevelObjectException;
import baryModel.exceptions.ObjectRemovedException;
import baryModel.BaryObject;
import baryModel.BaryObjectContainerInterface;
import baryModel.BaryUniverse;

/**
 * Top-level bound object; will always be an abstract system, never a simple object.
 * Needed for proper SOI calculations, as SOI for this object needs to be infinite
 */
public class TopBoundObject extends AbstractBarySystem {
    private static final @NotNull Color TOP_OBJECT_COLOR = Color.white;

    //
    public TopBoundObject(@NotNull BaryUniverse universe) {
        super(
                universe,
                new Coordinates(),
                TOP_OBJECT_COLOR);
    }

    //doesn't precalculate itself, only members
    @Override
    public final void precalculate(double time) {
        precalculateMembers(time);
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

    // Check child neighbors as usual
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