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
import ru.iteco.fmhandroid.ui.page.LoginScreenComponents;

public class LoginStepActions {

    LoginScreenComponents loginScreen = new LoginScreenComponents();

    public void waitForLoginScreenLoad() {
        Allure.step("Загрузка страницы авторизации");
        waitForViewPresence(withId(R.id.enter_button), 4000);
    }

    public void verifyAllLoginElementsPresent() {
        Allure.step("Наличие всех элементов формы авторизации");
        loginScreen.screenHeader.check(matches(isDisplayed()));
        loginScreen.usernameInput.check(matches(isDisplayed()));
        loginScreen.passwordInput.check(matches(isDisplayed()));
        loginScreen.submitButton.check(matches(isDisplayed()));
    }

    public void performValidLogin() {
        Allure.step("Авторизация в приложении под валидными данным");
        TestUtilities authHelper = new TestUtilities();
        loginScreen.usernameInput.perform(typeText(authHelper.getValidCredentials().getUsername()), closeSoftKeyboard());
        loginScreen.passwordInput.perform(typeText(authHelper.getValidCredentials().getSecretKey()), closeSoftKeyboard());
        loginScreen.submitButton.perform(click());
    }

    public void performInvalidLogin() {
        Allure.step("Авторизация в приложении под НЕвалидными данным");
        TestUtilities authHelper = new TestUtilities();
        loginScreen.usernameInput.perform(typeText(authHelper.getInvalidCredentials().getUsername()), closeSoftKeyboard());
        loginScreen.passwordInput.perform(typeText(authHelper.getInvalidCredentials().getSecretKey()), closeSoftKeyboard());
        loginScreen.submitButton.perform(click());
    }

    public void performEmptyLogin() {
        Allure.step("Авторизация в приложении с пустыми данным");
        loginScreen.submitButton.perform(click());
    }

    public void verifyToastMessageDisplayed(String expectedMessage, View rootDecorView) {
        Allure.step("Проверка сообщения по тексту");
        onView(withText(expectedMessage))
                .inRoot(withDecorView(not(rootDecorView)))
                .check(matches(isDisplayed()));
    }
}