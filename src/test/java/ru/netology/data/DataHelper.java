package ru.netology.data;

import lombok.Value;
import lombok.val;
import ru.netology.page.DashboardPage;

public class DataHelper {

    private DataHelper() {
    }

    @Value
    public static class AuthInfo {
        private String login;
        private String password;
    }

    public static AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    public static AuthInfo getOtherAuthInfo(AuthInfo original) {
        return new AuthInfo("petya", "123qwerty");
    }

    @Value
    public static class VerificationCode {
        private String code;
    }

    public static VerificationCode getVerificationCodeFor(AuthInfo authInfo) {
        return new VerificationCode("12345");
    }

    @Value
    public static class CardNumber {
        private String cardNumber;
    }

    public static String getCardNumber1() {
        val cardNumber = new CardNumber("5559 0000 0000 0001");
        return cardNumber.getCardNumber();
    }

    public static String getCardNumber2() {
        val cardNumber = new CardNumber("5559 0000 0000 0002");
        return cardNumber.getCardNumber();
    }

    public static String getLastFourDigits(String cardNumber) {
        return cardNumber.substring((cardNumber.length() - 4));
    }

    public static DashboardPage resetCardsBalance() {
        val dashboardPage = new DashboardPage();
        val currentBalanceCard1 = dashboardPage.getCardBalance(DataHelper.getCardNumber1());
        val currentBalanceCard2 = dashboardPage.getCardBalance(DataHelper.getCardNumber2());
        if (currentBalanceCard1 > 10000) {
            val amountRequired = currentBalanceCard1 - 10000;
            val transferPage = dashboardPage.goToTransferPageForCard(DataHelper.getCardNumber2());
            transferPage.transferAmountFromThisCard(amountRequired, DataHelper.getCardNumber1());
        }
        if (currentBalanceCard1 < 10000) {
            val amountRequired = currentBalanceCard2 - 10000;
            val transferPage = dashboardPage.goToTransferPageForCard(DataHelper.getCardNumber1());
            transferPage.transferAmountFromThisCard(amountRequired, DataHelper.getCardNumber2());
        }
        return new DashboardPage();
    }

}
