package planetTest;

import java.awt.Color;

import org.jetbrains.annotations.NotNull;

import graphicalTestAbstraction.GraphicalTest;
import planetTest.planetModel.Planet;
import planetTest.planetModel.PlanetContainer;
import planetTest.graphics.TestWindow;

//Testing testing
public class PlanetTest extends GraphicalTest {
    private static final @NotNull PlanetContainer PLANET_CONTAINER = new PlanetContainer();

    //main
    public static void main(String[] args) {
        new PlanetTest();
        PLANET_CONTAINER.setPlanet(new Planet(10, 100, Color.green));
    }

    private PlanetTest() {
        super(new TestWindow(PLANET_CONTAINER));
    }
}