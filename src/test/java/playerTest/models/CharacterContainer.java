package playerTest.models;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

//functions as a map
public class CharacterContainer {
    private final @NotNull List<@NotNull Character> characters;

    //
    CharacterContainer() {
        characters = new ArrayList<>();
    }

    void add(@NotNull Character character) {
        characters.add(character);
    }

    @NotNull List<@NotNull Character> getCharacters() {
        return characters;
    }
}
