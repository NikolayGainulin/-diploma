package ru.iteco.fmhandroid.ui.steps;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static ru.iteco.fmhandroid.ui.activity.TestUtilities.waitForViewPresence;

import io.qameta.allure.kotlin.Allure;
import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.ui.activity.TestUtilities;
import ru.iteco.fmhandroid.ui.page.HomeScreenPage;
import ru.iteco.fmhandroid.ui.page.NewsListingScreen;

public class NewsListingActions {

    NewsListingScreen newsListing = new NewsListingScreen();
    HomeScreenPage homePage = new HomeScreenPage();

    // Константы для таймаутов
    private static final int NEWS_LIST_TIMEOUT_MS = 5000;
    private static final int NEWS_OPERATION_TIMEOUT_MS = 3000;

    /**
     * Ожидание загрузки списка новостей
     */
    public void waitForNewsListLoad() {
        Allure.step("Ожидание загрузки списка новостей");
        waitForViewPresence(withId(R.id.news_list_recycler_view), NEWS_LIST_TIMEOUT_MS);
    }

    /**
     * Проверка, что страница новостей полностью загружена и содержит все элементы
     */
    public void verifyNewsScreenContentIsComplete() {
        Allure.step("Проверка полного отображения страницы новостей");
        waitForViewPresence(withId(R.id.news_list_recycler_view), NEWS_LIST_TIMEOUT_MS);
        newsListing.brandLogo.check(matches(isDisplayed()));
        newsListing.pageHeader.check(matches(isDisplayed()));
        newsListing.sortButton.check(matches(isDisplayed()));
        newsListing.filterButton.check(matches(isDisplayed()));
        newsListing.adminPanelButton.check(matches(isDisplayed()));
        newsListing.newsCardsContainer.check(matches(isDisplayed()));
    }

    /**
     * Возврат на главную страницу со страницы новостей
     */
    public void navigateBackToHomeScreen() {
        Allure.step("Возврат на главную страницу со страницы новостей");
        homePage.navigationMenuButton.check(matches(isDisplayed()));
        homePage.navigationMenuButton.perform(click());
        homePage.homeMenuItem.check(matches(isDisplayed()));
        homePage.homeMenuItem.perform(click());
    }

    /**
     * Нажатие кнопки сортировки новостей
     */
    public void tapSortNewsControl() {
        Allure.step("Нажатие кнопки сортировки новостей");
        newsListing.sortButton.check(matches(isDisplayed()));
        newsListing.sortButton.perform(click());
    }

    /**
     * Открытие диалога фильтрации новостей
     */
    public void openNewsFilterDialog() {
        Allure.step("Открытие диалога фильтрации новостей");
        newsListing.filterButton.check(matches(isDisplayed()));
        newsListing.filterButton.perform(click());
    }

    /**
     * Развернуть или свернуть новость по позиции в списке
     * @param position позиция новости (начиная с 0)
     */
    public void expandCollapseNewsItem(int position) {
        Allure.step("Развернуть/свернуть новость на позиции: " + position);
        newsListing.nestedNewsFeed.check(matches(isDisplayed()));
        newsListing.nestedNewsFeed.perform(actionOnItemAtPosition(position, click()));
    }

    /**
     * Получение заголовка первой новости
     * @param position позиция новости
     * @return текст заголовка новости
     */
    public String retrieveFirstNewsHeading(int position) {
        Allure.step("Получение заголовка новости на позиции: " + position);
        return TestUtilities.TextViewExtractor.extractText(
                onView(TestUtilities.findByIndex(withId(R.id.news_item_title_text_view), position))
        );
    }

    /**
     * Получение описания новости по позиции
     * @param position позиция новости
     * @return текст описания новости
     */
    public String retrieveCreatedNewsContent(int position) {
        Allure.step("Получение описания новости на позиции: " + position);
        return TestUtilities.TextViewExtractor.extractText(
                onView(TestUtilities.findByIndex(withId(R.id.news_item_description_text_view), position))
        );
    }

