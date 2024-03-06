package planetTest;

import graphicalTestAbstraction.GraphicalTest;
import planetTest.graphics.TestWindow;

//Testing testing
public class PlanetTest extends GraphicalTest {

    //main
    public static void main(String[] args) {
        new PlanetTest();
    }

    private PlanetTest() {
        super(new TestWindow());
    }
}