package AOP.mainPackage;

import AOP.proxy.Ioc;

import static AOP.proxy.Ioc.createMyClass;

public class MainClass {

    public static void main(String[] args) {
        System.out.println("--------------" + KinopoiskImpl.class + "-------------------");
        KinopoiskInterface kinopoiskImpl = Ioc.<KinopoiskInterface>createMyClass(new KinopoiskImpl(), KinopoiskInterface.class);
        kinopoiskImpl.runMovie("Gone in Sixty Seconds");
        kinopoiskImpl.stopMovie();
        kinopoiskImpl.nextMovie();
        kinopoiskImpl.prevMovie();
        System.out.println("--------------" + OkkoImpl.class + "-------------------");
        OkkoInterface okkoImpl = Ioc.<OkkoInterface>createMyClass(new OkkoImpl(), OkkoInterface.class);
        okkoImpl.runOkkoMovie("Matrix");
        okkoImpl.stopOkkoMovie();
        okkoImpl.nextOkkoMovie();
        okkoImpl.prevOkkoMovie();
    }
}




