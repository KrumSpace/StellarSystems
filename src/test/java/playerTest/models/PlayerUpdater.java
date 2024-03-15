package playerTest.models;

import ThreadAbstraction.AbstractUpdater;
import org.jetbrains.annotations.NotNull;

//
public final class PlayerUpdater extends AbstractUpdater {
    private final @NotNull Player player;

    //
    PlayerUpdater(@NotNull Player player) {
        this.player = player;
    }

    //
    @Override
    public void update() {
        //TODO: update player here
    }
}
