package ru.iteco.fmhandroid.ui.tests;

import static ru.iteco.fmhandroid.ui.data.Data.categoryBirthday;
import static ru.iteco.fmhandroid.ui.data.Data.categoryNotification;
import static ru.iteco.fmhandroid.ui.data.Data.categoryUnion;
import static ru.iteco.fmhandroid.ui.data.Data.dateNews;
import static ru.iteco.fmhandroid.ui.data.Data.descriptionNews;
import static ru.iteco.fmhandroid.ui.data.Data.futureDateNews;
import static ru.iteco.fmhandroid.ui.data.Data.newTittleNews;
import static ru.iteco.fmhandroid.ui.data.Data.timeNews;
import static ru.iteco.fmhandroid.ui.data.Data.tittleNews;
import static ru.iteco.fmhandroid.ui.data.Data.tittleNews2;
import static ru.iteco.fmhandroid.ui.data.Data.tittleNews3;
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
import ru.iteco.fmhandroid.ui.pages.EditingNewsPage;
import ru.iteco.fmhandroid.ui.pages.MainPage;
import ru.iteco.fmhandroid.ui.pages.NewsPage;

@LargeTest
@RunWith(AllureAndroidJUnit4.class)
@DisplayName("Раздел редактирования новостей")
public class NewsEditingTest {

    AuthorizationPage authorizationPage = new AuthorizationPage();
    EditingNewsPage editingNewsPage = new EditingNewsPage();
    MainPage mainPage = new MainPage();
    NewsPage newsPage = new NewsPage();

    @Before
    public void setUp() {
        authorizationPage.checkLogInAndLogInIfNot();
        mainPage.goToNewsPage();
        newsPage.goToNewsEditingPage();
    }

    @Rule
    public ActivityScenarioRule<AppActivity> activityScenarioRule = new ActivityScenarioRule<>(AppActivity.class);

    @Rule
    public ScreenshotRule screenshotRule = new ScreenshotRule(ScreenshotRule.Mode.FAILURE, generateScreenshotName("Failed"));


    @Test
    @DisplayName("Test-ID62. Создание новости с незаполненным заголовком")
    public void createdNewsWithEmptyTittleTest() {
        editingNewsPage.addNewsWithEmptyTittle(categoryNotification, dateNews, timeNews, descriptionNews);
        editingNewsPage.fieldsDoesNotBeEmpty();
    }

    @Test
    @DisplayName("Test-ID63. Создание новости с невыбранной датой публикации")
    public void createNewsWithEmptyDateTest() {
        editingNewsPage.addNewsEmptyDate(categoryNotification, tittleNews, timeNews, descriptionNews);
        editingNewsPage.fieldsDoesNotBeEmpty();
    }

    @Test
    @DisplayName("Test-ID64. Создание новости с невыбранным временем публикации")
    public void addNewsEmptyTimeTest() {
        editingNewsPage.addNewsEmptyTime(categoryNotification, tittleNews, dateNews, descriptionNews);
        editingNewsPage.fieldsDoesNotBeEmpty();
    }

    @Test
    @DisplayName("Test-ID65. Создание новости с заполненными полями валидными значениями")
    public void createNewsTest() {
        editingNewsPage.addNews(categoryNotification, tittleNews, dateNews, timeNews, descriptionNews);
        editingNewsPage.editNews(tittleNews);
        editingNewsPage.checkNews();
    }

    @Test
    @DisplayName("Test-ID83. Удаление новости")
    public void deleteNewsTest() {
        editingNewsPage.addNews(categoryBirthday, tittleNews3, dateNews, timeNews, descriptionNews);
        editingNewsPage.deleteNews(tittleNews3);
        waitElement(newsPage.newsListId);
        editingNewsPage.checkNewsDeleted(newsPage.getItemCount(), tittleNews3);
    }

    @Test
    @DisplayName("Test-ID86. Редактирование заголовка сохраненной новости")
    public void changeTittleNewsTest() {
        editingNewsPage.addNews(categoryUnion, tittleNews2, dateNews, timeNews, descriptionNews);
        editingNewsPage.editNews(tittleNews2);
        editingNewsPage.changeTittleNews(newTittleNews);
        editingNewsPage.editNews(newTittleNews);
        editingNewsPage.checkTittleAfterChange(newTittleNews);
    }
    @Test
    @DisplayName("Test-ID88. Редактирование даты публикации сохраненной новости")
    public void changeDateNewsTest() {
        editingNewsPage.addNews(categoryNotification, tittleNews2, dateNews, timeNews, descriptionNews);
        editingNewsPage.editNews(tittleNews2);
        editingNewsPage.changeDateNews(futureDateNews);
        editingNewsPage.editNews(tittleNews2);
        editingNewsPage.checkDateAfterChange(futureDateNews);

    }


    @Test
    @DisplayName("Test-ID119. Создание новости с пустыми полями")
    public void createNewsWithEmptyFieldsTest() {
        editingNewsPage.addNewsWithEmptyFields();
        editingNewsPage.fieldsDoesNotBeEmpty();
    }
}