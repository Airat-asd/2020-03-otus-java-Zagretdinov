package Banknotes;

/**
 * @author Ayrat Zagretdinov
 * created on 03.06.2020
 */
public enum BanknotesEnum {
        FIFTY_50(50),
        HUNDRED_100(100),
        FIVEHUNDRED_500(500),
        THOUSAND_1000(1000),
        TWOTHOUSAND_2000(2000),
        FIVETHOUSAND_5000(5000);
        private int banknote;

        BanknotesEnum(int banknote) {
            this.banknote = banknote;
        }

        public int getBanknote() {
            return banknote;
        }
    }
