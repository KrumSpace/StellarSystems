package baryModel;

import org.jetbrains.annotations.NotNull;
import utils.coordinates.Coordinates;

import java.awt.Color;

//
public class BarySimpleObject extends BaryObject {
    private final @NotNull SimpleBody simpleBody;

    //
    public BarySimpleObject(@NotNull BarySystem parent, @NotNull Coordinates coordinates,
                            @NotNull SimpleBody simpleBody) {
        super(parent, coordinates);
        this.simpleBody = simpleBody;
    }

    //for graphical purposes
    @Override
    public final @NotNull Color getColor() {
        return simpleBody.getColor();
    }
}