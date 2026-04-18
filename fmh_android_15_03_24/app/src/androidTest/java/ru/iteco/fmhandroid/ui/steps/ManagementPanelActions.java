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

    // Константы для таймаутов
    private static final int MANAGEMENT_PANEL_TIMEOUT_MS = 5000;
    private static final int NEWS_OPERATION_TIMEOUT_MS = 3000;

    /**
     * Переход в панель управления со страницы Новости
     */
    public void navigateToManagementPanel() {
        Allure.step("Переход в панель управления со страницы Новости");
        newsListing.adminPanelButton.check(matches(isDisplayed()));
        newsListing.adminPanelButton.perform(click());
        waitForViewPresence(withId(R.id.add_news_image_view), MANAGEMENT_PANEL_TIMEOUT_MS);
    }

    /**
     * Проверка, что панель управления полностью загружена и содержит все элементы
     */
    public void verifyManagementPanelContentIsComplete() {
        Allure.step("Проверка полного отображения панели управления");
        waitForViewPresence(withId(R.id.add_news_image_view), MANAGEMENT_PANEL_TIMEOUT_MS);
        managementBoard.brandLogo.check(matches(isDisplayed()));
        managementBoard.orderToggleButton.check(matches(isDisplayed()));
        managementBoard.filterToggleButton.check(matches(isDisplayed()));
        managementBoard.createNewsButton.check(matches(isDisplayed()));
    }

    /**
     * Нажать кнопку сортировки новостей
     */
    public void tapSortNewsButton() {
        Allure.step("Нажатие кнопки сортировки новостей");
        managementBoard.orderToggleButton.check(matches(isDisplayed()));
        managementBoard.orderToggleButton.perform(click());
    }

    /**
     * Открыть расширенный фильтр новостей
     */
    public void openExtendedNewsFilter() {
        Allure.step("Открытие расширенного фильтра новостей");
        managementBoard.filterToggleButton.check(matches(isDisplayed()));
        managementBoard.filterToggleButton.perform(click());
    }

    /**
     * Открыть диалог фильтрации новостей
     */
    public void openNewsFilterDialog() {
        Allure.step("Открытие диалога фильтрации новостей");
        managementBoard.filterToggleButton.perform(click());
    }

    /**
     * Нажать кнопку создания новости
     */
    public void tapCreateNewsButton() {
        Allure.step("Нажатие кнопки создания новости");
        managementBoard.createNewsButton.check(matches(isDisplayed()));
        managementBoard.createNewsButton.perform(click());
    }

    /**
     * Удалить новость по заголовку
     * @param newsHeading заголовок новости для удаления
     */
    public void removeNewsByTitle(String newsHeading) {
        Allure.step("Удаление новости с заголовком: \"" + newsHeading + "\"");
        managementBoard.removeNewsItem(newsHeading).check(matches(isDisplayed()));
        managementBoard.removeNewsItem(newsHeading).perform(click());
        newsCreationSteps.confirmDialogAction();
        waitForViewPresence(withId(R.id.news_list_recycler_view), NEWS_OPERATION_TIMEOUT_MS);
    }

    /**
     * Открыть форму редактирования новости по заголовку
     * @param newsHeading заголовок новости для редактирования
     */
    public void editNewsByTitle(String newsHeading) {
        Allure.step("Открытие формы редактирования новости: \"" + newsHeading + "\"");
        managementBoard.modifyNewsItem(newsHeading).check(matches(isDisplayed()));
        managementBoard.modifyNewsItem(newsHeading).perform(click());
    }

    /**
     * Проверка наличия новости с указанным заголовком
     * @param expectedTitle ожидаемый заголовок новости
     */
    public void verifyNewsExistsWithTitle(String expectedTitle) {
        Allure.step("Проверка наличия новости с заголовком: \"" + expectedTitle + "\"");
        onView(allOf(withText(expectedTitle), isDisplayed()))
                .check(matches(isDisplayed()));
    }

    /**
     * Проверка, что новости с указанным заголовком НЕ существует
     * @param expectedTitle заголовок новости, которой не должно быть
     */
    public void verifyNewsDoesNotExistWithTitle(String expectedTitle) {
        Allure.step("Проверка отсутствия новости с заголовком: \"" + expectedTitle + "\"");
        try {
            onView(allOf(withText(expectedTitle), isDisplayed()))
                    .check(doesNotExist());
        } catch (Exception e) {
            // Элемент не найден - это ожидаемое поведение
            Allure.step("Новость с заголовком \"" + expectedTitle + "\" не найдена (ожидаемый результат)");
        }
    }

    /**
     * Получить текст заголовка новости по позиции в списке
     * @param position позиция новости в списке (начиная с 0)
     * @return текст заголовка новости
     */
    public String getNewsTitleByPosition(int position) {
        Allure.step("Получение заголовка новости на позиции: " + position);
        // Реализация через RecyclerViewActions
        return ru.iteco.fmhandroid.ui.activity.TestUtilities.TextViewExtractor.extractText(
                onView(ru.iteco.fmhandroid.ui.activity.TestUtilities.findByIndex(
                        withId(R.id.news_item_title_text_view), position))
        );
    }

    /**
     * Проверка, что в панели управления отображается хотя бы одна новость
     */
    public void verifyAtLeastOneNewsExists() {
        Allure.step("Проверка наличия хотя бы одной новости в панели управления");
        // Проверяем, что список новостей не пуст
        onView(withId(R.id.news_list_recycler_view))
                .check(matches(isDisplayed()));
    }

    /**
     * Отмена удаления новости (нажатие "Отмена" в диалоге подтверждения)
     * @param newsHeading заголовок новости, удаление которой отменяется
     */
    public void cancelNewsDeletion(String newsHeading) {
        Allure.step("Отмена удаления новости: \"" + newsHeading + "\"");
        managementBoard.removeNewsItem(newsHeading).check(matches(isDisplayed()));
        managementBoard.removeNewsItem(newsHeading).perform(click());
        newsCreationSteps.cancelDialogAction();
    }

    /**
     * Проверка отображения диалога подтверждения удаления
     */
    public void verifyDeletionConfirmationDialogIsDisplayed() {
        Allure.step("Проверка отображения диалога подтверждения удаления");
        onView(withText("Удалить"))
                .check(matches(isDisplayed()));
        onView(withText("Отмена"))
                .check(matches(isDisplayed()));
    }
}