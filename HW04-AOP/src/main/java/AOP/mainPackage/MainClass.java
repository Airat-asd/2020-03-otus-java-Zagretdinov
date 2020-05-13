package AOP.mainPackage;

import AOP.proxy.Ioc;

public class MainClass {
    public static void main(String[] args) {
        KinopoiskInterface kinopoiskImpl = Ioc.createMyClass();
        System.out.println("test 3");
        kinopoiskImpl.runMovie("Gone in Sixty Seconds");
        System.out.println("test 4");
        kinopoiskImpl.stopMovie();
        System.out.println("test 5");
    }
}




