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
import baryModel.exceptions.ObjectRemovedException;
import baryModel.BaryChildInterface;
import baryModel.InteractiveObjectInterface;
import baryModel.BaryObjectContainerInterface;
import baryModel.UniverseConstants;
import baryModel.systems.AbstractBarySystem;
import baryModel.BaryUniverse;

//
public abstract class BasicBaryObject extends MassiveKineticObject
        implements BaryChildInterface, InteractiveObjectInterface, Representable {
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

    //override this to add more acceleration components, such as engines or user input
    @SuppressWarnings("unused")
    public void addAccelerationComponents(double time) {
        getAcceleration().addComponent(getGravitationalAcceleration());
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
                    UniverseConstants.GRAVITATIONAL_CONSTANT * parentMass / distanceSquared,
                    MathUtils.getAngle(dx, dy),
                    MathUtils.getAngle(Math.hypot(dx, dy), dz));
        } else {
            return new Acceleration(0, 0, 0);
        }
    }

    //
    @Override
    public void checkNeighbors() throws TopLevelObjectException, ObjectRemovedException {
        checkNeighbors(getParent().getObjects());
    }

    //exits current system
    @Override
    public void exitSystem() throws TopLevelObjectException {
        @Nullable BaryObjectContainerInterface parent = getParent();
        if (parent instanceof BaryUniverse) {
            throw new TopLevelObjectException(); //unable to exit top-level system
        } else {
            //find new parent
            if (parent instanceof @NotNull BaryChildInterface parentAsChild) {
                @NotNull BaryObjectContainerInterface grandparent = parentAsChild.getParent();

                //calculate and set new coordinates
                if (parent instanceof @NotNull AbstractBarySystem parentSystem) {
                    //sets new location and velocity
                    getLocation().increaseCartesian(parentSystem.getLocation());
                    getVelocity().increaseCartesian(parentSystem.getVelocity());

                    //remove from old list
                    parent.removeObject(this);

                    //recalculates barycenter of the old system, speed doesn't change
                    parentSystem.updateBaryCenter(true, false);

                } else {
                    throw new RuntimeException("Parent is not a system, unable to get coordinates!");
                }

                //set new parent
                setParent(grandparent);

                //add to new list
                grandparent.addObject(this);
            } else {
                throw new RuntimeException("Parent is not a child, therefore does not not have a parent. Unable to move an object up!");
            }
        }
    }
}