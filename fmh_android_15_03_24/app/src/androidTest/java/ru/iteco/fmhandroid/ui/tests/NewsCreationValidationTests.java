package ru.iteco.fmhandroid.ui.tests;

import static ru.iteco.fmhandroid.ui.activity.TestUtilities.RandomDataGenerator.generateRandomEventType;
import static ru.iteco.fmhandroid.ui.activity.TestUtilities.fetchCurrentDate;
import static ru.iteco.fmhandroid.ui.activity.TestUtilities.fetchCurrentTime;

import android.view.View;

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

@RunWith(AllureAndroidJUnit4.class)
@Epic("Функциональные тесты мобильного приложения")
@DisplayName("Тесты создания новостей")
public class NewsCreationValidationTests {

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

    private View rootDecorView;
    private String createdNewsHeading = null; // Для cleanup

    @Before
    public void initializeTestEnvironment() {
        loadingSteps.waitForApplicationLoad();

        // Проверяем, авторизованы ли мы
        if (!isOnHomeScreen()) {
            loginActions.performValidLogin();
            homeActions.waitForHomeScreenLoad();
        }

        activityScenarioRule.getScenario().onActivity(activity ->
                rootDecorView = activity.getWindow().getDecorView()
        );
    }

    @After
    public void cleanupTestData() {
        // Удаляем созданную новость, если она была создана
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
     * Тест проверяет процесс перехода к созданию новости
     * и наличие всех необходимых элементов на странице
     */
    @Test
    @Feature(value = "Тесты по разделу Новостей")
    @Story("Переход к созданию новости и Наличие всех элементов")
    @DisplayName("Переход к форме создания новости и проверка всех элементов")
    public void verifyAllComponentsArePresentOnNewsCreationScreen() {
        homeActions.navigateToNewsSection();
        panelActions.navigateToManagementPanel();
        panelActions.tapCreateNewsButton();
        creationSteps.verifyCreateNewsPageContentIsComplete();
    }

    /**
     * Тест проверяет создание новости с валидными данными
     */
    @Test
    @Feature(value = "Тесты по разделу Новостей")
    @Story("Cоздание новости с валидными данными")
    @DisplayName("Успешное создание новости с валидными данными")
    public void shouldCreateNewsSuccessfullyWithValidData() {
        // Генерируем уникальные данные для новости
        String uniqueId = String.valueOf(System.currentTimeMillis());
        String newsHeading = "Тестовая новость " + uniqueId;
        String newsContent = "Описание тестовой новости " + uniqueId;
        String currentDate = fetchCurrentDate();
        String currentTime = fetchCurrentTime();

        // Сохраняем заголовок для cleanup
        createdNewsHeading = newsHeading;

        homeActions.navigateToNewsSection();
        panelActions.navigateToManagementPanel();
        panelActions.tapCreateNewsButton();

        creationSteps.populateNewsData(generateRandomEventType(), newsHeading, currentDate,
                currentTime, newsContent);
        creationSteps.tapSaveNewsButton();

        // Проверяем, что новость создана
        panelActions.verifyNewsExistsWithTitle(newsHeading);

        // Сбрасываем заголовок, так как новость успешно создана и будет удалена в @After
        // createdNewsHeading уже установлен и будет удален в cleanup
    }

    /**
     * Тест проверяет попытку создания новости без ввода данных
     * Ожидается появление сообщения об ошибке
     */
    @Test
    @Feature(value = "Тесты по разделу Новостей")
    @Story("Cоздание новости с пустыми данными")
    @DisplayName("Попытка создания новости с пустыми полями")
    public void shouldFailToCreateNewsWithEmptyFieldsAndDisplayWarning() {
        homeActions.navigateToNewsSection();
        panelActions.navigateToManagementPanel();
        panelActions.tapCreateNewsButton();

        // Пытаемся сохранить без заполнения полей
        creationSteps.tapSaveNewsButton();

        // Проверяем появление сообщения об ошибке
        creationSteps.verifyPopupMessageContent("Заполните пустые поля", rootDecorView);

        // Проверяем, что мы все еще на форме создания новости
        creationSteps.verifyCreateNewsPageContentIsComplete();

        // Отменяем создание, чтобы вернуться
        creationSteps.tapCancelNewsButton();
        creationSteps.confirmDialogAction();
    }

    /**
     * Тест проверяет функционал отмены создания новости
     */
    @Test
    @Feature(value = "Тесты по разделу Новостей")
    @Story("Отменить создание новости")
    @DisplayName("Отмена создания новости и возврат в панель управления")
    public void shouldReturnToControlPanelWhenCancellingNewsCreation() {
        String uniqueId = String.valueOf(System.currentTimeMillis());
        String newsHeading = "Новость для отмены " + uniqueId;

        homeActions.navigateToNewsSection();
        panelActions.navigateToManagementPanel();
        panelActions.tapCreateNewsButton();

        // Заполняем поля, чтобы убедиться, что данные не сохранятся
        creationSteps.populateNewsData(generateRandomEventType(), newsHeading,
                fetchCurrentDate(), fetchCurrentTime(), "Описание для отмены");

        // Отменяем создание
        creationSteps.tapCancelNewsButton();
        creationSteps.confirmDialogAction();

        // Проверяем, что вернулись в панель управления
        panelActions.verifyManagementPanelContentIsComplete();

        // Проверяем, что новость НЕ была создана
        panelActions.verifyNewsDoesNotExistWithTitle(newsHeading);
    }

    /**
     * Дополнительный тест: создание новости с минимальными данными
     */
    @Test
    @Feature(value = "Тесты по разделу Новостей")
    @Story("Cоздание новости с минимальными данными")
    @DisplayName("Создание новости только с обязательными полями")
    public void shouldCreateNewsWithMinimalData() {
        String uniqueId = String.valueOf(System.currentTimeMillis());
        String newsHeading = "Минимальная новость " + uniqueId;
        String currentDate = fetchCurrentDate();
        String currentTime = fetchCurrentTime();

        createdNewsHeading = newsHeading;

        homeActions.navigateToNewsSection();
        panelActions.navigateToManagementPanel();
        panelActions.tapCreateNewsButton();

        // Заполняем только обязательные поля
        creationSteps.inputNewsHeading(newsHeading);
        creationSteps.inputPublicationDate(currentDate);
        creationSteps.inputPublicationTime(currentTime);
        creationSteps.tapSaveNewsButton();

        // Проверяем, что новость создана
        panelActions.verifyNewsExistsWithTitle(newsHeading);
    }
}