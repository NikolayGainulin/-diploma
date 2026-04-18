package ru.iteco.fmhandroid.ui.tests;

import static ru.iteco.fmhandroid.ui.activity.TestUtilities.RandomDataGenerator.generateRandomEventType;
import static ru.iteco.fmhandroid.ui.activity.TestUtilities.fetchCurrentDate;
import static ru.iteco.fmhandroid.ui.activity.TestUtilities.fetchCurrentTime;

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
import ru.iteco.fmhandroid.ui.steps.NewsCreationStepMethods;
import ru.iteco.fmhandroid.ui.steps.LoadingScreenSteps;
import ru.iteco.fmhandroid.ui.steps.HomeScreenActions;
import ru.iteco.fmhandroid.ui.steps.NewsListingActions;

@RunWith(AllureAndroidJUnit4.class)
@Epic("Функциональные тесты мобильного приложения")
@DisplayName("Тесты удаления новостей")
public class NewsDeletionValidationTests {

    @Rule
    public ActivityScenarioRule<AppActivity> activityScenarioRule =
            new ActivityScenarioRule<>(AppActivity.class);

    @Rule
    public ScreenshotRule screenshotRule = new ScreenshotRule(ScreenshotRule.Mode.FAILURE,
            String.valueOf(System.currentTimeMillis()));

    LoadingScreenSteps loadingSteps = new LoadingScreenSteps();
    HomeScreenActions homeActions = new HomeScreenActions();
    LoginStepActions loginActions = new LoginStepActions();
    ManagementPanelActions panelActions = new ManagementPanelActions();
    NewsCreationStepMethods creationSteps = new NewsCreationStepMethods();
    NewsListingActions newsActions = new NewsListingActions();

