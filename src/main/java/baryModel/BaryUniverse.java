package baryModel;

import java.util.List;

import org.jetbrains.annotations.NotNull;

import utils.PrecalculableInterface;
import baryModel.systems.TopBoundObject;

//
public class BaryUniverse implements BaryObjectContainerInterface, PrecalculableInterface.BufferedValueInterface {
    private final @NotNull TopBoundObject topObject;

    //
    public BaryUniverse() {
        topObject = new TopBoundObject(this);
    }

    //
    @Override
    public final @NotNull List<@NotNull BaryObject> getObjects() {
        return topObject.getObjects();
    }

    //
    @Override
    public final void addObject(@NotNull BaryObject object) {
        topObject.addObject(object);
    }

    //
    @Override
    public final void removeObject(@NotNull BaryObject object) {
        topObject.removeObject(object);
    }

    //does a complete cycle
    public final void iterateDynamicsAndStructure(double time) {
        handleDynamics(time);
        handleStructure();
    }

    private void handleDynamics(double time) {
        precalculate(time);
        update();
        /* TODO: recalculate barycenters here, after coordinates' update
         * go through all objects
         * could combine this with coordinate normalization
         * location normalization, so that the top object is always at {0, 0}
         * normalization of angles
         */
    }

    //
    @Override
    public final void precalculate(double time) {
        topObject.precalculate(time);
    }

    //
    @Override
    public final void update() {
        topObject.update();
    }

    private void handleStructure() {
        checkMeaninglessSystems();
        //barycenter recalculation shouldn't be necessary here, as they should be conserved

        checkChildNeighbors();
        /* TODO: recalculate barycenters, if system changes happen
         * if a new system is formed, the new barycenter will be created automatically at {0, 0}
         * if an object enters a system
         * only the target system's barycenter needs to be recalculated
         * parent's barycenter should be conserved
         * if a collision happens
         * there might be mass loss etc, so barycenters need to be recalculated all the way to the top
         * it would be easier to just check all members once, rather then checking all possibly multiple times
         */
    }

    //
    @Override
    public final void checkMeaninglessSystems() {
        topObject.checkMeaninglessSystems();
    }

    //
    @Override
    public final void checkChildNeighbors() {
        topObject.checkChildNeighbors();
    }
}