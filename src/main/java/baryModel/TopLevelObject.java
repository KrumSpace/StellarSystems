package baryModel;

import java.awt.Color;

import baryModel.basicModels.BasicBaryObject;
import baryModel.basicModels.InfluentialObject;
import kinetics.Location;
import kinetics.Velocity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import baryModel.exceptions.TopLevelObjectException;
import baryModel.basicModels.NonInfluentialObject;
import baryModel.simpleObjects.PhysicalBaryObject;
import baryModel.systems.AbstractBarySystem;

/**
 * Top-level bound object; a root system.
 * Needed for proper SOI calculations, and SOI for this object needs to be infinite.
 */
public abstract class TopLevelObject extends AbstractBarySystem {
    //
    public TopLevelObject(@NotNull Color color) {
        super(null, null, null, color);
    }

    //
    @Override
    public final void setParent(@Nullable BaryObjectContainerInterface parent) throws TopLevelObjectException {
        throw new TopLevelObjectException();
    }

    //not possible to exit the top-level system
    @Override
    public final void exitSystem() throws TopLevelObjectException {
        throw new TopLevelObjectException();
    }

    //
    @Override
    public final double getInfluenceRadius() throws TopLevelObjectException {
        throw new TopLevelObjectException();
    }

    @Override
    public final void updateCenter() {
        @NotNull Location baryCenter = getBaryCenter();
        updateMemberCenters(baryCenter);
        resetMomentum();
    }

    private void resetMomentum() {
        double
                pxTotal = 0,
                pyTotal = 0,
                pzTotal = 0;
        for (@NotNull BasicBaryObject child : getObjects()) { //calculates total momentum
            if (child instanceof @NotNull InfluentialObject influentialChild) {
                @NotNull Velocity childVelocity = influentialChild.getVelocity();
                double childMass = influentialChild.getMass();
                pxTotal += childMass * childVelocity.getX();
                pyTotal += childMass * childVelocity.getY();
                pzTotal += childMass * childVelocity.getZ();
            }
        }
        double  //calculates velocity change
                totalMass = getMass(),
                vx = pxTotal / totalMass,
                vy = pyTotal / totalMass,
                vz = pzTotal / totalMass;
        for (@NotNull BasicBaryObject child : getObjects()) { //applies velocity change to children
            child.getVelocity().increaseCartesian(
                    -vx,
                    -vy,
                    -vz);
        }
    }

    //this should not get called
    @Override
    public final void interactWith(@NotNull NonInfluentialObject object) {
        throw new RuntimeException(new TopLevelObjectException());
    }

    //this should not get called
    @Override
    public final void interactWith(@NotNull PhysicalBaryObject object) {
        throw new RuntimeException(new TopLevelObjectException());
    }

    //this should not get called
    @Override
    public final void interactWith(@NotNull AbstractBarySystem object) {
        throw new RuntimeException(new TopLevelObjectException());
    }
}