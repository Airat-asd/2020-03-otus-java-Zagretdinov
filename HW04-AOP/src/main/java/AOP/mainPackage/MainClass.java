package AOP.mainPackage;

import AOP.proxy.Ioc;

/**
 * Для демонстрации работы "прокси" класса Ioc реализованны два класса KinopoiskImpl и OkkoImpl,
 * каждый из которых имплементируем свои интерфесы.
 * @author Ayrat Zagretdinov
 * created on 15.05.2020
 */

public class MainClass {

    public static void main(String[] args) {
        System.out.println("--------------" + KinopoiskImpl.class + "-------------------");
        //для вызова метода createMyClass обязательно указывать generic интерфейса
        KinopoiskInterface kinopoiskImpl = Ioc.<KinopoiskInterface>createMyClass(new KinopoiskImpl());
        kinopoiskImpl.runMovie("Gone in Sixty Seconds");
        kinopoiskImpl.runMovie();
        kinopoiskImpl.stopMovie();
        kinopoiskImpl.nextMovie();
        kinopoiskImpl.prevMovie();
        System.out.println("--------------" + OkkoImpl.class + "-------------------");
        OkkoInterface okkoImpl = Ioc.<OkkoInterface>createMyClass(new OkkoImpl());
        okkoImpl.runOkkoMovie("Matrix");
        okkoImpl.stopOkkoMovie();
        okkoImpl.nextOkkoMovie();
        okkoImpl.prevOkkoMovie();
    }
}




