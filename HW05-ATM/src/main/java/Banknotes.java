public enum Banknotes {
    FIFTY_50(50),
    HUNDRED_100(100),
    FIVEHUNDRED_500(500),
    THOUSAND_1000(1000),
    TWOTHOUSAND_2000(2000),
    FIVETHOUSAND_5000(5000);
    private int banknote;

    Banknotes(int banknote) {
        this.banknote = banknote;
    }

    public int getBanknote() {
        return banknote;
    }
}
