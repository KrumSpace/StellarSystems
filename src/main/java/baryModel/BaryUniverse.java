package baryModel;

import java.awt.Color;

import org.jetbrains.annotations.NotNull;

import utils.UpdatableValueInterface;

//
public class BaryUniverse implements BaryObjectContainerInterface, UpdatableValueInterface.BufferedValueInterface {
    private static final @NotNull Color DEFAULT_COLOR = Color.white;
    private final @NotNull Color color; //for center marker and text

    //
    public BaryUniverse() {
        color = DEFAULT_COLOR;
    }

    //does a complete cycle
    public final void completeCycle(double time) {
        precalculate(time);
        update();
        checkMeaninglessSystems();
        checkChildNeighbors();
    }

    //
    @Override
    public final void precalculate(double time) {
        for (BaryObject object : objects) {
            object.precalculate(time);
        }
    }

    //
    @Override
    public final void update() {
        for (BaryObject object : objects) {
            object.update();
        }
    }

    //
    @Override
    public final void checkMeaninglessSystems() {
        for (BaryObject object : objects) {
            if (object instanceof BaryObjectContainerInterface container) {
                container.checkMeaninglessSystems();
            }
        }
    }
}