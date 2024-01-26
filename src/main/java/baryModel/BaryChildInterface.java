package baryModel;

import org.jetbrains.annotations.NotNull;

//
public interface BaryChildInterface {
    //
    @NotNull BaryObjectContainerInterface getParent();

    //
    void setParent(@NotNull BaryObjectContainerInterface parent);

    //
    void moveLevelUp() throws RootParentException;

    //
    class RootParentException extends Exception {
        public RootParentException() {
            super("Parent is a root member, unable to move up!");
        }
    }
}