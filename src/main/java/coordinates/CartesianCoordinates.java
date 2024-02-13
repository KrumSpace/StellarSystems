package coordinates;

import org.jetbrains.annotations.NotNull;

import utils.MathUtils;

//
public class CartesianCoordinates extends ConvertibleCoordinates {
    private double x, y, z;

    //
    public CartesianCoordinates(double x, double y, double z) {
        super();
        this.x = x;
        this.y = y;
        this.z = z;
    }

    //
    @Override
    public final void copy(@NotNull ConvertibleCoordinates original) {
        setCartesian(original.getX(), original.getY(), original.getZ());
    }

    //
    @Override
    public final double getX() {
        return x;
    }

    //
    @Override
    public final void setX(double x) {
        this.x = x;
    }

    //
    @Override
    public final double getY() {
        return y;
    }

    //
    @Override
    public final void setY(double y) {
        this.y = y;
    }

    //
    @Override
    public final double getZ() {
        return z;
    }

    //
    @Override
    public final void setZ(double z) {
        this.z = z;
    }

    //
    @Override
    public final double getRadius() {
        return Math.hypot(getRadiusHorizontalProjection(x, y), z);
    }

    //preserves horizontal and vertical angles
    @Override
    public final void setRadius(double radius) {
        double
                horizontalAngle = getHorizontalAngle(),
                verticalAngle = getVerticalAngle(),
                radiusHorizontalProjection = radius * Math.cos(verticalAngle);
        setCartesian(
                radiusHorizontalProjection * Math.cos(horizontalAngle),
                radiusHorizontalProjection * Math.sin(horizontalAngle),
                radius * Math.sin(verticalAngle));
    }

    //
    @Override
    public final double getHorizontalAngle() {
        return MathUtils.getAngle(x, y);
    }

    //preserves radius and vertical angle; radius horizontal projection, z and vertical angle don't change
    @Override
    public final void setHorizontalAngle(double angle) {
        double radiusHorizontalProjection = getRadiusHorizontalProjection(x, y);
        setX(radiusHorizontalProjection * Math.cos(angle));
        setY(radiusHorizontalProjection * Math.sin(angle));
    }

    //
    @Override
    public final double getVerticalAngle() {
        return MathUtils.getAngle(getRadiusHorizontalProjection(x, y), z);
    }

    //preserves radius and horizontal angle
    @Override
    public final void setVerticalAngle(double angle) {
        double
                radius = getRadius(),
                horizontalAngle = getHorizontalAngle(),
                radiusHorizontalProjection = radius * Math.cos(angle);
        setCartesian(
                radiusHorizontalProjection * Math.cos(horizontalAngle),
                radiusHorizontalProjection * Math.sin(horizontalAngle),
                radius * Math.sin(angle));
    }

    private static double getRadiusHorizontalProjection(double x, double y) {
        return Math.hypot(x, y);
    }
}