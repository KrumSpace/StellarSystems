package baryModel.basicModels;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import utils.MathUtils;
import utils.Representable;
import coordinates.ConvertibleCoordinates;
import kinetics.Location;
import kinetics.Velocity;
import kinetics.Acceleration;
import kinetics.MassiveKineticObject;
import baryModel.exceptions.TopLevelObjectException;
import baryModel.BaryChildInterface;
import baryModel.BaryObjectContainerInterface;
import baryModel.systems.AbstractBarySystem;

//
public abstract class BasicBaryObject extends MassiveKineticObject
        implements BaryChildInterface, Representable {
    private static final double GRAVITATIONAL_CONSTANT = 10000; //6.67 * Math.pow(10, -13);
    private @Nullable BaryObjectContainerInterface parent;

    //
    BasicBaryObject(@Nullable BaryObjectContainerInterface parent,
                    @Nullable Location location,
                    @Nullable Velocity velocity,
                    @Nullable Acceleration acceleration) {
        super(location, velocity, acceleration);
        this.parent = parent;
    }

    //
    @Override
    public final @NotNull BaryObjectContainerInterface getParent() throws TopLevelObjectException {
        if (parent == null) {
            throw new TopLevelObjectException();
        } else {
            return parent;
        }
    }

    //setting parent to null is possible, but not recommended
    @Override
    public void setParent(@Nullable BaryObjectContainerInterface parent) throws TopLevelObjectException {
        this.parent = parent;
    }

    @Override
    public void calculate(double time) {
        addAccelerationComponents(time);
        super.calculate(time);
    }

    @SuppressWarnings("unused")
    public void addAccelerationComponents(double time) {
        getAcceleration().addComponent(getGravitationalAcceleration());
        //override to add other acceleration components here, such as engines or user input
    }

    private @NotNull Acceleration getGravitationalAcceleration() {
        if (parent instanceof @NotNull AbstractBarySystem parentSystem) {
            double parentMass = parentSystem.getMassWithout(this);
            @NotNull Location parentLocation = parentSystem.getBaryCenterWithout(this);
            @NotNull ConvertibleCoordinates relativeParentLocation = getDistanceTo(parentLocation);
            double
                    dx = relativeParentLocation.getX(),
                    dy = relativeParentLocation.getY(),
                    dz = relativeParentLocation.getZ(),
                    distanceSquared = Math.pow(relativeParentLocation.getRadius(), 2);
            return new Acceleration(
                    GRAVITATIONAL_CONSTANT * parentMass / distanceSquared,
                    MathUtils.getAngle(dx, dy),
                    MathUtils.getAngle(Math.hypot(dx, dy), dz));
        } else {
            return new Acceleration(0, 0, 0);
        }
    }
}