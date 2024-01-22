package baryModel;

import org.jetbrains.annotations.NotNull;

import utils.UpdatableValueInterface;

//
public class BaryModel implements UpdatableValueInterface.BufferedValueInterface {
    private final @NotNull BaryUniverse universe;

    //
    public BaryModel() {
        universe = new BaryUniverse();
    }

    //
    public final @NotNull BaryUniverse getUniverse() {
        return universe;
    }

    //
    @Override
    public final void precalculate(double time) {
        universe.precalculate(time);
    }

    //
    @Override
    public final void update() {
        universe.update();
    }
}