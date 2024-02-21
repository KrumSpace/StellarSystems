package coordinates;

import org.jetbrains.annotations.NotNull;

//
interface CartesianCoordinateInterface {
    //
    double getX();

    //
    void setX(double x);

    //
    default void increaseX(double delta) {
        setX(getX() + delta);
    }

    //
    double getY();

    //
    void setY(double y);

    //
    default void increaseY(double delta) {
        setY(getY() + delta);
    }

    //
    double getZ();

    //
    void setZ(double z);

    //
    default void increaseZ(double delta) {
        setZ(getZ() + delta);
    }

    //
    default void setCartesian(double x, double y, double z) {
        setX(x);
        setY(y);
        setZ(z);
    }

    //
    default void increaseCartesian(double dx, double dy, double dz) {
        increaseX(dx);
        increaseY(dy);
        increaseZ(dz);
    }

    //
    default void increaseCartesian(@NotNull CartesianCoordinateInterface deltaCoordinates) {
        increaseCartesian(
                deltaCoordinates.getX(),
                deltaCoordinates.getY(),
                deltaCoordinates.getZ());
    }
}