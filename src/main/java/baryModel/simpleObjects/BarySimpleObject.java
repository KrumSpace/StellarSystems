package baryModel.simpleObjects;

import java.awt.Color;

import baryModel.BaryObject;
import baryModel.BaryObjectContainerInterface;
import org.jetbrains.annotations.NotNull;

import utils.coordinates.Coordinates;

//
public class BarySimpleObject extends BaryObject {
    private final @NotNull PhysicalBody simpleBody;

    //
    public BarySimpleObject(@NotNull BaryObjectContainerInterface parent,
                            @NotNull Coordinates coordinates,
                            @NotNull PhysicalBody simpleBody) {
        super(parent, coordinates);
        this.simpleBody = simpleBody;
    }

    //
    @Override
    public final double getMass() {
        return simpleBody.getMass();
    }

    //for graphical purposes
    @Override
    public final @NotNull Color getColor() {
        return simpleBody.getColor();
    }

    //
    public final @NotNull PhysicalBody getSimpleBody() {
        return simpleBody;
    }
}