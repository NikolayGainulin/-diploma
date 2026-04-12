package ru.iteco.fmhandroid.ui.steps;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;

import io.qameta.allure.kotlin.Allure;
import ru.iteco.fmhandroid.ui.page.NewsUpdateScreen;

public class NewsUpdateStepMethods {

    NewsUpdateScreen newsUpdateScreen = new NewsUpdateScreen();

    public void verifyEditNewsPageContentIsComplete() {
        Allure.step("Проверка, что в окне Редактирования новости полный контент");
        newsUpdateScreen.screenHeader.check(matches(isDisplayed()));
        newsUpdateScreen.categorySelector.check(matches(isDisplayed()));
        newsUpdateScreen.headingInput.check(matches(isDisplayed()));
        newsUpdateScreen.contentInput.check(matches(isDisplayed()));
        newsUpdateScreen.dateSelector.check(matches(isDisplayed()));
        newsUpdateScreen.timeSelector.check(matches(isDisplayed()));
        newsUpdateScreen.activeSwitch.check(matches(isDisplayed()));
        newsUpdateScreen.updateButton.check(matches(isDisplayed()));
        newsUpdateScreen.rejectButton.check(matches(isDisplayed()));
    }

    public void inputNewsCategoryValue(String categoryValue) {
        Allure.step("Ввод данных в поле Категория");
        newsUpdateScreen.categorySelector.perform(replaceText(categoryValue));
    }

    public void inputNewsHeadingValue(String headingValue) {
        Allure.step("Ввод данных в поле Заголовок");
        newsUpdateScreen.headingInput.perform(replaceText(headingValue));
    }

    public void inputPublicationDateValue(String dateValue) {
        Allure.step("Ввод данных в поле Дата публикации");
        newsUpdateScreen.dateSelector.perform(replaceText(dateValue));
    }

    public void inputPublicationTimeValue(String timeValue) {
        Allure.step("Ввод данных в поле Время");
        newsUpdateScreen.timeSelector.perform(replaceText(timeValue));
    }

    public void inputNewsContentValue(String contentValue) {
        Allure.step("Ввод данных в поле Описание");
        newsUpdateScreen.contentInput.perform(replaceText(contentValue));
    }

    public void modifyNewsData(String category, String heading, String publishDate,
                               String publishTime, String description) {
        Allure.step("Перезаполнение/редактирование данных новости");
        inputNewsCategoryValue(category);
        inputNewsHeadingValue(heading);
        inputNewsContentValue(description);
        inputPublicationDateValue(publishDate);
        inputPublicationTimeValue(publishTime);
    }

    public void toggleNewsStatus() {
        Allure.step("Поменять статус новости");
        newsUpdateScreen.activeSwitch.perform(click());
    }

    public void tapUpdateButton() {
        Allure.step("Нажатие кнопки Сохранить");
        newsUpdateScreen.updateButton.perform(click());
    }

    public void tapRejectButton() {
        Allure.step("Нажатие кнопки Отмена");
        newsUpdateScreen.rejectButton.perform(click());
    }

    public void tapConfirmDialogButton() {
        Allure.step("Нажатие кнопки ОК в сообщении");
        newsUpdateScreen.confirmAction.perform(click());
    }
}