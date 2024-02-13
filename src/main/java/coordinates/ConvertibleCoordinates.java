package coordinates;

import org.jetbrains.annotations.NotNull;

//for converting coordinate systems
public abstract class ConvertibleCoordinates implements CartesianCoordinateInterface, PolarCoordinateInterface {
    //
    ConvertibleCoordinates() {}

    //copies values to this
    public abstract void copy(@NotNull ConvertibleCoordinates original);
}