package baryGraphics;

import org.jetbrains.annotations.NotNull;

//
public class Observer {
    private static final double @NotNull [] DEFAULT_LOCATION = new double [] {0, 0};
    private static final double DEFAULT_SCALE = 25;
    private final double @NotNull [] location; //actual location
    private double scale;

    //
    public Observer(double @NotNull [] location, double scale) {
        this.location = location;
        this.scale = scale;
    }

    //default settings
    public Observer() {
        this(DEFAULT_LOCATION, DEFAULT_SCALE);
    }

    //gets actual location
    public double @NotNull [] getLocation() {
        return location;
    }

    //
    public void shiftLocation(double dx, double dy) {
        location[0] += dx;
        location[1] += dy;
    }

    //
    public double getScale() {
        return scale;
    }

    //accepts from 0 (included) to 1 (excluded), otherwise throws an exception
    public void zoomIn(double rate) throws IllegalArgumentException {
        checkZoomRateApplicability(rate);
        zoom(-rate);
    }

    //accepts from 0 (included) to 1 (excluded), otherwise throws an exception
    public void zoomOut(double rate) throws IllegalArgumentException {
        checkZoomRateApplicability(rate);
        zoom(rate);
    }

    //checks range
    private void checkZoomRateApplicability(double rate) throws IllegalArgumentException {
        if (rate < 0 || rate >= 1) {
            throw new IllegalArgumentException();
        }
    }

    //accepts positive and negative
    private void zoom(double rate) {
        scale = scale * (1 + rate);
    }
}