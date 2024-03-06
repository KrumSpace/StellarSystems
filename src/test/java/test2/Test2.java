package test2;

import org.jetbrains.annotations.NotNull;

import baryModel.BaryUniverse;

import graphicalTestAbstraction.GraphicalBaryTest;
import test2.graphics.TestWindow;

//Testing testing
public class Test2 extends GraphicalBaryTest {

    //main
    public static void main(String[] args) {
        new Test2(new TestUniverse());
    }

    private Test2(@NotNull BaryUniverse universe) {
        super(universe, new TestWindow(universe));
    }
}