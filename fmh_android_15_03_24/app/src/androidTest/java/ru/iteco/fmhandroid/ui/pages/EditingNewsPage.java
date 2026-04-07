package ru.iteco.fmhandroid.ui.pages;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;
import static org.junit.Assert.assertNotEquals;
import static ru.iteco.fmhandroid.ui.data.Data.categoryBirthday;
import static ru.iteco.fmhandroid.ui.data.Data.dateNews;
import static ru.iteco.fmhandroid.ui.data.Data.descriptionNews;
import static ru.iteco.fmhandroid.ui.data.Data.futureDateNews;
import static ru.iteco.fmhandroid.ui.data.Data.timeNews;
import static ru.iteco.fmhandroid.ui.data.Data.tittleNews;
import static ru.iteco.fmhandroid.ui.data.DataHelper.checkToast;
import static ru.iteco.fmhandroid.ui.data.DataHelper.clickChildViewWithId;
import static ru.iteco.fmhandroid.ui.data.DataHelper.getTextFromNews;
import static ru.iteco.fmhandroid.ui.data.DataHelper.waitElement;
import static ru.iteco.fmhandroid.ui.data.DataHelper.waitUntilVisible;

import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.contrib.RecyclerViewActions;

import io.qameta.allure.kotlin.Allure;
import io.qameta.allure.kotlin.Step;
import ru.iteco.fmhandroid.R;

public class EditingNewsPage {

    public static ViewInteraction addNewsButton = onView(withId(R.id.add_news_image_view));
    public static ViewInteraction categoryField = onView(withId(R.id.news_item_category_text_auto_complete_text_view));
    public static ViewInteraction tittleField = onView(withId(R.id.news_item_title_text_input_edit_text));
    public static ViewInteraction dateField = onView(withId(R.id.news_item_publish_date_text_input_edit_text));
    public static ViewInteraction timeField = onView(withId(R.id.news_item_publish_time_text_input_edit_text));
    public static ViewInteraction descriptionField = onView(withId(R.id.news_item_description_text_input_edit_text));
    public static ViewInteraction saveButton = onView(withId(R.id.save_button));
    public static ViewInteraction confirmDeleteNewsButton = onView(allOf(withId(android.R.id.button1)));
    public static int confirmDeleteNewsButtonId = android.R.id.button1;
    NewsPage newsPage = new NewsPage();

    public void scrollNews(int i) {
        onView(withId(newsPage.newsListId))
                .perform(scrollToPosition(i))
                .perform(actionOnItemAtPosition(i, scrollTo()))
                .check(matches(isDisplayed()));
    }

    @Step("Создание новости")
    public void addNews(String category, String tittle, String date, String time, String description) {
        Allure.step("Тап на кнопку \"+\". Заполняем поля: категория, заголовок, дата, время, описание. Нажать кнопку \"Сохранить\"");
        addNewsButton.perform(click());
        categoryField.perform(replaceText(category));
        tittleField.perform(replaceText(tittle));
        dateField.perform(replaceText(date));
        timeField.perform(replaceText(time));
        descriptionField.perform(replaceText(description));
        saveButton.perform(click());
    }

    @Step("Поиск новости в списке по заголовку")
    public void findNewsWithTittle(String tittle) {
        Allure.step("Ищем новость в списке по заголовку " + tittle);
        waitElement(newsPage.newsListId);
        onView(withId(newsPage.newsListId))
                .check(matches(isDisplayed()))
                .perform(RecyclerViewActions.scrollTo(hasDescendant(allOf(withText(tittle)))));
        onView(withId(newsPage.newsListId))
                .check(matches(isDisplayed()));
    }

    @Step("Выбираем новость для редактирования")
    public void editNews(String tittle) {
        Allure.step("Выбираем новость - " + tittle + " для редактирования");
        findNewsWithTittle(tittle);
        onView(allOf(withId(R.id.news_item_material_card_view), hasDescendant(withText(tittle))))
                .perform(clickChildViewWithId(R.id.edit_news_item_image_view));
    }

    @Step("Проверяем, что все поля новости соответствуют заданным при ее создании")
    public void checkNews() {
        Allure.step("Проверяем, что все поля новости соответствуют заданным");
        onView(withText(tittleNews)).check(matches(isDisplayed()));
        onView(withText(dateNews)).check(matches(isDisplayed()));
        onView(withText(timeNews)).check(matches(isDisplayed()));
        onView(withText(descriptionNews)).check(matches(isDisplayed()));
    }

