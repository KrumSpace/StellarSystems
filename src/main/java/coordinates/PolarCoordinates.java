package coordinates;

import org.jetbrains.annotations.NotNull;

import utils.MathUtils;

//
public class PolarCoordinates extends ConvertibleCoordinates {
    private double radius, horizontalAngle, verticalAngle;

    //
    public PolarCoordinates(double radius, double horizontalAngle, double verticalAngle) {
        super();
        this.radius = radius;
        this.horizontalAngle = horizontalAngle;
        this.verticalAngle = verticalAngle;
    }

    //
    @Override
    public final void copy(@NotNull ConvertibleCoordinates original) {
        setPolar(original.getRadius(), original.getHorizontalAngle(), original.getVerticalAngle());
    }

    //
    @Override
    public final double getRadius() {
        return radius;
    }

    //
    @Override
    public final void setRadius(double radius) {
        this.radius = radius;
    }

    //
    @Override
    public final double getHorizontalAngle() {
        return horizontalAngle;
    }

    //
    @Override
    public final void setHorizontalAngle(double angle) {
        this.horizontalAngle = angle;
    }

    //
    @Override
    public final double getVerticalAngle() {
        return verticalAngle;
    }

    //
    @Override
    public final void setVerticalAngle(double angle) {
        this.verticalAngle = angle;
    }

    //
    @Override
    public final double getX() {
        return getRadiusHorizontalProjection() * Math.cos(horizontalAngle);
    }

    //preserves y and z
    @Override
    public final void setX(double x) {
        setXY(x, getY());
    }

    //
    @Override
    public final double getY() {
        return getRadiusHorizontalProjection() * Math.sin(horizontalAngle);
    }

    //preserves x and z
    @Override
    public final void setY(double y) {
        setXY(getX(), y);
    }

    //
    @Override
    public double getZ() {
        return radius * Math.sin(verticalAngle);
    }

    //preserves x and y; horizontal angle doesn't change
    @Override
    public final void setZ(double z) {
        double
                x = getX(),
                y = getY(),
                radiusHorizontalProjection = Math.hypot(x, y);
        setRadius(Math.hypot(radiusHorizontalProjection, z));
        setVerticalAngle(MathUtils.getAngle(radiusHorizontalProjection, z));
    }

    private double getRadiusHorizontalProjection() {
        return radius * Math.cos(verticalAngle);
    }

    private void setXY(double x, double y) {
        double
                z = getZ(),
                radiusHorizontalProjection = Math.hypot(x, y);
        setPolar(
                Math.hypot(radiusHorizontalProjection, z),
                MathUtils.getAngle(x, y),
                MathUtils.getAngle(radiusHorizontalProjection, z));
    }
}