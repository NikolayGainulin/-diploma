package ru.iteco.fmhandroid.ui.steps;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static ru.iteco.fmhandroid.ui.activity.TestUtilities.waitForViewPresence;
import static ru.iteco.fmhandroid.ui.activity.TestUtilities.findByIndex;

import io.qameta.allure.kotlin.Allure;
import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.ui.activity.TestUtilities;
import ru.iteco.fmhandroid.ui.page.HomeScreenPage;
import ru.iteco.fmhandroid.ui.page.NewsListingScreen;

public class NewsListingActions {

    NewsListingScreen newsListing = new NewsListingScreen();
    HomeScreenPage homePage = new HomeScreenPage();

    public void waitForNewsListLoad() {
        Allure.step("Загрузка списка новостей");
        waitForViewPresence(withId(R.id.news_list_recycler_view), 5000);
    }

    public void verifyNewsScreenContentIsComplete() {
        Allure.step("Проверка, что в блоке новостей полный контент");
        newsListing.brandLogo.check(matches(isDisplayed()));
        newsListing.pageHeader.check(matches(isDisplayed()));
        newsListing.sortButton.check(matches(isDisplayed()));
        newsListing.filterButton.check(matches(isDisplayed()));
        newsListing.adminPanelButton.check(matches(isDisplayed()));
        newsListing.newsCardsContainer.check(matches(isDisplayed()));
    }

    public void navigateBackToHomeScreen() {
        Allure.step("Возврат на Главную страницу со страницы Новости");
        homePage.navigationMenuButton.perform(click());
        homePage.homeMenuItem.perform(click());
    }

    public void tapSortNewsControl() {
        Allure.step("Нажать кнопку сортировки");
        newsListing.sortButton.perform(click());
    }

    public void openNewsFilterDialog() {
        Allure.step("Открыть расширенный фильтр");
        newsListing.filterButton.perform(click());
    }

    public void expandCollapseNewsItem(int position) {
        Allure.step("Развернуть/свернуть новость");
        newsListing.nestedNewsFeed.perform(actionOnItemAtPosition(position, click()));
    }

    public String retrieveFirstNewsHeading(int position) {
        Allure.step("Заголовок первой новости");
        return TestUtilities.TextViewExtractor.extractText(onView(findByIndex(withId(R.id.news_item_title_text_view), position)));
    }

    public String retrieveCreatedNewsContent(int position) {
        Allure.step("Описание созданной новости");
        return TestUtilities.TextViewExtractor.extractText(onView(findByIndex(withId(R.id.news_item_description_text_view), position)));
    }
}
