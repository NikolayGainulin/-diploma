package ru.iteco.fmhandroid.ui.steps;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static ru.iteco.fmhandroid.ui.activity.TestUtilities.waitForViewPresence;

import io.qameta.allure.kotlin.Allure;
import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.ui.page.ManagementBoardPage;
import ru.iteco.fmhandroid.ui.page.NewsListingScreen;

public class ManagementPanelActions {

    ManagementBoardPage managementBoard = new ManagementBoardPage();
    NewsListingScreen newsListing = new NewsListingScreen();
    NewsCreationStepMethods newsCreationSteps = new NewsCreationStepMethods();

    public void navigateToManagementPanel() {
        Allure.step("Переход в панель управления со страницы Новости");
        newsListing.adminPanelButton.perform(click());
        waitForViewPresence(withId(R.id.add_news_image_view), 5000);
    }

    public void verifyManagementPanelContentIsComplete() {
        Allure.step("Проверка, что в панели управления полный контент");
        waitForViewPresence(withId(R.id.add_news_image_view), 5000);
        managementBoard.brandLogo.check(matches(isDisplayed()));
        managementBoard.orderToggleButton.check(matches(isDisplayed()));
        managementBoard.filterToggleButton.check(matches(isDisplayed()));
        managementBoard.createNewsButton.check(matches(isDisplayed()));
    }

    public void tapSortNewsButton() {
        Allure.step("Нажать кнопку сортировки");
        managementBoard.orderToggleButton.perform(click());
    }

    public void openExtendedNewsFilter() {
        Allure.step("Открыть расширенный фильтр");
        managementBoard.filterToggleButton.perform(click());
    }

    public void tapCreateNewsButton() {
        Allure.step("Нажать кнопку создание новости");
        managementBoard.createNewsButton.perform(click());
    }

    public void removeNewsByTitle(String newsHeading) {
        Allure.step("Удалить новость с указанным заголовком");
        managementBoard.removeNewsItem(newsHeading).perform(click());
        newsCreationSteps.confirmDialogAction();
    }

    public void editNewsByTitle(String newsHeading) {
        Allure.step("Нажать кнопку Корректировка новости");
        managementBoard.modifyNewsItem(newsHeading).perform(click());
    }

    public void verifyNewsExistsWithTitle(String expectedTitle) {
        Allure.step("Проверка наличия новости с указанным заголовком");
        onView(allOf(withText(expectedTitle), isDisplayed())).check(matches(isDisplayed()));
    }

    public void verifyNewsDoesNotExistWithTitle(String expectedTitle) {
        Allure.step("Проверка, что новости с указанным заголовком нет");
        onView(allOf(withText(expectedTitle), isDisplayed())).check(doesNotExist());
    }
}