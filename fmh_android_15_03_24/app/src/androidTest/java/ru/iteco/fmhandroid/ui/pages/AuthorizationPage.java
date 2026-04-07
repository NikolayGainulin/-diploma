package ru.iteco.fmhandroid.ui.pages;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.supportsInputMethods;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;
import static ru.iteco.fmhandroid.ui.data.Data.validLogin;
import static ru.iteco.fmhandroid.ui.data.Data.validPassword;
import static ru.iteco.fmhandroid.ui.data.DataHelper.checkToast;
import static ru.iteco.fmhandroid.ui.data.DataHelper.waitElement;
import static ru.iteco.fmhandroid.ui.data.DataHelper.waitUntilVisible;

import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.ViewInteraction;

import io.qameta.allure.kotlin.Allure;
import io.qameta.allure.kotlin.Step;
import ru.iteco.fmhandroid.R;

public class AuthorizationPage {

    private ViewInteraction fieldLogin = onView(allOf(supportsInputMethods(), isDescendantOfA(withId(R.id.login_text_input_layout))));
    private ViewInteraction fieldPassword = onView(allOf(supportsInputMethods(), isDescendantOfA(withId(R.id.password_text_input_layout))));
    private ViewInteraction enterButton = onView(allOf(withId(R.id.enter_button)));
    public int enterButtonId = R.id.enter_button;

    MainPage mainPage = new MainPage();

    @Step("Авторизация в приложении.")
    public void login(String login, String password){
        Allure.step("Вводим логин и пароль: " + login + ", " + password);
        waitElement(enterButtonId);
        fieldLogin.perform(replaceText(login));
        fieldPassword.perform(replaceText(password));
        enterButton.check(matches(isDisplayed())).perform(click());
    }

    @Step("Вход в аккаунт, если не авторизован.")
    public void checkLogInAndLogInIfNot() {
        if (isLogIn()) {
            login(validLogin, validPassword);
        }
    }

    @Step("Выход из аккаунта, если авторизован.")
    public void checkLogOutAndLogOutIfNot() {
        if (isLogOut()) {
            mainPage.logOut();
        }
    }

    @Step("Проверка видимости сообщения об ошибке при пустых логине и/или пароле.")
    public void loginOrPasswordDoesNotBeEmpty() {
        Allure.step("Проверяем видимость сообщения об ошибке при пустых логине и/или пароле.");
        waitUntilVisible(checkToast(R.string.empty_login_or_password, true));
    }

    @Step("Проверка видимости сообщения об ошибке при неправильных логине и/или пароле.")
    public void loginOrPasswordIsWrong() {
        Allure.step("Проверяем видимость сообщения об ошибке при неправильных логине и/или пароле.");
        waitUntilVisible(checkToast(R.string.error, true));
    }

    public boolean isLogIn() {
        boolean check =false;
        try {
            waitElement(enterButtonId);
            fieldLogin.check(matches(isDisplayed()));
            check = true;
            return check;
        } catch (NoMatchingViewException e) {
            check = false;
            return check;
        } finally {
            return check;
        }
    }

    public boolean isLogOut() {
        boolean check =false;
        try {
            waitElement(mainPage.LogOutId);
            mainPage.logOutIsVisible();
            check = true;
            return check;
        } catch (NoMatchingViewException e) {
            check = false;
            return check;
        } finally {
            return check;
        }
    }

}