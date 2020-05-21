package AOP.mainPackage;

import AOP.proxy.annotations.Log;

public class KinopoiskImpl implements KinopoiskInterface, TestInterface {

    @Override
    @Log
    public void runMovie(String movie) {
        System.out.println("Run method runMovie: " + movie);
    }

    @Override
    @Log
    public void runMovie() {
        System.out.println("Run method runMovie");
    }

    @Override
    public void stopMovie() {
        System.out.println("Run method stopMovie");
    }

    @Override
    public void nextMovie() {
        System.out.println("Run method nextMovie");
    }

    @Override
    @Log
    public void prevMovie() {
        System.out.println("Run method prevMovie");
    }

    @Override
    public String toString() {
        return "KinopoiskImpl{}";
    }

    @Override
    public void testMethod() {

    }
}
