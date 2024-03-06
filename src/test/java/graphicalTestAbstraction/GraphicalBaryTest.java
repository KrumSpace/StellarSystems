package graphicalTestAbstraction;

import org.jetbrains.annotations.NotNull;

import baryModel.BaryUniverse;
import baryModel.UniverseUpdater;

import commonGraphics.AbstractWindow;

//TODO: add graceful close/exit
@SuppressWarnings({"FieldCanBeLocal", "unused"})
public class GraphicalBaryTest extends GraphicalTest {
    private final @NotNull BaryUniverse universe;
    private final @NotNull UniverseUpdater universeUpdater;

    //
    public GraphicalBaryTest(@NotNull BaryUniverse universe,
                             @NotNull AbstractWindow window) {
        super(window);
        this.universe = universe;
        universeUpdater = new UniverseUpdater(universe);
    }

    //
    public final @NotNull BaryUniverse getUniverse() {
        return universe;
    }

    //
    public final @NotNull UniverseUpdater getUniverseUpdater() {
        return universeUpdater;
    }
}