package demo;

import org.jetbrains.annotations.NotNull;

import baryModel.BaryUniverse;

import graphicalTestAbstraction.GraphicalBaryTest;
import demo.graphics.DemoWindow;

//
public class Demo extends GraphicalBaryTest {
    //
    public static void main(String[] args) {
        new Demo(new TestUniverse());
    }

    private Demo(@NotNull BaryUniverse universe) {
        super(universe, new DemoWindow(universe));
    }
}