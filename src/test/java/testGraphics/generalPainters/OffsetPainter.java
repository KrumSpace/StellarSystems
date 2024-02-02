package testGraphics.generalPainters;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

//
public class OffsetPainter {
    private static final int @NotNull [] DEFAULT_DRAW_OFFSET = new int [] {400, 400};
    private int @NotNull [] drawOffset;

    //
    public OffsetPainter(int @Nullable [] drawOffset) {
        this.drawOffset = Objects.requireNonNullElse(drawOffset, DEFAULT_DRAW_OFFSET);
    }

    //
    public final int @NotNull [] getDrawOffset() {
        return drawOffset;
    }

    //
    public final void setDrawOffset(int @Nullable [] drawOffset) {
        this.drawOffset = Objects.requireNonNullElse(drawOffset, DEFAULT_DRAW_OFFSET);
    }
}