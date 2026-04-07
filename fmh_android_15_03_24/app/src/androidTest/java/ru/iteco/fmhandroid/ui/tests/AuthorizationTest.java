package ru.iteco.fmhandroid.ui.tests;

import static ru.iteco.fmhandroid.ui.data.Data.emptyLogin;
import static ru.iteco.fmhandroid.ui.data.Data.emptyPassword;
import static ru.iteco.fmhandroid.ui.data.Data.invalidLogin;
import static ru.iteco.fmhandroid.ui.data.Data.invalidPassword;
import static ru.iteco.fmhandroid.ui.data.Data.validLogin;
import static ru.iteco.fmhandroid.ui.data.Data.validPassword;
import static ru.iteco.fmhandroid.ui.data.DataHelper.generateScreenshotName;
import static ru.iteco.fmhandroid.ui.data.DataHelper.waitElement;

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
import ru.iteco.fmhandroid.ui.pages.AuthorizationPage;
import ru.iteco.fmhandroid.ui.pages.MainPage;


@LargeTest
@RunWith(AllureAndroidJUnit4.class)
@DisplayName("Раздел \"Авторизация\"")
public class AuthorizationTest {

    AuthorizationPage authorizationPage = new AuthorizationPage();
    MainPage mainPage = new MainPage();

    @Before
    public void setUp(){
        authorizationPage.checkLogOutAndLogOutIfNot();
    }

    @Rule
    public ActivityScenarioRule<AppActivity> activityScenarioRule = new ActivityScenarioRule<>(AppActivity.class);

    @Rule
    public ScreenshotRule screenshotRule = new ScreenshotRule(ScreenshotRule.Mode.FAILURE, generateScreenshotName("Failed"));


    @Test
    @DisplayName("Test-ID4, Авторизация пользователя под валидным логином и паролем")
    public void validLoginAndPasswordAuthorizationTest() {
        authorizationPage.login(validLogin, validPassword);
        waitElement(mainPage.LogOutId);
        mainPage.logOutIsVisible();
    }

    @Test
    @DisplayName("Test-ID5, Авторизация пользователя под валидным логином и невалидным паролем")
    public void invalidPasswordAuthorizationTest() {
        authorizationPage.login(validLogin, invalidPassword);
        authorizationPage.loginOrPasswordIsWrong();
    }

    @Test
    @DisplayName("Test-ID6, Авторизация пользователя под невалидным логином и валидным паролем")
    public void invalidLoginAuthorizationTest() {
        authorizationPage.login(invalidLogin, validPassword);
        authorizationPage.loginOrPasswordIsWrong();
    }

    @Test
    @DisplayName("Test-ID7, Авторизация пользователя под невалидными логином и паролем")
    public void invalidLoginInvalidPasswordTest() {
        authorizationPage.login(invalidLogin, invalidPassword);
        authorizationPage.loginOrPasswordIsWrong();
    }


    @Test
    @DisplayName("Test-ID8, Авторизация пользователя с незаполненными полями")
    public void emptyLoginAndPasswordAuthorizationTest() {
        authorizationPage.login(emptyLogin,emptyPassword);
        authorizationPage.loginOrPasswordDoesNotBeEmpty();
    }


}