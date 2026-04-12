package ru.iteco.fmhandroid.ui.steps;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;

import android.view.View;

import io.qameta.allure.kotlin.Allure;
import ru.iteco.fmhandroid.ui.page.NewsCreationScreen;

public class NewsCreationStepMethods {

    NewsCreationScreen newsCreationScreen = new NewsCreationScreen();

    public void verifyCreateNewsPageContentIsComplete() {
        Allure.step("Проверка, что в окне Создания новости полный контент");
        newsCreationScreen.headerTitle.check(matches(isDisplayed()));
        newsCreationScreen.categoryField.check(matches(isDisplayed()));
        newsCreationScreen.headingField.check(matches(isDisplayed()));
        newsCreationScreen.contentField.check(matches(isDisplayed()));
        newsCreationScreen.publishDateField.check(matches(isDisplayed()));
        newsCreationScreen.publishTimeField.check(matches(isDisplayed()));
        newsCreationScreen.activeToggle.check(matches(isDisplayed()));
        newsCreationScreen.submitButton.check(matches(isDisplayed()));
        newsCreationScreen.discardButton.check(matches(isDisplayed()));
    }

    public void inputNewsCategory(String categoryValue) {
        Allure.step("Ввод данных в поле Категория");
        newsCreationScreen.categoryField.perform(replaceText(categoryValue));
    }

    public void inputNewsHeading(String headingValue) {
        Allure.step("Ввод данных в поле Заголовок");
        newsCreationScreen.headingField.perform(replaceText(headingValue));
    }

    public void inputPublicationDate(String dateValue) {
        Allure.step("Ввод данных в поле Дата публикации");
        newsCreationScreen.publishDateField.perform(replaceText(dateValue));
    }

    public void inputPublicationTime(String timeValue) {
        Allure.step("Ввод данных в поле Время");
        newsCreationScreen.publishTimeField.perform(replaceText(timeValue));
    }

    public void inputNewsContent(String contentValue) {
        Allure.step("Ввод данных в поле Описание");
        newsCreationScreen.contentField.perform(replaceText(contentValue));
    }

    public void populateNewsData(String category, String heading, String publishDate,
                                 String publishTime, String description) {
        Allure.step("Ввод данных для создания новости");
        inputNewsCategory(category);
        inputNewsHeading(heading);
        inputPublicationDate(publishDate);
        inputPublicationTime(publishTime);
        inputNewsContent(description);
    }

    public void tapSaveNewsButton() {
        Allure.step("Нажатие кнопки Сохранить");
        newsCreationScreen.submitButton.perform(click());
    }

    public void tapCancelNewsButton() {
        Allure.step("Нажатие кнопки Отмена");
        newsCreationScreen.discardButton.perform(click());
    }

    public void confirmDialogAction() {
        Allure.step("Нажатие кнопки ОК в сообщении");
        newsCreationScreen.confirmDialogButton.perform(click());
    }

    public void verifyPopupMessageContent(String expectedMessage, View rootView) {
        Allure.step("Проверка сообщения");
        onView(withText(expectedMessage))
                .inRoot(withDecorView(not(rootView)))
                .check(matches(isDisplayed()));
    }
}