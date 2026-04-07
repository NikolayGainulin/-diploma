package ru.iteco.fmhandroid.ui.tests;

import static ru.iteco.fmhandroid.ui.data.Data.urlPrivacyPolicy;
import static ru.iteco.fmhandroid.ui.data.Data.urlTermsOfUse;
import static ru.iteco.fmhandroid.ui.data.DataHelper.generateScreenshotName;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.qameta.allure.android.rules.ScreenshotRule;
import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.junit4.DisplayName;
import ru.iteco.fmhandroid.ui.AppActivity;
import ru.iteco.fmhandroid.ui.pages.AboutPage;
import ru.iteco.fmhandroid.ui.pages.AuthorizationPage;
import ru.iteco.fmhandroid.ui.pages.MainPage;

@LargeTest
@RunWith(AllureAndroidJUnit4.class)
@DisplayName("Раздел \"О приложении\"")
public class AboutPageTest {

    AuthorizationPage authorizationPage = new AuthorizationPage();
    AboutPage aboutPage = new AboutPage();
    MainPage mainPage = new MainPage();

    @Before
    public void setUp(){
        authorizationPage.checkLogInAndLogInIfNot();
        mainPage.goToAboutPage();
    }

    @Rule
    public ActivityScenarioRule<AppActivity> activityScenarioRule = new ActivityScenarioRule<>(AppActivity.class);

    @Rule
    public ScreenshotRule screenshotRule = new ScreenshotRule(ScreenshotRule.Mode.FAILURE, generateScreenshotName("Failed"));

    @Test
    @DisplayName("Test-ID22. Переход по ссылке \"Политика конфиденциальности\" в разделе \"О приложении\" ")
    public void goToUrlPrivacyPolicyTest() {
        aboutPage.isPageExists(urlPrivacyPolicy);
    }

    @Test
    @DisplayName("Test-ID23. Переход по ссылке \"Пользовательское соглашение\" в разделе \"О приложении\"")
    public void goToUrlTermsOfUseTest() {
        aboutPage.isPageExists(urlTermsOfUse);
    }

}