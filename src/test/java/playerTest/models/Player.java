package playerTest.models;

import org.jetbrains.annotations.Nullable;

//
public class Player {
    private @Nullable Character character;

    //
    Player() {
        //
    }

    public void setCharacter(@Nullable Character character) {
        this.character = character;
    }

    public @Nullable Character getCharacter() {
        return character;
    }
}
