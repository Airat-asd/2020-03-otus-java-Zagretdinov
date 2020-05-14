package AOP.mainPackage;

import AOP.proxy.annotations.Log;

public interface KinopoiskInterface {

    @Log
    void runMovie(String param);

    void stopMovie();
}

