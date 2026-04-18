package ru.iteco.fmhandroid.ui.tests;

import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasData;

import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.qameta.allure.android.rules.ScreenshotRule;
import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.Epic;
import io.qameta.allure.kotlin.Feature;
import io.qameta.allure.kotlin.junit4.DisplayName;

import ru.iteco.fmhandroid.ui.AppActivity;
import ru.iteco.fmhandroid.ui.steps.LoadingScreenSteps;
import ru.iteco.fmhandroid.ui.steps.AboutSectionActions;
import ru.iteco.fmhandroid.ui.steps.LoginStepActions;
import ru.iteco.fmhandroid.ui.steps.HomeScreenActions;

@RunWith(AllureAndroidJUnit4.class)
@Epic("Функциональные тесты мобильного приложения")
@DisplayName("Тесты страницы \"О приложении\"")
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

        // Явная проверка состояния авторизации вместо try-catch
        if (isOnLoginScreen()) {
            loginActions.performValidLogin();
        }
        homeActions.waitForHomeScreenLoad();
    }

    @After
    public void cleanupTestState() {
        try {
            // Возврат на главный экран после тестов для независимости
            homeActions.navigateToHomeScreen();
        } catch (Exception ignored) {
            // Ignore cleanup errors
        }
    }

    // Вспомогательный метод (нужно добавить в HomeScreenActions или сюда)
    private boolean isOnLoginScreen() {
        try {
            loginActions.waitForLoginScreenLoad();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Тест проверяет, что страница "О приложении" содержит все необходимые элементы.
     */
    @Test
    @Feature(value = "Тесты по странице \"О приложении\"")
    @DisplayName("Наличие всех элементов страницы")
    public void verifyAllComponentsPresentOnAboutScreen() {
        homeActions.navigateToAboutSection();
        aboutActions.verifyAboutSectionContentIsComplete();
    }

    /**
     * Тест проверяет функциональность кнопки возврата на главную страницу
     * со страницы "О приложении"
     */
    @Test
    @Feature(value = "Тесты по странице \"О приложении\"")
    @DisplayName("Возвращение на главную страницу")
    public void verifyNavigationBackToMainScreenFromAboutPage() {
        homeActions.navigateToAboutSection();
        aboutActions.navigateBackToHome();
        homeActions.waitForHomeScreenLoad();
        homeActions.verifyHomeScreenContentIsComplete();
    }

    /**
     * Тест проверяет переход к странице "О приложении" со страницы "Новости".
     * ИЗВЕСТНЫЙ БАГ: кнопка перехода не активна
     * @see <a href="ссылка_на_issue">Issue #1234</a>
     */
    @Test
    @Feature(value = "Тесты по странице \"О приложении\"")
    @DisplayName("Попытка перехода к About с экрана новостей (известный баг)")
    public void checkAboutPageAccessibilityFromNewsSection() {
        homeActions.navigateToNewsSection();
        homeActions.navigateToAboutSection();

        // Если баг существует, тест упадет здесь
        // TODO: BUG - Кнопка перехода не активна, переход невозможен
        aboutActions.verifyAboutSectionContentIsComplete();
    }

    /**
     * Тест проверяет переход к политике конфиденциальности по ссылке.
     * ИЗВЕСТНЫЙ БАГ: ссылка кликабельна, но страница не загружается
     */
    @Test
    @Feature(value = "Тесты по странице \"О приложении\"")
    @DisplayName("Переход к политике конфиденциальности (страница не загружается)")
    public void validatePrivacyPolicyLinkClickability() {
        homeActions.navigateToAboutSection();
        Intents.init();

        aboutActions.openPrivacyPolicy();

        // Проверяем, что Intent был отправлен
        intended(hasData("https://vhospice.org/#/privacy-policy"));

        Intents.release();
    }

    /**
     * Тест проверяет переход к пользовательскому соглашению по ссылке.
     * ИЗВЕСТНЫЙ БАГ: ссылка кликабельна, но страница не загружается
     */
    @Test
    @Feature(value = "Тесты по странице \"О приложении\"")
    @DisplayName("Переход к пользовательскому соглашению (страница не загружается)")
    public void validateTermsOfUseLinkClickability() {
        homeActions.navigateToAboutSection();
        Intents.init();

        aboutActions.openTermsOfUse();

        // Проверяем, что Intent был отправлен
        intended(hasData("https://vhospice.org/#/terms-of-use"));

        Intents.release();
    }
}