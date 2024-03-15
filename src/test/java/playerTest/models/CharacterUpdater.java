package playerTest.models;

import ThreadAbstraction.AbstractUpdater;
import org.jetbrains.annotations.NotNull;

import java.util.List;

//
public final class CharacterUpdater extends AbstractUpdater {
    private final @NotNull CharacterContainer characters;

    //
    CharacterUpdater(@NotNull CharacterContainer characters) {
        this.characters = characters;
    }

    //
    @Override
    public void update() {
        @NotNull List<@NotNull Character> characterList = characters.getCharacters();
        for (@NotNull Character character : characterList) {
            updateSingleCharacter(character);
        }
    }

    private void updateSingleCharacter(@NotNull Character character) {
        //TODO: update characters here
        //semi-ordered below:

        //analyze situation
        //check AI or user input
        //apply forces, accelerations, etc.

        //precalculate trajectory
        //check collisions
        //apply coordinate, velocity etc. changes
    }
}
