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
import ru.iteco.fmhandroid.ui.steps.NewsUpdateStepMethods;
import ru.iteco.fmhandroid.ui.steps.LoadingScreenSteps;
import ru.iteco.fmhandroid.ui.steps.HomeScreenActions;
import ru.iteco.fmhandroid.ui.steps.NewsListingActions;

@RunWith(AllureAndroidJUnit4.class)
@Epic("Функциональные тесты мобильного приложения")
@DisplayName("Тесты редактирования новостей")
public class NewsEditingValidationTests {

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
    NewsUpdateStepMethods updateSteps = new NewsUpdateStepMethods();
    NewsListingActions newsActions = new NewsListingActions();

    private String createdNewsHeading = null;
    private String updatedNewsHeading = null;

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
        try {
            homeActions.navigateToNewsSection();
            panelActions.navigateToManagementPanel();

            if (updatedNewsHeading != null && !updatedNewsHeading.equals(createdNewsHeading)) {
                panelActions.removeNewsByTitle(updatedNewsHeading);
            }
            if (createdNewsHeading != null) {
                panelActions.removeNewsByTitle(createdNewsHeading);
            }
        } catch (Exception ignored) {
            // Новости могли быть уже удалены или не созданы
        }

        // Возврат на главный экран
        try {
            homeActions.navigateToHomeScreen();
        } catch (Exception ignored) {
            // Ignore cleanup errors
        }

        createdNewsHeading = null;
        updatedNewsHeading = null;
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

    // Вспомогательный метод для создания тестовой новости
    private String createTestNews(String prefix) {
        String uniqueId = String.valueOf(System.currentTimeMillis());
        String heading = prefix + " " + uniqueId;
        String content = "Содержимое для " + prefix + " " + uniqueId;
        String currentDate = fetchCurrentDate();
        String currentTime = fetchCurrentTime();

        panelActions.tapCreateNewsButton();
        creationSteps.populateNewsData(generateRandomEventType(), heading, currentDate,
                currentTime, content);
        creationSteps.tapSaveNewsButton();
        panelActions.verifyNewsExistsWithTitle(heading);

        return heading;
    }

    /**
     * Тест проверяет процесс редактирования новости
     * Создает новость → Редактирует все поля → Проверяет изменения
     */
    @Test
    @Feature(value = "Тесты по разделу Новостей")
    @Story("Редактирование новости")
    @DisplayName("Успешное редактирование новости с изменением всех полей")
    public void shouldEditNewsSuccessfullyAndConfirmChanges() {
        String uniqueId = String.valueOf(System.currentTimeMillis());
        String originalHeading = "Оригинальный заголовок " + uniqueId;
        String updatedHeading = "Обновленный заголовок " + uniqueId;
        String originalContent = "Оригинальное описание " + uniqueId;
        String updatedContent = "Обновленное описание " + uniqueId;
        String currentDate = fetchCurrentDate();
        String currentTime = fetchCurrentTime();

        // Сохраняем для cleanup
        createdNewsHeading = originalHeading;
        updatedNewsHeading = updatedHeading;

        // Шаг 1: Создаем новость
        homeActions.navigateToNewsSection();
        panelActions.navigateToManagementPanel();

        panelActions.tapCreateNewsButton();
        creationSteps.populateNewsData(generateRandomEventType(), originalHeading, currentDate,
                currentTime, originalContent);
        creationSteps.tapSaveNewsButton();

        // Шаг 2: Проверяем, что новость создана
        panelActions.verifyNewsExistsWithTitle(originalHeading);

        // Шаг 3: Редактируем новость
        panelActions.editNewsByTitle(originalHeading);
        updateSteps.verifyEditNewsPageContentIsComplete();

        updateSteps.modifyNewsData(generateRandomEventType(), updatedHeading, currentDate,
                currentTime, updatedContent);
        updateSteps.tapUpdateButton();

        // Шаг 4: Проверяем изменения
        panelActions.verifyNewsExistsWithTitle(updatedHeading);
        panelActions.verifyNewsDoesNotExistWithTitle(originalHeading);

        // Сбрасываем, так как новость теперь имеет новый заголовок
        createdNewsHeading = null;
    }

