package baryGraphics.painters;

import java.util.Map;
import java.util.HashMap;
import java.awt.Graphics;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import baryModel.BaryObject;
import baryModel.exceptions.UnrecognizedBaryObjectTypeException;
import baryModel.simpleObjects.BarySimpleObject;
import baryModel.systems.AbstractBarySystem;

//
final class ObjectTypeSwitch implements ObjectPainterInterface<BaryObject> {
    private static final @NotNull Map<@NotNull Class<? extends @NotNull BaryObject>, @NotNull FunctionalPainterInterface>
            PAINTER_MAP = new HashMap<>();
    static {
        PAINTER_MAP.put(BarySimpleObject.class, (g, painters, object, location) ->
                painters.getSimpleObjectPainter().paint(g, (BarySimpleObject) object, location));
        PAINTER_MAP.put(AbstractBarySystem.class, (g, painters, object, location) ->
                painters.getSystemPainter().paint(g, (AbstractBarySystem) object, location));
    }
    private final @NotNull PainterContainer painters;

    //
    ObjectTypeSwitch(@NotNull PainterContainer painters) {
        this.painters = painters;
    }

    //
    @Override
    public void paint(@NotNull Graphics g,
                      @NotNull BaryObject object,
                      double @NotNull [] absoluteLocation) throws UnrecognizedBaryObjectTypeException {
        @NotNull Class<? extends @NotNull BaryObject> type = object.getClass();
        @Nullable FunctionalPainterInterface painter = getPainterForClass(type);
        if (painter == null) {
            throw new UnrecognizedBaryObjectTypeException();
        } else {
            painter.accept(g, painters, object, absoluteLocation);
        }
    }

    @SuppressWarnings("unchecked")
    private @Nullable FunctionalPainterInterface getPainterForClass(@NotNull Class<? extends @NotNull BaryObject> type) {
        while (type != null) {
            @Nullable FunctionalPainterInterface painter = PAINTER_MAP.get(type);
            if (painter != null) {
                return painter;
            }
            type = (Class<? extends BaryObject>) type.getSuperclass(); // Move up the class hierarchy
        }
        return null; // No matching painter found
    }

    @FunctionalInterface
    private interface FunctionalPainterInterface {
        void accept(@NotNull Graphics g, @NotNull PainterContainer painters,
                    @NotNull BaryObject obj, double @NotNull [] absoluteLocation);
    }
}