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
import io.qameta.allure.kotlin.Epic;
import io.qameta.allure.kotlin.Feature;
import io.qameta.allure.kotlin.Story;
import io.qameta.allure.kotlin.junit4.DisplayName;

import ru.iteco.fmhandroid.ui.AppActivity;
import ru.iteco.fmhandroid.ui.steps.LoginStepActions;
import ru.iteco.fmhandroid.ui.steps.LoadingScreenSteps;
import ru.iteco.fmhandroid.ui.steps.HomeScreenActions;

@RunWith(AllureAndroidJUnit4.class)
@Epic("Функциональные тесты мобильного приложения")
@DisplayName("Тесты авторизации")
public class LoginValidationTests {

    @Rule
    public ActivityScenarioRule<AppActivity> activityScenarioRule =
            new ActivityScenarioRule<>(AppActivity.class);

    @Rule
    public ScreenshotRule screenshotRule = new ScreenshotRule(ScreenshotRule.Mode.FAILURE,
            String.valueOf(System.currentTimeMillis()));

    LoadingScreenSteps loadingSteps = new LoadingScreenSteps();
    LoginStepActions loginActions = new LoginStepActions();
    HomeScreenActions homeActions = new HomeScreenActions();
    private View rootDecorView;

    @Before
    public void initializeTestEnvironment() {
        loadingSteps.waitForApplicationLoad();

        // Проверяем, авторизованы ли мы
        if (isLoggedIn()) {
            homeActions.performLogout();
        }

        loginActions.waitForLoginScreenLoad();
        activityScenarioRule.getScenario().onActivity(activity ->
                rootDecorView = activity.getWindow().getDecorView()
        );
    }

    @After
    public void cleanupTestState() {
        try {
            // Выходим из системы после каждого теста
            if (isLoggedIn()) {
                homeActions.performLogout();
            }
        } catch (Exception ignored) {
            // Ignore cleanup errors
        }
    }

    // Вспомогательный метод для проверки состояния авторизации
    private boolean isLoggedIn() {
        try {
            homeActions.waitForHomeScreenLoad();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Тест проверяет, что все элементы блока авторизации присутствуют на странице
     */
    @Test
    @Feature(value = "Тесты по странице Авторизации")
    @Story("Наличие всех элементов формы авторизации")
    @DisplayName("Проверка отображения всех элементов формы авторизации")
    public void verifyAllLoginFormComponentsArePresent() {
        loginActions.verifyAllLoginElementsPresent();
    }

    /**
     * Тест проверяет процесс авторизации с использованием корректных учетных данных
     */
    @Test
    @Feature(value = "Тесты по странице Авторизации")
    @Story("Авторизация в приложении под валидными данными")
    @DisplayName("Успешный вход с валидными учетными данными")
    public void shouldSuccessfullyLoginAndDisplayMainScreenWithValidCredentials() {
        loginActions.performValidLogin();
        homeActions.waitForHomeScreenLoad();
        homeActions.verifyHomeScreenContentIsComplete();
    }

    /**
     * Тест проверяет процесс авторизации с невалидными учетными данными
     * ИЗВЕСТНЫЙ БАГ: Приложение показывает некорректное сообщение об ошибке
     * @see <a href="ссылка_на_issue">Issue #5678</a>
     */
    @Test
    @Feature(value = "Тесты по странице Авторизации")
    @Story("Авторизация в приложении под НЕ валидными данными")
    @DisplayName("Отклонение входа с невалидными данными (известный баг с сообщением)")
    public void shouldRejectLoginWithInvalidCredentialsAndShowErrorPopup() {
        loginActions.performInvalidLogin();

        // TODO: BUG #5678 - Приложение показывает "Что-то пошло не так"
        // вместо ожидаемого "Неверный логин или пароль"
        loginActions.verifyToastMessageDisplayed("Что-то пошло не так. Попробуйте позднее.", rootDecorView);

        // Проверяем, что остались на экране авторизации
        loginActions.verifyLoginScreenStillDisplayed();
        loginActions.verifyHomeScreenNotDisplayed();
    }

    /**
     * Тест проверяет попытку входа в систему с пустыми данными
     */
    @Test
    @Feature(value = "Тесты по странице Авторизации")
    @Story("Авторизация в приложении с пустыми данными")
    @DisplayName("Отклонение входа с пустыми полями")
    public void shouldRejectEmptyLoginFieldsWithWarningMessage() {
        loginActions.performEmptyLogin();

        // Проверка сообщения о необходимости заполнить поля
        loginActions.verifyToastMessageDisplayed("Логин и пароль не могут быть пустыми", rootDecorView);

        // Проверяем, что остались на экране авторизации
        loginActions.verifyLoginScreenStillDisplayed();
        loginActions.verifyHomeScreenNotDisplayed();
    }

    /**
     * Тест проверяет процесс выхода из учетной записи после успешной авторизации
     */
    @Test
    @Feature(value = "Тесты по странице Авторизации")
    @Story("Выход из учётной записи")
    @DisplayName("Успешный выход из аккаунта")
    public void shouldLogoutAndReturnToAuthorizationScreen() {
        loginActions.performValidLogin();
        homeActions.waitForHomeScreenLoad();
        homeActions.verifyHomeScreenContentIsComplete();

        homeActions.performLogout();

        // Проверяем, что вернулись на экран авторизации
        loginActions.waitForLoginScreenLoad();
        loginActions.verifyAllLoginElementsPresent();
    }
}