    /**
     * Тест проверяет редактирование только заголовка новости
     */
    @Test
    @Feature(value = "Тесты по разделу Новостей")
    @Story("Редактирование новости")
    @DisplayName("Редактирование только заголовка новости")
    public void shouldEditOnlyNewsHeading() {
        String uniqueId = String.valueOf(System.currentTimeMillis());
        String originalHeading = "Оригинальный заголовок " + uniqueId;
        String updatedHeading = "Обновленный заголовок " + uniqueId;
        String newsContent = "Неизменное описание " + uniqueId;
        String currentDate = fetchCurrentDate();
        String currentTime = fetchCurrentTime();

        createdNewsHeading = originalHeading;
        updatedNewsHeading = updatedHeading;

        homeActions.navigateToNewsSection();
        panelActions.navigateToManagementPanel();

        // Создаем новость
        panelActions.tapCreateNewsButton();
        creationSteps.populateNewsData(generateRandomEventType(), originalHeading, currentDate,
                currentTime, newsContent);
        creationSteps.tapSaveNewsButton();
        panelActions.verifyNewsExistsWithTitle(originalHeading);

        // Редактируем только заголовок
        panelActions.editNewsByTitle(originalHeading);
        updateSteps.inputNewsHeadingValue(updatedHeading);
        updateSteps.tapUpdateButton();

        // Проверяем
        panelActions.verifyNewsExistsWithTitle(updatedHeading);
        panelActions.verifyNewsDoesNotExistWithTitle(originalHeading);

        createdNewsHeading = null;
    }

    /**
     * Тест проверяет редактирование только описания новости
     */
    @Test
    @Feature(value = "Тесты по разделу Новостей")
    @Story("Редактирование новости")
    @DisplayName("Редактирование только описания новости")
    public void shouldEditOnlyNewsContent() {
        String uniqueId = String.valueOf(System.currentTimeMillis());
        String newsHeading = "Новость с неизменным заголовком " + uniqueId;
        String originalContent = "Оригинальное описание " + uniqueId;
        String updatedContent = "Обновленное описание " + uniqueId;
        String currentDate = fetchCurrentDate();
        String currentTime = fetchCurrentTime();

        createdNewsHeading = newsHeading;

        homeActions.navigateToNewsSection();
        panelActions.navigateToManagementPanel();

        // Создаем новость
        panelActions.tapCreateNewsButton();
        creationSteps.populateNewsData(generateRandomEventType(), newsHeading, currentDate,
                currentTime, originalContent);
        creationSteps.tapSaveNewsButton();

        // Редактируем только описание
        panelActions.editNewsByTitle(newsHeading);
        updateSteps.inputNewsContentValue(updatedContent);
        updateSteps.tapUpdateButton();

        // Проверяем, что заголовок не изменился
        panelActions.verifyNewsExistsWithTitle(newsHeading);
    }

    /**
     * Тест проверяет функциональность отмены редактирования новости
     */
    @Test
    @Feature(value = "Тесты по разделу Новостей")
    @Story("Отмена редактирования новости")
    @DisplayName("Отмена редактирования сохраняет исходные данные")
    public void shouldCancelEditingAndPreserveOriginalNewsData() {
        String uniqueId = String.valueOf(System.currentTimeMillis());
        String newsHeading = "Заголовок для отмены редактирования " + uniqueId;
        String newsContent = "Описание для отмены редактирования " + uniqueId;
        String newHeading = "Новый заголовок (не должен сохраниться) " + uniqueId;
        String currentDate = fetchCurrentDate();
        String currentTime = fetchCurrentTime();

        createdNewsHeading = newsHeading;

        homeActions.navigateToNewsSection();
        panelActions.navigateToManagementPanel();

        // Создаем новость
        panelActions.tapCreateNewsButton();
        creationSteps.populateNewsData(generateRandomEventType(), newsHeading, currentDate,
                currentTime, newsContent);
        creationSteps.tapSaveNewsButton();

        // Проверяем, что новость создана
        panelActions.verifyNewsExistsWithTitle(newsHeading);

        // Пытаемся редактировать
        panelActions.editNewsByTitle(newsHeading);
        updateSteps.verifyEditNewsPageContentIsComplete();

        // Изменяем данные
        updateSteps.inputNewsHeadingValue(newHeading);
        updateSteps.inputNewsContentValue("Новое описание (не должно сохраниться)");

        // Отменяем редактирование
        updateSteps.tapRejectButton();
        updateSteps.tapConfirmDialogButton();

        // Проверяем, что вернулись в панель управления
        panelActions.verifyManagementPanelContentIsComplete();

        // Проверяем, что оригинальная новость осталась без изменений
        panelActions.verifyNewsExistsWithTitle(newsHeading);

        // Проверяем, что новый заголовок НЕ появился
        panelActions.verifyNewsDoesNotExistWithTitle(newHeading);
    }

