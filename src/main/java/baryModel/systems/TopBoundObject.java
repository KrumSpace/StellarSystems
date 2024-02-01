package baryModel.systems;

import java.util.List;
import java.awt.Color;

import org.jetbrains.annotations.NotNull;

import utils.coordinates.Coordinates;
import utils.coordinates.Location;
import utils.coordinates.Velocity;
import baryModel.exceptions.ObjectRemovedException;
import baryModel.BaryObject;
import baryModel.BaryObjectContainerInterface;
import baryModel.BaryUniverse;

/**
 * Top-level bound object; will always be a system.
 * Needed for proper SOI calculations, as SOI for this object needs to be infinite
 */
public class TopBoundObject extends AbstractBarySystem {
    private static final @NotNull Color TOP_OBJECT_COLOR = Color.white;

    //
    public TopBoundObject(@NotNull BaryUniverse universe) {
        super(
                universe,
                new Coordinates(
                        new Location.LocationCartesian(0, 0),
                        new Velocity.VelocityCartesian(0, 0)),
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

    @Override
    public final void checkChildNeighbors() {
        super.checkChildNeighbors();
    }

    //no neighbors to check
    @Override
    public final void checkNeighbors() {}

    //no neighbors to check
    @Override
    public final void checkNeighbor(@NotNull BaryObject neighbor) {}
}