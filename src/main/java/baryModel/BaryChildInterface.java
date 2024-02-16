package baryModel;

import org.jetbrains.annotations.NotNull;

import baryModel.exceptions.*;
import baryModel.systems.BarySystem;

//
public interface BaryChildInterface {
    //
    @NotNull BaryObjectContainerInterface getParent() throws TopLevelObjectException;

    //
    void setParent(@NotNull BaryObjectContainerInterface parent) throws TopLevelObjectException;

    //exits from this system into its parent
    void exitSystem() throws TopLevelObjectException;

    //enters neighboring system, has to be of the same parent!
    //TODO: finish this
    void enterNeighboringSystem(@NotNull BarySystem neighbor) throws DifferentParentException;
}