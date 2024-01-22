package utils.coordinates;

import org.jetbrains.annotations.NotNull;

//for converting coordinate systems
public interface ConvertibleCoordinateSystemInterface {
    //
    double @NotNull [] getCartesian();

    //
    double @NotNull [] getRadial();
}