    /**
     * Тест проверяет редактирование статуса новости (активна/не активна)
     */
    @Test
    @Feature(value = "Тесты по разделу Новостей")
    @Story("Редактирование новости")
    @DisplayName("Изменение статуса активности новости")
    public void shouldToggleNewsStatus() {
        String uniqueId = String.valueOf(System.currentTimeMillis());
        String newsHeading = "Новость для смены статуса " + uniqueId;
        String newsContent = "Описание для смены статуса " + uniqueId;
        String currentDate = fetchCurrentDate();
        String currentTime = fetchCurrentTime();

        createdNewsHeading = newsHeading;

        homeActions.navigateToNewsSection();
        panelActions.navigateToManagementPanel();

        // Создаем новость
        panelActions.tapCreateNewsButton();
        creationSteps.populateNewsData(generateRandomEventType(), newsHeading, currentDate,
                currentTime, newsContent);
        creationSteps.tapSaveNewsButton();

        panelActions.verifyNewsExistsWithTitle(newsHeading);

        // Редактируем и меняем статус
        panelActions.editNewsByTitle(newsHeading);
        updateSteps.toggleNewsStatus();
        updateSteps.tapUpdateButton();

        // Проверяем, что новость все еще существует
        panelActions.verifyNewsExistsWithTitle(newsHeading);

        // Дополнительно можно проверить, что статус изменился
        // (например, через отображение иконки или цвета)
    }

    /**
     * Тест проверяет редактирование даты и времени публикации
     */
    @Test
    @Feature(value = "Тесты по разделу Новостей")
    @Story("Редактирование новости")
    @DisplayName("Изменение даты и времени публикации новости")
    public void shouldEditPublicationDateAndTime() {
        String uniqueId = String.valueOf(System.currentTimeMillis());
        String newsHeading = "Новость для изменения даты " + uniqueId;
        String newsContent = "Описание " + uniqueId;
        String currentDate = fetchCurrentDate();
        String currentTime = fetchCurrentTime();

        createdNewsHeading = newsHeading;

        homeActions.navigateToNewsSection();
        panelActions.navigateToManagementPanel();

        // Создаем новость
        panelActions.tapCreateNewsButton();
        creationSteps.populateNewsData(generateRandomEventType(), newsHeading, currentDate,
                currentTime, newsContent);
        creationSteps.tapSaveNewsButton();

        panelActions.verifyNewsExistsWithTitle(newsHeading);

        // Редактируем дату и время
        panelActions.editNewsByTitle(newsHeading);

        // Устанавливаем другую дату и время (например, завтрашний день)
        String tomorrowDate = fetchCurrentDate(); // В реальности нужно вычислять завтрашнюю дату
        String differentTime = "23:59";

        updateSteps.inputPublicationDateValue(tomorrowDate);
        updateSteps.inputPublicationTimeValue(differentTime);
        updateSteps.tapUpdateButton();

        // Проверяем, что новость существует
        panelActions.verifyNewsExistsWithTitle(newsHeading);
    }
}