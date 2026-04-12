package ru.iteco.fmhandroid.ui.tests;

import static ru.iteco.fmhandroid.ui.activity.TestUtilities.RandomDataGenerator.generateRandomEventType;
import static ru.iteco.fmhandroid.ui.activity.TestUtilities.fetchCurrentDate;
import static ru.iteco.fmhandroid.ui.activity.TestUtilities.fetchCurrentTime;

import android.view.View;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.qameta.allure.android.rules.ScreenshotRule;
import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.Feature;
import io.qameta.allure.kotlin.Story;
import ru.iteco.fmhandroid.ui.AppActivity;
import ru.iteco.fmhandroid.ui.steps.LoginStepActions;
import ru.iteco.fmhandroid.ui.steps.ManagementPanelActions;
import ru.iteco.fmhandroid.ui.steps.NewsCreationStepMethods;
import ru.iteco.fmhandroid.ui.steps.LoadingScreenSteps;
import ru.iteco.fmhandroid.ui.steps.HomeScreenActions;

@RunWith(AllureAndroidJUnit4.class)
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

    @Before
    public void initializeTestEnvironment() {
        loadingSteps.waitForApplicationLoad();
        try {
            homeActions.waitForHomeScreenLoad();
        } catch (Exception e) {
            loginActions.performValidLogin();
            homeActions.waitForHomeScreenLoad();
        }
        activityScenarioRule.getScenario().onActivity(activity -> rootDecorView = activity.getWindow().getDecorView());
    }

    /* Тест проверяет процесс перехода к созданию новости и наличие всех необходимых элементов на странице */
    @Test
    @Feature(value = "Тесты по разделу Новостей")
    @Story("Переход к созданию новости и Наличие всех элементов")
    public void verifyAllComponentsArePresentOnNewsCreationScreen() {
        homeActions.navigateToNewsSection();
        panelActions.navigateToManagementPanel();
        panelActions.tapCreateNewsButton();
        creationSteps.verifyCreateNewsPageContentIsComplete();
    }

    /* Тест проверяет создание новости с валидными данными */
    @Test
    @Feature(value = "Тесты по разделу Новостей")
    @Story("Cоздание новости с валидными данными")
    public void shouldCreateNewsSuccessfullyWithValidData() {
        String currentDate = fetchCurrentDate();
        String currentTime = fetchCurrentTime();
        String newsHeading = "Новость тест";
        String newsContent = "Описание новости тест";

        homeActions.navigateToNewsSection();
        panelActions.navigateToManagementPanel();
        panelActions.tapCreateNewsButton();
        creationSteps.populateNewsData(generateRandomEventType(), newsHeading, currentDate,
                currentTime, newsContent);
        creationSteps.tapSaveNewsButton();
        panelActions.verifyNewsExistsWithTitle(newsHeading);
    }

    /* Тест проверяет попытку создания новости без ввода данных */
    @Test
    @Feature(value = "Тесты по разделу Новостей")
    @Story("Cоздание новости с пустыми данными")
    public void shouldFailToCreateNewsWithEmptyFieldsAndDisplayWarning() {
        homeActions.navigateToNewsSection();
        panelActions.navigateToManagementPanel();
        panelActions.tapCreateNewsButton();
        creationSteps.tapSaveNewsButton();
        creationSteps.verifyPopupMessageContent("Заполните пустые поля", rootDecorView);
    }

    /* Тест проверяет функционал отмены создания новости */
    @Test
    @Feature(value = "Тесты по разделу Новостей")
    @Story("Отменить создание новости")
    public void shouldReturnToControlPanelWhenCancellingNewsCreation() {
        homeActions.navigateToNewsSection();
        panelActions.navigateToManagementPanel();
        panelActions.tapCreateNewsButton();
        creationSteps.tapCancelNewsButton();
        creationSteps.confirmDialogAction();
        panelActions.verifyManagementPanelContentIsComplete();
    }
}