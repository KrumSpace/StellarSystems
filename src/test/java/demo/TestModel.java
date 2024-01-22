package demo;

import java.awt.Color;

import static baryModel.BaryLocation.BaryLocationGenerator.newBaryLocationFromCartesian;
import static baryModel.BaryLocation.BaryLocationGenerator.newBaryLocationFromRadial;

import baryModel.BaryObject;
import baryModel.BarySimpleObject;
import baryModel.BarySystem;
import baryModel.BaryUniverse;
import baryModel.BaryModel;
import org.jetbrains.annotations.NotNull;

//
class TestModel extends BaryModel {
    //
    TestModel() {
        super();
        @NotNull BaryUniverse universe = getUniverse();
        @NotNull BaryObject independentObject = new BarySimpleObject(
                null,
                newBaryLocationFromRadial(500, Math.PI, 0.3),
                Color.CYAN);
        universe.addObject(independentObject);

        @NotNull BaryObject
                dependentObject1 = new BarySimpleObject(
                        null,
                        newBaryLocationFromRadial(70, 0, 2),
                        Color.YELLOW),
                dependentObject2 = new BarySimpleObject(
                        null,
                        newBaryLocationFromRadial(100, Math.PI * 2 / 3, 1.5),
                        Color.ORANGE);
        @NotNull BarySystem system = new BarySystem(
                null,
                newBaryLocationFromCartesian(220, 0, 0.6),
                Color.MAGENTA);
        system.addObject(dependentObject1);
        system.addObject(dependentObject2);
        universe.addObject(system);
    }
}