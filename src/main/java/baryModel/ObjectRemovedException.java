package baryModel;

//
public final class ObjectRemovedException extends Exception {
    //
    public ObjectRemovedException() {
        super("Object removed. Gotta break the loop.");
    }
}