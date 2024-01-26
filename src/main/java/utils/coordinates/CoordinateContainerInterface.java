package utils.coordinates;

import org.jetbrains.annotations.NotNull;

//
public interface CoordinateContainerInterface {
    //
    @NotNull Coordinates getCoordinates();

    //
    void setCoordinates(@NotNull Coordinates coordinates);

    //
    void setCoordinates(@NotNull Location location, @NotNull Velocity velocity);
}