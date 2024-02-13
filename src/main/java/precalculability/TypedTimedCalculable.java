package precalculability;

import org.jetbrains.annotations.NotNull;

import coordinates.ConvertibleCoordinates;

//
public interface TypedTimedCalculable<T extends ConvertibleCoordinates> {
    //calculates, time in seconds
    void calculate(double time, @NotNull T parameter);
}