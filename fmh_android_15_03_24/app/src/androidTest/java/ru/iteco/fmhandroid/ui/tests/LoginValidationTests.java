package ru.iteco.fmhandroid.ui.tests;

import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static org.hamcrest.CoreMatchers.not;

import android.view.View;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.qameta.allure.android.rules.ScreenshotRule;
import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.Feature;
import io.qameta.allure.kotlin.Story;
import ru.iteco.fmhandroid.ui.AppActivity;
import ru.iteco.fmhandroid.ui.page.LoginScreenComponents;
import ru.iteco.fmhandroid.ui.page.HomeScreenPage;
import ru.iteco.fmhandroid.ui.steps.LoginStepActions;
import ru.iteco.fmhandroid.ui.steps.LoadingScreenSteps;
import ru.iteco.fmhandroid.ui.steps.HomeScreenActions;

@RunWith(AllureAndroidJUnit4.class)
public class LoginValidationTests {

    @Rule
    public ActivityScenarioRule<AppActivity> activityScenarioRule =
            new ActivityScenarioRule<>(AppActivity.class);

    @Rule
    public ScreenshotRule screenshotRule = new ScreenshotRule(ScreenshotRule.Mode.FAILURE,
            String.valueOf(System.currentTimeMillis()));

    LoadingScreenSteps loadingSteps = new LoadingScreenSteps();
    LoginScreenComponents loginScreen = new LoginScreenComponents();
    LoginStepActions loginActions = new LoginStepActions();
    HomeScreenPage homePage = new HomeScreenPage();
    HomeScreenActions homeActions = new HomeScreenActions();
    private View rootDecorView;

    @Before
    public void initializeTestEnvironment() {
        loadingSteps.waitForApplicationLoad();
        try {
            loginActions.waitForLoginScreenLoad();
        } catch (Exception e) {
            homeActions.performLogout();
            loginActions.waitForLoginScreenLoad();
        }
        activityScenarioRule.getScenario().onActivity(activity -> rootDecorView = activity.getWindow().getDecorView());
    }

    @After
    public void cleanupTestState() {
        try {
            homeActions.performLogout();
        } catch (Exception ignored) {
        }
    }

    /* Тест проверяет, что все элементы блока авторизации присутствуют на странице */
    @Test
    @Feature(value = "Тесты по странице Авторизации")
    @Story("Наличие всех элементов формы авторизации")
    public void verifyAllLoginFormComponentsArePresent() {
        loginActions.verifyAllLoginElementsPresent();
    }

    /* Тест проверяет процесс авторизации с использованием корректных учетных данных пользователя */
    @Test
    @Feature(value = "Тесты по странице Авторизации")
    @Story("Авторизация в приложении под валидными данными")
    public void shouldSuccessfullyLoginAndDisplayMainScreenWithValidCredentials() {
        loginActions.performValidLogin();
        homeActions.waitForHomeScreenLoad();
        homeActions.verifyHomeScreenContentIsComplete();
    }

    /* Тест проверяет процесс авторизации с невалидными учетными данными */
    @Test
    @Feature(value = "Тесты по странице Авторизации")
    @Story("Авторизация в приложении под НЕ валидными данными")
    public void shouldRejectLoginWithInvalidCredentialsAndShowErrorPopup() {
        loginActions.performInvalidLogin();
        loginActions.verifyToastMessageDisplayed("Неверный логин или пароль", rootDecorView);
        // Верное сообщение: "Неверный логин или пароль"
        // Фактическое сообщение: "Что-то пошло не так. Попробуйте позднее."
        loginScreen.screenHeader.check(matches(isDisplayed()));
        homePage.appLogo.check(matches(not(isDisplayed())));
    }

    /* Тест проверяет попытку входа в систему с пустыми данными */
    @Test
    @Feature(value = "Тесты по странице Авторизации")
    @Story("Авторизация в приложении с пустыми данными")
    public void shouldRejectEmptyLoginFieldsWithWarningMessage() {
        loginActions.performEmptyLogin();

        // Проверка сообщения:
        loginActions.verifyToastMessageDisplayed("Логин и пароль не могут быть пустыми", rootDecorView);

        loginScreen.screenHeader.check(matches(isDisplayed()));
        homePage.appLogo.check(matches(not(isDisplayed())));
    }

    /* Тест проверяет процесс выхода из учетной записи после успешной авторизации */
    @Test
    @Feature(value = "Тесты по странице Авторизациии")
    @Story("Выход из учётной записи")
    public void shouldLogoutAndReturnToAuthorizationScreen() {
        loginActions.performValidLogin();
        homeActions.waitForHomeScreenLoad();
        homeActions.performLogout();
        loginActions.verifyAllLoginElementsPresent();
    }
}