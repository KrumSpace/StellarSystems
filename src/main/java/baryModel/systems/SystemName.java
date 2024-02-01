package baryModel.systems;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

//for storing and generating names of the systems
record SystemName(@NotNull String name) {
    private static final @NotNull String SYSTEM_NAME_PREFIX = "System-";
    private static int system_name_counter = 0;

    //
    SystemName(@Nullable String name) {
        this.name = Objects.requireNonNullElse(name, getNewSystemName());
    }

    private static @NotNull String getNewSystemName() {
        system_name_counter++;
        return SYSTEM_NAME_PREFIX + system_name_counter;
    }
}