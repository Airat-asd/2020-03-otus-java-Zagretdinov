package AOP.mainPackage;

import AOP.proxy.annotations.Log;

public class KinopoiskImpl implements KinopoiskInterface {

    @Override
    public void runMovie(String movie) {
        System.out.println("run movie: " + movie);
    }

    @Override
    public void stopMovie() {
        System.out.println("Movie stop");
    }

    @Override
    public String toString() {
        return "KinopoiskImpl{}";
    }
}
