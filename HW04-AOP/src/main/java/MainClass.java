public class MainClass {
    public static void main(String[] args) {
        KinopoiskInterface kinopoiskImpl = Ioc.createMyClass();
        kinopoiskImpl.runMovie("Gone in Sixty Seconds");
        kinopoiskImpl.stopMovie();
    }
}



