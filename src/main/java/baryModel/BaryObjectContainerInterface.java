package baryModel;

import java.util.List;

import org.jetbrains.annotations.NotNull;

//
public interface BaryObjectContainerInterface {
    //
    @NotNull List<@NotNull BaryObject> getObjects();

    //
    void addObject(@NotNull BaryObject object);

    //
    void removeObject(@NotNull BaryObject object);

    //consolidates systems
    void checkMeaninglessSystems() throws ObjectRemovedException;

    //checks all child relations
    default void checkChildNeighbors() {
        @NotNull List<@NotNull BaryObject> objects = getObjects();
        for (int i = 0; i < objects.size(); i++) {
            @NotNull BaryObject object = objects.get(i);
            if (object instanceof BaryObjectContainerInterface) { //goes deeper if there are more children
                ((BaryObjectContainerInterface) object).checkChildNeighbors();
            }
            try {
                object.checkNeighbors();
            } catch (ObjectRemovedException exception) {
                break; //crude solution; TODO: improve
            }
        }
    }
}