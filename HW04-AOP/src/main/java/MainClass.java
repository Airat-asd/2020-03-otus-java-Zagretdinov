public class MainClass {
    public static void main(String[] args) throws NoSuchMethodException {
        KinopoiskInterface kinopoiskImpl = Ioc.createMyClass();
        kinopoiskImpl.runMovie("Gone in Sixty Seconds");
        kinopoiskImpl.stopMovie();
    }
}



