package testGraphics;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

//
public class OffsetPainter {
    private static final int @NotNull [] DEFAULT_DRAW_OFFSET = new int [] {300, 300};
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