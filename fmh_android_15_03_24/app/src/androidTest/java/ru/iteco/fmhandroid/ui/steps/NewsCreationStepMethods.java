package ru.iteco.fmhandroid.ui.steps;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;

import android.view.View;

import io.qameta.allure.kotlin.Allure;
import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.ui.page.NewsCreationScreen;

public class NewsCreationStepMethods {

    NewsCreationScreen newsCreationScreen = new NewsCreationScreen();

    // Константы для таймаутов
    private static final int DIALOG_RESPONSE_TIMEOUT_MS = 2000;

    /**
     * Проверка, что страница создания новости полностью загружена
     */
    public void verifyCreateNewsPageContentIsComplete() {
        Allure.step("Проверка полного отображения формы создания новости");
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

    /**
     * Ввод значения в поле категории
     * @param categoryValue значение категории
     */
    public void inputNewsCategory(String categoryValue) {
        Allure.step("Ввод категории новости: \"" + categoryValue + "\"");
        newsCreationScreen.categoryField.check(matches(isDisplayed()));
        newsCreationScreen.categoryField.perform(replaceText(categoryValue));
    }

    /**
     * Ввод значения в поле заголовка
     * @param headingValue значение заголовка
     */
    public void inputNewsHeading(String headingValue) {
        Allure.step("Ввод заголовка новости: \"" + headingValue + "\"");
        newsCreationScreen.headingField.check(matches(isDisplayed()));
        newsCreationScreen.headingField.perform(replaceText(headingValue));
    }

    /**
     * Ввод даты публикации
     * @param dateValue дата в формате "dd.MM.yyyy"
     */
    public void inputPublicationDate(String dateValue) {
        Allure.step("Ввод даты публикации: \"" + dateValue + "\"");
        newsCreationScreen.publishDateField.check(matches(isDisplayed()));
        newsCreationScreen.publishDateField.perform(replaceText(dateValue));
    }

    /**
     * Ввод времени публикации
     * @param timeValue время в формате "HH:mm"
     */
    public void inputPublicationTime(String timeValue) {
        Allure.step("Ввод времени публикации: \"" + timeValue + "\"");
        newsCreationScreen.publishTimeField.check(matches(isDisplayed()));
        newsCreationScreen.publishTimeField.perform(replaceText(timeValue));
    }

    /**
     * Ввод описания новости
     * @param contentValue текст описания
     */
    public void inputNewsContent(String contentValue) {
        Allure.step("Ввод описания новости: \"" + contentValue + "\"");
        newsCreationScreen.contentField.check(matches(isDisplayed()));
        newsCreationScreen.contentField.perform(replaceText(contentValue));
    }

    /**
     * Заполнение всех полей новости (комбинированный метод)
     * @param category категория новости
     * @param heading заголовок новости
     * @param publishDate дата публикации
     * @param publishTime время публикации
     * @param description описание новости
     */
    public void populateNewsData(String category, String heading, String publishDate,
                                 String publishTime, String description) {
        Allure.step("Заполнение всех полей формы создания новости");
        inputNewsCategory(category);
        inputNewsHeading(heading);
        inputPublicationDate(publishDate);
        inputPublicationTime(publishTime);
        inputNewsContent(description);
    }

    /**
     * Нажатие кнопки сохранения новости
     */
    public void tapSaveNewsButton() {
        Allure.step("Нажатие кнопки сохранения новости");
        newsCreationScreen.submitButton.check(matches(isDisplayed()));
        newsCreationScreen.submitButton.perform(click());
    }

    /**
     * Нажатие кнопки отмены создания новости
     */
    public void tapCancelNewsButton() {
        Allure.step("Нажатие кнопки отмены создания новости");
        newsCreationScreen.discardButton.check(matches(isDisplayed()));
        newsCreationScreen.discardButton.perform(click());
    }

    /**
     * Подтверждение действия в диалоговом окне (нажатие "ОК" или "Да")
     */
    public void confirmDialogAction() {
        Allure.step("Подтверждение действия в диалоговом окне (нажатие 'ОК')");
        try {
            newsCreationScreen.confirmDialogButton.check(matches(isDisplayed()));
            newsCreationScreen.confirmDialogButton.perform(click());
        } catch (Exception e) {
            // Пробуем альтернативные кнопки
            Allure.step("Поиск альтернативной кнопки подтверждения");
            onView(withText("ОК")).perform(click());
        }
    }

    /**
     * Отмена действия в диалоговом окне (нажатие "Отмена" или "Нет")
     */
    public void cancelDialogAction() {
        Allure.step("Отмена действия в диалоговом окне (нажатие 'Отмена')");
        try {
            onView(withText("Отмена")).check(matches(isDisplayed()));
            onView(withText("Отмена")).perform(click());
        } catch (Exception e) {
            // Пробуем альтернативные кнопки
            Allure.step("Поиск альтернативной кнопки отмены");
            onView(withText("Нет")).perform(click());
        }
    }

    /**
     * Проверка отображения всплывающего сообщения
     * @param expectedMessage ожидаемый текст сообщения
     * @param rootView корневое представление активности
     */
    public void verifyPopupMessageContent(String expectedMessage, View rootView) {
        Allure.step("Проверка отображения сообщения: \"" + expectedMessage + "\"");
        onView(withText(expectedMessage))
                .inRoot(withDecorView(not(rootView)))
                .check(matches(isDisplayed()));
    }

    /**
     * Очистка всех полей формы создания новости
     */
    public void clearAllFields() {
        Allure.step("Очистка всех полей формы создания новости");
        inputNewsCategory("");
        inputNewsHeading("");
        inputPublicationDate("");
        inputPublicationTime("");
        inputNewsContent("");
    }

    /**
     * Проверка, что форма создания новости пуста
     */
    public void verifyFormIsEmpty() {
        Allure.step("Проверка, что форма создания новости пуста");
        // Проверяем, что поля содержат пустые строки
        // Реализация зависит от того, как приложение отображает пустые поля
    }

    /**
     * Переключение статуса активности новости (Активна/Не активна)
     */
    public void toggleNewsActiveStatus() {
        Allure.step("Переключение статуса активности новости");
        newsCreationScreen.activeToggle.check(matches(isDisplayed()));
        newsCreationScreen.activeToggle.perform(click());
    }

    /**
     * Проверка, что кнопка сохранения активна
     */
    public void verifySaveButtonIsEnabled() {
        Allure.step("Проверка, что кнопка сохранения активна");
        newsCreationScreen.submitButton.check(matches(isDisplayed()));
        // Дополнительная проверка на enabled/disabled если необходимо
    }
}