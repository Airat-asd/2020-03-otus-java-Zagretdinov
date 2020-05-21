package AOP.mainPackage;

import AOP.proxy.annotations.Log;

public class OkkoImpl implements OkkoInterface {

    @Override
    public void runOkkoMovie(String param) {
        System.out.println("Run method runOkkoMovie: " + param);
    }

    @Override
    @Log
    public void stopOkkoMovie() {
        System.out.println("Run method stopOkkoMovie");
    }

    @Override
    @Log
    public void nextOkkoMovie() {
        System.out.println("Run method nextOkkoMovie");
    }

    @Override
    public void prevOkkoMovie() {
        System.out.println("Run method prevOkkoMovie");
    }
}
