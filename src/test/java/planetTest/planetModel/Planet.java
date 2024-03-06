package planetTest.planetModel;

import java.awt.Color;

import org.jetbrains.annotations.NotNull;

//
public class Planet {
    private final double
            mass,
            radius;
    private final @NotNull Color color;

    //
    public Planet(double mass, double radius, @NotNull Color color) {
        this.mass = mass;
        this.radius = radius;
        this.color = color;
    }

    //
    public double getMass() {
        return mass;
    }

    //
    public double getRadius() {
        return radius;
    }

    //
    public @NotNull Color getColor() {
        return color;
    }
}