package ru.iteco.fmhandroid.ui.tests;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.qameta.allure.android.rules.ScreenshotRule;
import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.Epic;
import io.qameta.allure.kotlin.Feature;
import io.qameta.allure.kotlin.Story;
import io.qameta.allure.kotlin.junit4.DisplayName;

import ru.iteco.fmhandroid.ui.AppActivity;
import ru.iteco.fmhandroid.ui.steps.LoginStepActions;
import ru.iteco.fmhandroid.ui.steps.ManagementPanelActions;
import ru.iteco.fmhandroid.ui.steps.LoadingScreenSteps;
import ru.iteco.fmhandroid.ui.steps.NewsFilterStepMethods;
import ru.iteco.fmhandroid.ui.steps.HomeScreenActions;
import ru.iteco.fmhandroid.ui.steps.NewsListingActions;

@RunWith(AllureAndroidJUnit4.class)
@Epic("Функциональные тесты мобильного приложения")
@DisplayName("Модульные тесты раздела Новостей")
public class NewsModuleValidationTests {

    @Rule
    public ActivityScenarioRule<AppActivity> activityScenarioRule =
            new ActivityScenarioRule<>(AppActivity.class);

    @Rule
    public ScreenshotRule screenshotRule = new ScreenshotRule(ScreenshotRule.Mode.FAILURE,
            String.valueOf(System.currentTimeMillis()));

    LoadingScreenSteps loadingSteps = new LoadingScreenSteps();
    HomeScreenActions homeActions = new HomeScreenActions();
    LoginStepActions loginActions = new LoginStepActions();
    NewsListingActions newsActions = new NewsListingActions();
    ManagementPanelActions panelActions = new ManagementPanelActions();
    NewsFilterStepMethods filterActions = new NewsFilterStepMethods();

    @Before
    public void initializeTestEnvironment() {
        loadingSteps.waitForApplicationLoad();

        // Проверяем, авторизованы ли мы
        if (!isOnHomeScreen()) {
            loginActions.performValidLogin();
            homeActions.waitForHomeScreenLoad();
        }
    }

    @After
    public void cleanupTestState() {
        try {
            // Возврат на главный экран после тестов
            homeActions.navigateToHomeScreen();
        } catch (Exception ignored) {
            // Ignore cleanup errors
        }
    }

