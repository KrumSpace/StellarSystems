package planetTest.planetModel;

import org.jetbrains.annotations.Nullable;

//
public class PlanetContainer {
    private @Nullable Planet planet;

    //
    public PlanetContainer() {}

    //
    public final @Nullable Planet getPlanet() {
        return planet;
    }

    //
    public final void setPlanet(@Nullable Planet planet) {
        this.planet = planet;
    }
}