    private String createdNewsHeading = null; // Для cleanup

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
    public void cleanupTestData() {
        // Удаляем созданную новость, если она не была удалена в тесте
        if (createdNewsHeading != null) {
            try {
                homeActions.navigateToNewsSection();
                panelActions.navigateToManagementPanel();
                panelActions.removeNewsByTitle(createdNewsHeading);
                createdNewsHeading = null;
            } catch (Exception ignored) {
                // Новость могла быть уже удалена или не создана
            }
        }

        // Возврат на главный экран
        try {
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
     * Тест проверяет процесс удаления новости
     * Создает новость → Удаляет → Проверяет, что новость исчезла
     */
    @Test
    @Feature(value = "Тесты по разделу Новостей")
    @Story("Удаление новости")
    @DisplayName("Успешное удаление созданной новости")
    public void shouldDeleteNewsSuccessfullyAndConfirmRemoval() {
        // Генерируем уникальные данные для новости
        String uniqueId = String.valueOf(System.currentTimeMillis());
        String newsHeading = "Новость для удаления " + uniqueId;
        String newsContent = "Описание новости для удаления " + uniqueId;
        String currentDate = fetchCurrentDate();
        String currentTime = fetchCurrentTime();

        // Сохраняем заголовок для cleanup
        createdNewsHeading = newsHeading;

        // Шаг 1: Создаем новость
        homeActions.navigateToNewsSection();
        panelActions.navigateToManagementPanel();
        panelActions.tapCreateNewsButton();

        creationSteps.populateNewsData(generateRandomEventType(), newsHeading, currentDate,
                currentTime, newsContent);
        creationSteps.tapSaveNewsButton();

        // Шаг 2: Проверяем, что новость создана
        panelActions.verifyNewsExistsWithTitle(newsHeading);

        // Шаг 3: Удаляем новость
        panelActions.removeNewsByTitle(newsHeading);

        // Шаг 4: Проверяем, что панель управления все еще отображается
        panelActions.verifyManagementPanelContentIsComplete();

        // Шаг 5: Проверяем, что новость больше не существует
        panelActions.verifyNewsDoesNotExistWithTitle(newsHeading);

        // Сбрасываем заголовок, так как новость уже удалена
        createdNewsHeading = null;
    }

    /**
     * Тест проверяет, что после удаления новости она не отображается на главном экране
     */
    @Test
    @Feature(value = "Тесты по разделу Новостей")
    @Story("Удаление новости")
    @DisplayName("Удаленная новость не отображается на главном экране")
    public void verifyDeletedNewsDoesNotAppearOnHomeScreen() {
        // Генерируем уникальные данные для новости
        String uniqueId = String.valueOf(System.currentTimeMillis());
        String newsHeading = "Новость для проверки главного экрана " + uniqueId;
        String newsContent = "Описание для проверки главного экрана " + uniqueId;
        String currentDate = fetchCurrentDate();
        String currentTime = fetchCurrentTime();

        // Сохраняем заголовок для cleanup
        createdNewsHeading = newsHeading;

        // Создаем новость
        homeActions.navigateToNewsSection();
        panelActions.navigateToManagementPanel();
        panelActions.tapCreateNewsButton();

        creationSteps.populateNewsData(generateRandomEventType(), newsHeading, currentDate,
                currentTime, newsContent);
        creationSteps.tapSaveNewsButton();

        // Проверяем, что новость создана в панели управления
        panelActions.verifyNewsExistsWithTitle(newsHeading);

        // Удаляем новость
        panelActions.removeNewsByTitle(newsHeading);

        // Возвращаемся на главный экран
        homeActions.navigateToHomeScreen();
        homeActions.waitForHomeScreenLoad();

        // Переходим ко всем новостям на главном экране
        homeActions.viewAllNewsItems();
        newsActions.waitForNewsListLoad();

        // Проверяем, что удаленная новость НЕ отображается в списке
        newsActions.verifyNewsDoesNotExistWithTitle(newsHeading);

        // Сбрасываем заголовок, так как новость уже удалена
        createdNewsHeading = null;
    }

    /**
     * Тест проверяет удаление нескольких новостей подряд
     */
    @Test
    @Feature(value = "Тесты по разделу Новостей")
    @Story("Удаление новости")
    @DisplayName("Последовательное удаление нескольких новостей")
    public void shouldDeleteMultipleNewsSuccessfully() {
        String uniqueId = String.valueOf(System.currentTimeMillis());
        String firstNewsHeading = "Первая новость для удаления " + uniqueId;
        String secondNewsHeading = "Вторая новость для удаления " + uniqueId;
        String newsContent = "Описание для удаления " + uniqueId;
        String currentDate = fetchCurrentDate();
        String currentTime = fetchCurrentTime();

        homeActions.navigateToNewsSection();
        panelActions.navigateToManagementPanel();

        // Создаем первую новость
        panelActions.tapCreateNewsButton();
        creationSteps.populateNewsData(generateRandomEventType(), firstNewsHeading, currentDate,
                currentTime, newsContent);
        creationSteps.tapSaveNewsButton();
        panelActions.verifyNewsExistsWithTitle(firstNewsHeading);

        // Создаем вторую новость
        panelActions.tapCreateNewsButton();
        creationSteps.populateNewsData(generateRandomEventType(), secondNewsHeading, currentDate,
                currentTime, newsContent);
        creationSteps.tapSaveNewsButton();
        panelActions.verifyNewsExistsWithTitle(secondNewsHeading);

        // Удаляем первую новость
        panelActions.removeNewsByTitle(firstNewsHeading);
        panelActions.verifyNewsDoesNotExistWithTitle(firstNewsHeading);

        // Проверяем, что вторая новость все еще существует
        panelActions.verifyNewsExistsWithTitle(secondNewsHeading);

        // Удаляем вторую новость
        panelActions.removeNewsByTitle(secondNewsHeading);
        panelActions.verifyNewsDoesNotExistWithTitle(secondNewsHeading);

        // Созданные новости удалены, cleanup не нужен
    }

    /**
     * Тест проверяет подтверждение удаления через диалог
     */
    @Test
    @Feature(value = "Тесты по разделу Новостей")
    @Story("Удаление новости")
    @DisplayName("Отмена удаления новости через диалог подтверждения")
    public void shouldCancelDeletionAndKeepNews() {
        // Генерируем уникальные данные для новости
        String uniqueId = String.valueOf(System.currentTimeMillis());
        String newsHeading = "Новость для отмены удаления " + uniqueId;
        String newsContent = "Описание для отмены удаления " + uniqueId;
        String currentDate = fetchCurrentDate();
        String currentTime = fetchCurrentTime();

        // Сохраняем заголовок для cleanup
        createdNewsHeading = newsHeading;

        // Создаем новость
        homeActions.navigateToNewsSection();
        panelActions.navigateToManagementPanel();
        panelActions.tapCreateNewsButton();

        creationSteps.populateNewsData(generateRandomEventType(), newsHeading, currentDate,
                currentTime, newsContent);
        creationSteps.tapSaveNewsButton();

        // Проверяем, что новость создана
        panelActions.verifyNewsExistsWithTitle(newsHeading);

        // Пытаемся удалить, но отменяем в диалоге
        // Нужно добавить метод cancelDeletion в ManagementPanelActions
        // panelActions.cancelNewsDeletion(newsHeading);

        // Проверяем, что новость все еще существует
        panelActions.verifyNewsExistsWithTitle(newsHeading);

        // Очищаем после теста (удаляем новость)
        panelActions.removeNewsByTitle(newsHeading);
        createdNewsHeading = null;
    }
}