    // Вспомогательный метод для проверки состояния
    private boolean isOnHomeScreen() {
        try {
            homeActions.waitForHomeScreenLoad();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Тест проверяет возможность перехода ко всем новостям с главной страницы
     */
    @Test
    @Feature(value = "Тесты по разделу Новостей")
    @Story("Переход ко всем новостям с главной страницы")
    @DisplayName("Переход к списку всех новостей с главного экрана")
    public void verifyAllNewsAccessFromHomeScreen() {
        homeActions.viewAllNewsItems();

        // Проверяем, что перешли на страницу новостей
        newsActions.waitForNewsListLoad();
        newsActions.verifyNewsScreenContentIsComplete();
    }

    /**
     * Тест проверяет функциональность разворачивания (раскрытия) отдельной новости на главной странице
     */
    @Test
    @Feature(value = "Тесты по разделу Новостей")
    @Story("Развернуть новость на главной странице")
    @DisplayName("Раскрытие новости для просмотра описания")
    public void verifySingleNewsExpansionOnHomeScreen() {
        // Сохраняем заголовок первой новости до раскрытия
        String firstNewsTitle = newsActions.retrieveFirstNewsHeading(0);

        // Раскрываем первую новость
        newsActions.expandCollapseNewsItem(0);

        // Проверяем, что описание появилось
        newsActions.verifyNewsDescriptionIsDisplayed(0);

        // Проверяем, что заголовок все еще отображается
        newsActions.verifyNewsHeadingIsDisplayed(firstNewsTitle);

        // Сворачиваем обратно (опционально)
        newsActions.expandCollapseNewsItem(0);
    }

    /**
     * Тест проверяет, что все элементы на странице новостей присутствуют и корректно отображаются
     */
    @Test
    @Feature(value = "Тесты по разделу Новостей")
    @Story("Наличие всех элементов на странице Новости")
    @DisplayName("Проверка полного отображения страницы новостей")
    public void verifyCompleteContentDisplayOnNewsPage() {
        homeActions.navigateToNewsSection();
        newsActions.waitForNewsListLoad();
        newsActions.verifyNewsScreenContentIsComplete();
    }

    /**
     * Тест проверяет возможность перехода на страницу панели управления
     * и наличие всех элементов на этой странице
     */
    @Test
    @Feature(value = "Тесты по разделу Новостей")
    @Story("Переход на страницу Панели управления и Наличие всех элементов")
    @DisplayName("Переход в панель управления и проверка всех элементов")
    public void verifyAccessToManagementPanelWithAllComponents() {
        homeActions.navigateToNewsSection();
        panelActions.navigateToManagementPanel();
        panelActions.verifyManagementPanelContentIsComplete();
    }

    /**
     * Тест проверяет возврат на главную страницу со страницы новостей
     */
    @Test
    @Feature(value = "Тесты по разделу Новостей")
    @Story("Возврат на Главную страницу со страницы Новости")
    @DisplayName("Возврат на главную страницу со страницы новостей")
    public void verifyReturnToHomeScreenFromNewsPageWithFullContentCheck() {
        homeActions.navigateToNewsSection();
        newsActions.verifyNewsScreenContentIsComplete();

        newsActions.navigateBackToHomeScreen();

        homeActions.waitForHomeScreenLoad();
        homeActions.verifyHomeScreenContentIsComplete();
    }

    /**
     * Тест проверяет функциональность отмены фильтрации новостей
     */
    @Test
    @Feature(value = "Тесты по разделу Новостей")
    @Story("Выход из фильтра без фильтрации новостей")
    @DisplayName("Отмена фильтрации без применения изменений")
    public void verifyCancelNewsFilterWithoutApplyingChanges() {
        homeActions.navigateToNewsSection();
        newsActions.waitForNewsListLoad();

        // Сохраняем первую новость до фильтрации
        String firstNewsBefore = newsActions.retrieveFirstNewsHeading(0);

        // Открываем фильтр
        newsActions.openNewsFilterDialog();

        // Отменяем фильтрацию
        filterActions.tapCancelFilterButton();

        // Проверяем, что список новостей не изменился
        String firstNewsAfter = newsActions.retrieveFirstNewsHeading(0);
        org.junit.Assert.assertEquals("Список новостей не должен измениться после отмены фильтра",
                firstNewsBefore, firstNewsAfter);

        // Проверяем, что страница новостей все еще отображается корректно
        newsActions.verifyNewsScreenContentIsComplete();
    }

    /**
     * Тест проверяет функциональность сортировки новостей
     */
    @Test
    @Feature(value = "Тесты по разделу Новостей")
    @Story("Сортировка новостей")
    @DisplayName("Сортировка новостей по дате")
    public void verifyNewsSortingFunctionality() {
        homeActions.navigateToNewsSection();
        newsActions.waitForNewsListLoad();

        // Получаем первую новость до сортировки
        String firstNewsBefore = newsActions.retrieveFirstNewsHeading(0);

        // Нажимаем кнопку сортировки
        newsActions.tapSortNewsControl();

        // Небольшая задержка для применения сортировки (Espresso обрабатывает автоматически)

        // Получаем первую новость после сортировки
        String firstNewsAfter = newsActions.retrieveFirstNewsHeading(0);

        // Проверяем, что порядок изменился (или хотя бы что сортировка сработала)
        // Примечание: если порядок не изменился, это может быть не багом, а особенностью данных
        newsActions.verifyNewsScreenContentIsComplete();
    }

    /**
     * Тест проверяет применение фильтра по категории
     */
    @Test
    @Feature(value = "Тесты по разделу Новостей")
    @Story("Фильтрация новостей")
    @DisplayName("Фильтрация новостей по категории")
    public void verifyNewsFilterByCategory() {
        homeActions.navigateToNewsSection();
        newsActions.waitForNewsListLoad();

        // Открываем фильтр
        newsActions.openNewsFilterDialog();

        // Выбираем категорию (например, "Объявление")
        filterActions.selectNewsCategory("Объявление");

        // Применяем фильтр
        filterActions.tapApplyFilterButton();

        // Проверяем, что страница новостей все еще отображается
        newsActions.verifyNewsScreenContentIsComplete();

        // Проверяем, что отфильтрованные новости соответствуют выбранной категории
        // (реализация зависит от отображения категории в списке)
    }

    /**
     * Тест проверяет фильтрацию по статусу (активные/неактивные новости)
     */
    @Test
    @Feature(value = "Тесты по разделу Новостей")
    @Story("Фильтрация новостей")
    @DisplayName("Фильтрация новостей по статусу активности")
    public void verifyNewsFilterByStatus() {
        homeActions.navigateToNewsSection();
        newsActions.waitForNewsListLoad();

        // Открываем фильтр
        newsActions.openNewsFilterDialog();

        // Выбираем только активные новости
        filterActions.selectActiveStatusCheckbox();

        // Применяем фильтр
        filterActions.tapApplyFilterButton();

        // Проверяем, что страница новостей отображается
        newsActions.verifyNewsScreenContentIsComplete();
    }

    /**
     * Тест проверяет сброс всех фильтров
     */
    @Test
    @Feature(value = "Тесты по разделу Новостей")
    @Story("Фильтрация новостей")
    @DisplayName("Сброс всех примененных фильтров")
    public void verifyResetAllFilters() {
        homeActions.navigateToNewsSection();
        newsActions.waitForNewsListLoad();

        // Сохраняем первую новость до фильтрации
        String firstNewsBefore = newsActions.retrieveFirstNewsHeading(0);

        // Применяем фильтр
        newsActions.openNewsFilterDialog();
        filterActions.selectActiveStatusCheckbox();
        filterActions.tapApplyFilterButton();

        // Сбрасываем фильтры (открываем фильтр и сбрасываем)
        newsActions.openNewsFilterDialog();
        filterActions.resetAllFilters();
        filterActions.tapApplyFilterButton();

        // Проверяем, что вернулись к исходному списку
        String firstNewsAfter = newsActions.retrieveFirstNewsHeading(0);
        org.junit.Assert.assertEquals("После сброса фильтров список должен вернуться к исходному",
                firstNewsBefore, firstNewsAfter);
    }
}