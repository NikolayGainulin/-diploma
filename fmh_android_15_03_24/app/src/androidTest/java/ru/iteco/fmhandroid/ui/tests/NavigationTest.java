package ru.iteco.fmhandroid.ui.tests;

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
import ru.iteco.fmhandroid.ui.pages.NewsPage;
import ru.iteco.fmhandroid.ui.pages.QuotesPage;


@LargeTest
@RunWith(AllureAndroidJUnit4.class)
@DisplayName("Навигация по приложению")
public class NavigationTest {

    AboutPage aboutPage = new AboutPage();
    AuthorizationPage authorizationPage = new AuthorizationPage();
    MainPage mainPage = new MainPage();
    NewsPage newsPage = new NewsPage();
    QuotesPage quotesPage = new QuotesPage();


    @Before
    public void setUp(){
        authorizationPage.checkLogInAndLogInIfNot();
    }

    @Rule
    public ActivityScenarioRule<AppActivity> activityScenarioRule = new ActivityScenarioRule<>(AppActivity.class);

    @Rule
    public ScreenshotRule screenshotRule = new ScreenshotRule(ScreenshotRule.Mode.FAILURE, generateScreenshotName("Failed"));

    @Test
    @DisplayName("Test-ID16. Переход в раздел \"Цитаты\" через бабочку")
    public void goToQuotesPageTest() {
        mainPage.goToQuotesPage();
        quotesPage.checkQuotesPage();
    }

    @Test
    @DisplayName("Test-ID20. Переход в раздел \"О приложении\"")
    public void goToAboutPageTest() {
        mainPage.goToAboutPage();
        aboutPage.checkAboutPage();
    }

    @Test
    @DisplayName("Test-ID25. Переход в раздел \"Новости\" через меню-гамбургер")
    public void goToNewsPageTest() {
        mainPage.goToNewsPage();
        newsPage.checkNewsPage();
    }

    @Test
    @DisplayName("Test-ID26. Переход в раздел \"Новости\" через кнопку \"Все новости\" в главном разделе")
    public void goToNewsPageByAllNewsButtonTest() {
        mainPage.goToNewsPageByAllNewsButton();
        newsPage.checkNewsPage();
    }

}