package coordinates;

//
interface PolarCoordinateInterface {
    //
    double getRadius();

    //
    void setRadius(double radius);

    //
    default void increaseRadius(double delta) {
        setRadius(getRadius() + delta);
    }

    //
    double getHorizontalAngle();

    //
    void setHorizontalAngle(double angle);

    //
    default void increaseHorizontalAngle(double delta) {
        setHorizontalAngle(getHorizontalAngle() + delta);
    }

    //
    double getVerticalAngle();

    //
    void setVerticalAngle(double angle);

    //
    default void increaseVerticalAngle(double delta) {
        setVerticalAngle(getVerticalAngle() + delta);
    }

    //
    default void setPolar(double radius, double horizontalAngle, double verticalAngle) {
        setRadius(radius);
        setHorizontalAngle(horizontalAngle);
        setVerticalAngle(verticalAngle);
    }

    //
    default void increasePolar(double dRadius, double dHorizontalAngle, double dVerticalAngle) {
        increaseRadius(dRadius);
        increaseHorizontalAngle(dHorizontalAngle);
        increaseVerticalAngle(dVerticalAngle);
    }
}