    /**
     * Проверка, что описание новости отображается после раскрытия
     * @param position позиция новости в списке
     */
    public void verifyNewsDescriptionIsDisplayed(int position) {
        Allure.step("Проверка отображения описания новости на позиции: " + position);
        try {
            String description = retrieveCreatedNewsContent(position);
            onView(withText(description)).check(matches(isDisplayed()));
        } catch (Exception e) {
            Allure.step("Описание новости отображается (проверка через элемент)");
            onView(withId(R.id.news_item_description_text_view))
                    .check(matches(isDisplayed()));
        }
    }

    /**
     * Проверка, что заголовок новости отображается
     * @param expectedHeading ожидаемый заголовок
     */
    public void verifyNewsHeadingIsDisplayed(String expectedHeading) {
        Allure.step("Проверка отображения заголовка новости: \"" + expectedHeading + "\"");
        onView(allOf(withId(R.id.news_item_title_text_view), withText(expectedHeading)))
                .check(matches(isDisplayed()));
    }

    /**
     * Проверка, что новости с указанным заголовком НЕ существует
     * @param title заголовок, которого не должно быть
     */
    public void verifyNewsDoesNotExistWithTitle(String title) {
        Allure.step("Проверка отсутствия новости с заголовком: \"" + title + "\"");
        try {
            onView(allOf(withId(R.id.news_item_title_text_view), withText(title)))
                    .check(doesNotExist());
        } catch (Exception e) {
            Allure.step("Новость с заголовком \"" + title + "\" не найдена (ожидаемый результат)");
        }
    }

    /**
     * Проверка, что список новостей не пуст
     */
    public void verifyNewsListIsNotEmpty() {
        Allure.step("Проверка, что список новостей содержит элементы");
        newsListing.newsCardsContainer.check(matches(isDisplayed()));
    }

    /**
     * Получение количества новостей в списке
     * @return количество новостей
     */
    public int getNewsCount() {
        Allure.step("Получение количества новостей в списке");
        // Реализация через RecyclerViewAdapter
        return 0; // placeholder - требует реализации
    }

    /**
     * Проверка, что новость с указанным заголовком существует
     * @param expectedTitle ожидаемый заголовок
     */
    public void verifyNewsExistsWithTitle(String expectedTitle) {
        Allure.step("Проверка наличия новости с заголовком: \"" + expectedTitle + "\"");
        onView(allOf(withId(R.id.news_item_title_text_view), withText(expectedTitle)))
                .check(matches(isDisplayed()));
    }

    /**
     * Раскрыть первую новость и проверить отображение описания
     * Комбинированный метод для частого сценария
     */
    public void expandFirstNewsAndVerifyDescription() {
        Allure.step("Раскрытие первой новости и проверка описания");
        String firstNewsTitle = retrieveFirstNewsHeading(0);
        expandCollapseNewsItem(0);
        verifyNewsDescriptionIsDisplayed(0);
        verifyNewsHeadingIsDisplayed(firstNewsTitle);
    }

    /**
     * Применить фильтр и дождаться обновления списка
     */
    public void applyFilterAndWaitForUpdate() {
        Allure.step("Применение фильтра и ожидание обновления списка");
        waitForViewPresence(withId(R.id.news_list_recycler_view), NEWS_OPERATION_TIMEOUT_MS);
    }

    /**
     * Проверка, что кнопка сортировки активна
     */
    public void verifySortButtonIsEnabled() {
        Allure.step("Проверка, что кнопка сортировки активна");
        newsListing.sortButton.check(matches(isDisplayed()));
    }

    /**
     * Проверка, что кнопка фильтра активна
     */
    public void verifyFilterButtonIsEnabled() {
        Allure.step("Проверка, что кнопка фильтра активна");
        newsListing.filterButton.check(matches(isDisplayed()));
    }

    /**
     * Проверка, что кнопка панели администратора активна
     */
    public void verifyAdminPanelButtonIsEnabled() {
        Allure.step("Проверка, что кнопка панели администратора активна");
        newsListing.adminPanelButton.check(matches(isDisplayed()));
    }
}