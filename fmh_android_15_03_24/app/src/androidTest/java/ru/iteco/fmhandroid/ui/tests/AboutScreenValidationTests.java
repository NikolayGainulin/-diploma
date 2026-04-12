package ru.iteco.fmhandroid.ui.tests;

import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasData;

import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.qameta.allure.android.rules.ScreenshotRule;
import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.Feature;
import io.qameta.allure.kotlin.junit4.DisplayName;

import ru.iteco.fmhandroid.ui.AppActivity;
import ru.iteco.fmhandroid.ui.steps.LoadingScreenSteps;
import ru.iteco.fmhandroid.ui.steps.AboutSectionActions;
import ru.iteco.fmhandroid.ui.steps.LoginStepActions;
import ru.iteco.fmhandroid.ui.steps.HomeScreenActions;

@RunWith(AllureAndroidJUnit4.class)
public class AboutScreenValidationTests {

    LoadingScreenSteps loadingSteps = new LoadingScreenSteps();
    AboutSectionActions aboutActions = new AboutSectionActions();
    LoginStepActions loginActions = new LoginStepActions();
    HomeScreenActions homeActions = new HomeScreenActions();

    @Rule
    public ActivityScenarioRule<AppActivity> activityScenarioRule =
            new ActivityScenarioRule<>(AppActivity.class);

    @Rule
    public ScreenshotRule screenshotRule = new ScreenshotRule(ScreenshotRule.Mode.FAILURE,
            String.valueOf(System.currentTimeMillis()));

    @Before
    public void initializeApplication() {
        loadingSteps.waitForApplicationLoad();
        try {
            homeActions.waitForHomeScreenLoad();
        } catch (Exception e) {
            loginActions.performValidLogin();
            homeActions.waitForHomeScreenLoad();
        }
    }

    /* Тест проверяет, что страница "О приложении" содержит все необходимые элементы. */
    @Test
    @Feature(value = "Тесты по странице \"О приложении\"")
    @DisplayName("Наличие всех элементов страницы")
    public void verifyAllComponentsPresentOnAboutScreen() {
        homeActions.navigateToAboutSection();
        aboutActions.verifyAboutSectionContentIsComplete();
    }

    /* Тест проверяет функциональность кнопки возврата на главную страницу с страницы "О приложении" */
    @Test
    @Feature(value = "Тесты по странице \"О приложении\"")
    @DisplayName("Возвращение на главную")
    public void verifyNavigationBackToMainScreenFromAboutPage() {
        homeActions.navigateToAboutSection();
        aboutActions.navigateBackToHome();
        homeActions.waitForHomeScreenLoad();
        homeActions.verifyHomeScreenContentIsComplete();
    }

    /* Тест предназначен для проверки возможности перехода на страницу "О приложении" из страницы "Новости". Однако кнопка перехода не активна, и перейти невозможно. */
    @Test
    @Feature(value = "Тесты по странице \"О приложении\"")
    @DisplayName("Переход к странице \"О приложении\", находясь на странице \"Новости\"")
    public void checkAboutPageAccessibilityFromNewsSection() {
        homeActions.navigateToNewsSection();
        homeActions.navigateToAboutSection();
        aboutActions.verifyAboutSectionContentIsComplete();
    }

    /* Тест проверяет переход к политике конфиденциальности по кликабельной ссылке, хотя страница не загружается. */
    @Test
    @Feature(value = "Тесты по странице \"О приложении\"")
    @DisplayName("Переход к политике конфиденциальности по ссылке. Ссылка кликабельна, но страница фактически не загружается")
    public void validatePrivacyPolicyLinkClickability() {
        homeActions.navigateToAboutSection();
        Intents.init();
        aboutActions.openPrivacyPolicy();
        intended(hasData("https://vhospice.org/#/privacy-policy"));
        Intents.release();
    }

    /* Тест проверяет переход к пользовательскому соглашению по кликабельной ссылке, но страница фактически не загружается */
    @Test
    @Feature(value = "Тесты по странице \"О приложении\"")
    @DisplayName("Переход к пользовательскому соглашению по ссылке. Ссылка кликабельна, но страница фактически не загружается")
    public void validateTermsOfUseLinkClickability() {
        homeActions.navigateToAboutSection();
        Intents.init();
        aboutActions.openTermsOfUse();
        intended(hasData("https://vhospice.org/#/terms-of-use"));
        Intents.release();
    }
}