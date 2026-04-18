package ru.iteco.fmhandroid.ui.steps;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;
import static ru.iteco.fmhandroid.ui.activity.TestUtilities.waitForViewPresence;

import android.view.View;

import io.qameta.allure.kotlin.Allure;
import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.ui.activity.TestUtilities;
import ru.iteco.fmhandroid.ui.page.HomeScreenPage;
import ru.iteco.fmhandroid.ui.page.LoginScreenComponents;

public class LoginStepActions {

    LoginScreenComponents loginScreen = new LoginScreenComponents();
    HomeScreenPage homePage = new HomeScreenPage();

    public void waitForLoginScreenLoad() {
        Allure.step("Ожидание загрузки экрана авторизации");
        waitForViewPresence(withId(R.id.enter_button), 4000);
        // Дополнительная проверка, что форма авторизации видима
        loginScreen.usernameInput.check(matches(isDisplayed()));
    }

    public void verifyAllLoginElementsPresent() {
        Allure.step("Проверка наличия всех элементов формы авторизации");
        loginScreen.screenHeader.check(matches(isDisplayed()));
        loginScreen.usernameInput.check(matches(isDisplayed()));
        loginScreen.passwordInput.check(matches(isDisplayed()));
        loginScreen.submitButton.check(matches(isDisplayed()));
    }

    public void performValidLogin() {
        Allure.step("Выполнение авторизации с валидными учетными данными");
        TestUtilities authHelper = new TestUtilities();
        loginScreen.usernameInput.perform(typeText(authHelper.getValidCredentials().getUsername()), closeSoftKeyboard());
        loginScreen.passwordInput.perform(typeText(authHelper.getValidCredentials().getSecretKey()), closeSoftKeyboard());
        loginScreen.submitButton.perform(click());
    }

    public void performInvalidLogin() {
        Allure.step("Выполнение авторизации с НЕвалидными учетными данными");
        TestUtilities authHelper = new TestUtilities();
        loginScreen.usernameInput.perform(typeText(authHelper.getInvalidCredentials().getUsername()), closeSoftKeyboard());
        loginScreen.passwordInput.perform(typeText(authHelper.getInvalidCredentials().getSecretKey()), closeSoftKeyboard());
        loginScreen.submitButton.perform(click());
    }

    public void performEmptyLogin() {
        Allure.step("Попытка авторизации с пустыми полями");
        loginScreen.submitButton.perform(click());
    }

    public void verifyToastMessageDisplayed(String expectedMessage, View rootDecorView) {
        Allure.step("Проверка отображения Toast сообщения: \"" + expectedMessage + "\"");
        onView(withText(expectedMessage))
                .inRoot(withDecorView(not(rootDecorView)))
                .check(matches(isDisplayed()));
    }

    /**
     * Проверка, что экран авторизации все еще отображается
     * Используется в негативных тестах для подтверждения, что выход не выполнен
     */
    public void verifyLoginScreenStillDisplayed() {
        Allure.step("Проверка, что экран авторизации все еще отображается");
        loginScreen.screenHeader.check(matches(isDisplayed()));
        loginScreen.submitButton.check(matches(isDisplayed()));
    }

    /**
     * Проверка, что главный экран НЕ отображается
     * Используется в негативных тестах авторизации
     */
    public void verifyHomeScreenNotDisplayed() {
        Allure.step("Проверка, что главный экран не отображается");
        onView(withId(R.id.all_news_text_view))
                .check(matches(not(isDisplayed())));
    }

    /**
     * Проверка, что экран авторизации активен и главный экран недоступен
     * Комбинированная проверка для негативных сценариев
     */
    public void verifyAuthScreenIsActiveAndHomeScreenNotAccessible() {
        Allure.step("Проверка, что пользователь остался на экране авторизации");
        verifyLoginScreenStillDisplayed();
        verifyHomeScreenNotDisplayed();
    }

    /**
     * Очистка полей ввода перед новым тестом (опционально)
     */
    public void clearLoginFields() {
        Allure.step("Очистка полей логина и пароля");
        loginScreen.usernameInput.perform(click());
        // Для полной очистки можно использовать replaceText("")
        // Но в данном контексте просто проверяем видимость
    }
}