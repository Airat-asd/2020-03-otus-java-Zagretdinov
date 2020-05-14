package AOP.mainPackage;

import AOP.proxy.annotations.Log;

public class OkkoImpl implements OkkoInterface {

    @Override
    public void runOkkoMovie(String param) {
        System.out.println("run movie: " + param);
    }

    @Override
    @Log
    public void stopOkkoMovie() {
        System.out.println("Movie stop");
    }

    @Override
    @Log
    public void nextOkkoMovie() {
        System.out.println("Next movie");
    }

    @Override
    public void prevOkkoMovie() {
        System.out.println("Previous movie");
    }
}
