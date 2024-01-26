package baryModel.simpleObjects;

import java.awt.Color;

import org.jetbrains.annotations.NotNull;

//
public class PhysicalBody {
    private final @NotNull String name;
    @SuppressWarnings("FieldMayBeFinal")
    private double
            mass,
            radius;
    private final @NotNull Color color;

    //
    public PhysicalBody(@NotNull String name,
                        double mass, double radius,
                        @NotNull Color color) {
        this.name = name;
        this.mass = mass;
        this.radius = radius;
        this.color = color;
    }

    //
    public final @NotNull String getName() {
        return name;
    }

    //
    public final double getMass() {
        return mass;
    }

    //
    public final double getRadius() {
        return radius;
    }

    //for graphical purposes
    public @NotNull Color getColor() {
        return color;
    }
}