package ru.iteco.fmhandroid.ui.pages;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static ru.iteco.fmhandroid.ui.data.DataHelper.waitElement;

import androidx.test.espresso.ViewInteraction;

import io.qameta.allure.kotlin.Allure;
import io.qameta.allure.kotlin.Step;
import ru.iteco.fmhandroid.R;

public class MainPage {

    private ViewInteraction mainMenuButton = onView(allOf(withId(R.id.main_menu_image_button)));
    private ViewInteraction newsButton = onView(withText(R.string.news));
    private ViewInteraction logOutButton = onView(allOf(withId(R.id.authorization_image_button)));
    private ViewInteraction quotesButton = onView(allOf(withId(R.id.our_mission_image_button)));
    private ViewInteraction aboutButton = onView(withText(R.string.about));
    private ViewInteraction allNewsButton = onView(allOf(withId(R.id.all_news_text_view)));
    private int mainMenuButtonId = R.id.main_menu_image_button;
    private int allNewsButtonId = R.id.all_news_text_view;
    public int LogOutId = R.id.authorization_image_button;
    private int quotesButtonID = R.id.our_mission_image_button;

    @Step("Выход из аккаунта.")
    public void logOut() {
        Allure.step("Выйти из аккаунта, если авторизованы.");
        onView(withId(LogOutId)).perform(click());
        onView(withId(android.R.id.title)).check(matches(isDisplayed()));
        onView(withId(android.R.id.title)).perform(click());
    }

    @Step("Переход в раздел \"Новости\"")
    public void goToNewsPage() {
        Allure.step("Тап \"Главное меню\" (меню-гамбургер), выбрать раздел \"Новости\"");
        waitElement(mainMenuButtonId);
        mainMenuButton.perform(click());
        newsButton.check(matches(isDisplayed()));
        newsButton.perform(click());
    }

    @Step("Переход в раздел \"Новости\" через кнопку \"Все новости\"")
    public void goToNewsPageByAllNewsButton() {
        Allure.step("Тап \"Все новости\" на Главной странице");
        waitElement(allNewsButtonId);
        allNewsButton.perform(click());
    }

    @Step("Переход в раздел \"О приложении\"")
    public void goToAboutPage() {
        Allure.step("Тап \"Главное меню\" (меню-гамбургер), выбрать раздел \"О приложении\"");
        waitElement(mainMenuButtonId);
        mainMenuButton.perform(click());
        aboutButton.check(matches(isDisplayed()));
        aboutButton.perform(click());
    }

    @Step("Переход в раздел \"Цитаты\"")
    public void goToQuotesPage() {
        Allure.step("Тап \"Значок бабочка\"");
        waitElement(quotesButtonID);
        quotesButton.perform(click());
    }

    @Step("Проверка видимости кнопки выхода из аккаунта.")
    public void logOutIsVisible() {
        logOutButton.check(matches(isDisplayed()));
    }

}