    @Step("Проверка заголовка после изменения")
    public void checkTittleAfterChange(String tittle) {
        Allure.step("Проверяем, что заголовок изменен");
        onView(withText(tittle)).check(matches(isDisplayed()));
    }

    @Step("Смена заголовка новости")
    public void changeTittleNews(String newTittle) {
        Allure.step("Меняем заголовок новости на " + newTittle);
        tittleField.perform(replaceText(newTittle));
        saveButton.perform(click());
        waitElement(newsPage.newsListId);
    }

    @Step("Смена даты публикации новости")
    public void changeDateNews(String futureDateNews) {
        Allure.step("Меняем дату публикации на " + futureDateNews);
        dateField.perform(replaceText(futureDateNews));
        saveButton.perform(click());
        waitElement(newsPage.newsListId);
    }

    @Step("Проверка смены даты публикации новости")
    public void checkDateAfterChange(String futureDateNews) {
        Allure.step("Проверяем, что дата публикации изменилась");
        onView(withText(futureDateNews)).check(matches(isDisplayed()));
    }


    @Step("Удаление новости")
    public void deleteNews(String tittle) {
        Allure.step("Удаляем выбранную новость - " + tittle);
        findNewsWithTittle(tittle);
        onView(allOf(withId(R.id.news_item_material_card_view), hasDescendant(withText(tittle))))
                .perform(clickChildViewWithId(R.id.delete_news_item_image_view));
        confirmDelete();
    }

    @Step("Проверка, что новость удалена")
    public void checkNewsDeleted(int itemCount, String tittle) {
        Allure.step("Проверяем, что новость удалена");
        for (int i = 0; i < itemCount; i++) {
            scrollNews(i);
            String actualTittle = getTextFromNews(R.id.news_item_title_text_view, i);
            assertNotEquals(tittle, actualTittle);
        }
    }

    @Step
    public void confirmDelete() {
        Allure.step("Подтверждаем удаление новости нажимая ОК");
        waitElement(confirmDeleteNewsButtonId);
        confirmDeleteNewsButton.perform(click());
    }

    @Step("Создание новости с пустыми полями")
    public void addNewsWithEmptyFields() {
        Allure.step("Тап на кнопку \"+\". Оставить пустыми поля: категория, заголовок, дата, время, описание. Нажать кнопку \"Сохранить\"");
        addNewsButton.perform(click());
        saveButton.perform(click());
    }

    @Step("Создание новости с пустым заголовком")
    public void addNewsWithEmptyTittle(String category, String date, String time, String description) {
        Allure.step("Тап на кнопку \"+\". Заполнить поля, кроме заголовка: категория, дата, время, описание. Нажать кнопку \"Сохранить\"");
        addNewsButton.perform(click());
        categoryField.perform(replaceText(category));
        dateField.perform(replaceText(date));
        timeField.perform(replaceText(time));
        descriptionField.perform(replaceText(description));
        saveButton.perform(click());
    }

    @Step("Создание новости с невыбранной датой публикации")
    public void addNewsEmptyDate(String category, String tittle, String time, String description) {
        Allure.step("Тап на кнопку \"+\". Заполнить поля, кроме даты публикации: категория, время, описание. Нажать на кнопку \"Сохранить\"");
        addNewsButton.perform(click());
        categoryField.perform(replaceText(category));
        tittleField.perform(replaceText(tittle));
        timeField.perform(replaceText(time));
        descriptionField.perform(replaceText(description));
        saveButton.perform(click());
    }
    @Step("Создание новости c невыбранным временем публикации")
    public void addNewsEmptyTime(String category, String tittle, String date, String description) {
        Allure.step("Тап на кнопку \"+\". Заполнить поля, кроме времени публикации: категория, заголовок, дата, описание. Нажать кнопку \"Сохранить\"");
        addNewsButton.perform(click());
        categoryField.perform(replaceText(category));
        tittleField.perform(replaceText(tittle));
        dateField.perform(replaceText(date));
        descriptionField.perform(replaceText(description));
        saveButton.perform(click());
    }
    @Step("Проверка видимости сообщения об ошибке при создании новости с пустыми полями.")
    public void fieldsDoesNotBeEmpty() {
        Allure.step("Проверяем видимость сообщения об ошибке при создании новости с пустыми полями.");
        waitUntilVisible(checkToast(R.string.empty_fields, true));
    }
}