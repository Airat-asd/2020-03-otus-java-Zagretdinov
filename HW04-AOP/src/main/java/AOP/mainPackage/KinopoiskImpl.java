package AOP.mainPackage;

import AOP.proxy.annotations.Log;

public class KinopoiskImpl implements KinopoiskInterface, TestInterface {

    @Override
    @Log
    public void runMovie(String movie) {
        System.out.println("run movie: " + movie);
    }

    @Override
    public void stopMovie() {
        System.out.println("Movie stop");
    }

    @Override
    public void nextMovie() {
        System.out.println("Next movie");
    }

    @Override
    @Log
    public void prevMovie() {
        System.out.println("Previous movie");
    }

    @Override
    public String toString() {
        return "KinopoiskImpl{}";
    }

    @Override
    public void testMethod() {

    }
}
