package playerTest;

import graphicalTestAbstraction.GraphicalTest;
import playerTest.graphics.TestWindow;

//
public class PlayerTest extends GraphicalTest {
    //
    public static void main(String[] args) {
        new PlayerTest();
    }

    //
    public PlayerTest() {
        super(new TestWindow());
    }
}