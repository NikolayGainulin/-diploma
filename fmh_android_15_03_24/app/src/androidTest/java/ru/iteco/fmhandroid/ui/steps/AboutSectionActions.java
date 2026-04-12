package ru.iteco.fmhandroid.ui.steps;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static ru.iteco.fmhandroid.ui.activity.TestUtilities.waitForViewPresence;

import io.qameta.allure.kotlin.Allure;
import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.ui.page.AboutScreenElements;

public class AboutSectionActions {

    AboutScreenElements aboutScreen = new AboutScreenElements();

    public void verifyAboutSectionContentIsComplete() {
        Allure.step("Проверка, что в блоке \"О приложении\" полный контент");
        waitForViewPresence(withId(R.id.about_company_info_label_text_view), 5000);
        aboutScreen.appLogo.check(matches(isDisplayed()));
        aboutScreen.navigateBackButton.check(matches(isDisplayed()));
        aboutScreen.versionLabel.check(matches(isDisplayed()));
        aboutScreen.versionValue.check(matches(isDisplayed()));
        aboutScreen.privacyPolicyLabel.check(matches(isDisplayed()));
        aboutScreen.termsOfUseLabel.check(matches(isDisplayed()));
        aboutScreen.companyInfoText.check(matches(isDisplayed()));
        aboutScreen.privacyPolicyLink.check(matches(isDisplayed()));
        aboutScreen.termsOfUseLink.check(matches(isDisplayed()));
    }

    public void navigateBackToHome() {
        Allure.step("Назад на Главную страницу");
        aboutScreen.navigateBackButton.perform(click());
    }

    public void openPrivacyPolicy() {
        Allure.step("Переход к политике конфиденциальности");
        aboutScreen.privacyPolicyLink.perform(click());
    }

    public void openTermsOfUse() {
        Allure.step("Переход к пользовательскому соглашению");
        aboutScreen.termsOfUseLink.perform(click());
    }
}