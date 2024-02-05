package demo.graphics.panels;

import org.jetbrains.annotations.NotNull;

import baryModel.BaryUniverse;
import baryGraphics.Observer;
import baryGraphics.panels.UniverseDrawPanel;

//
public final class CentralPanel extends UniverseDrawPanel {
    //
    public CentralPanel(@NotNull BaryUniverse universe, @NotNull Observer observer) {
        super(universe, observer);
    }
}