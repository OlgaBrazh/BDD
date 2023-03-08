import lombok.val;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BDDTest {

    @BeforeEach
    void openPage() {open("http://localhost:9999");}

    @AfterEach
    void resetBalance() {
        open("http://localhost:9999");
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        val dashboardPage = verificationPage.validVerify(verificationCode);
        DataHelper.resetCardsBalance();
    }


    @Test
    public void shouldTransfer5000FromCard1ToCard2() {
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        val dashboardPage = verificationPage.validVerify(verificationCode);
        val transferPage = dashboardPage.goToTransferPageForCard(DataHelper.getCardNumber2());
        transferPage.transferAmountFromThisCard(5000, DataHelper.getCardNumber1());
        assertEquals(15000, dashboardPage.getCardBalance(DataHelper.getCardNumber2()));
        assertEquals(5000, dashboardPage.getCardBalance(DataHelper.getCardNumber1()));
    }

    @Test
    public void shouldTransfer10000FromCard2ToCard1() {
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        val dashboardPage = verificationPage.validVerify(verificationCode);
        val transferPage = dashboardPage.goToTransferPageForCard(DataHelper.getCardNumber1());
        transferPage.transferAmountFromThisCard(10000, DataHelper.getCardNumber2());
        assertEquals(20000, dashboardPage.getCardBalance(DataHelper.getCardNumber1()));
        assertEquals(0, dashboardPage.getCardBalance(DataHelper.getCardNumber2()));
    }

}
