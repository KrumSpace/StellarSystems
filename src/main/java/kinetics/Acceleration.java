package kinetics;

import java.util.List;
import java.util.ArrayList;

import org.jetbrains.annotations.NotNull;

import utils.MathUtils;

//
public class Acceleration extends CalculableVector {
    private final @NotNull List<@NotNull Acceleration> components;

    //
    public Acceleration(double magnitude, double horizontalAngle, double verticalAngle) {
        super(magnitude, horizontalAngle, verticalAngle);
        components = new ArrayList<>();
    }

    //
    @Override
    public final void calculate(double time) {
        double x = 0, y = 0, z = 0;
        while (!components.isEmpty()) {
            @NotNull Acceleration component = components.removeFirst();
            x += component.getX();
            y += component.getY();
            z += component.getZ();
        }
        setCartesian(x, y, z);
    }

    //
    public final void addComponent(@NotNull Acceleration component) {
        components.add(component);
    }

    //
    public static @NotNull Acceleration newFromProjections(double ax, double ay, double az) {
        double
                horizontalMagnitude = Math.hypot(ax, ay),
                magnitude = Math.hypot(horizontalMagnitude, az),
                horizontalAngle = MathUtils.getAngle(ax, ay),
                verticalAngle = MathUtils.getAngle(horizontalMagnitude, az);
        return new Acceleration(magnitude, horizontalAngle, verticalAngle);
    }
}