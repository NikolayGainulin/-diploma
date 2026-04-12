package ru.iteco.fmhandroid.ui.tests;

import static ru.iteco.fmhandroid.ui.activity.TestUtilities.RandomDataGenerator.generateRandomEventType;
import static ru.iteco.fmhandroid.ui.activity.TestUtilities.fetchCurrentDate;
import static ru.iteco.fmhandroid.ui.activity.TestUtilities.fetchCurrentTime;

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

    @Before
    public void initializeTestEnvironment() {
        loadingSteps.waitForApplicationLoad();
        try {
            homeActions.waitForHomeScreenLoad();
        } catch (Exception e) {
            loginActions.performValidLogin();
            homeActions.waitForHomeScreenLoad();
        }
    }

    /* Тест проверяет процесс удаления новости */
    @Test
    @Feature(value = "Тесты по разделу Новостей")
    @Story("Удаление новости")
    public void shouldDeleteNewsSuccessfullyAndConfirmRemoval() {
        String currentDate = fetchCurrentDate();
        String currentTime = fetchCurrentTime();
        String newsHeading = "Новость тест2";
        String newsContent = "Описание новости тест2";

        homeActions.navigateToNewsSection();
        panelActions.navigateToManagementPanel();
        panelActions.tapCreateNewsButton();

        creationSteps.populateNewsData(generateRandomEventType(), newsHeading, currentDate,
                currentTime, newsContent);
        creationSteps.tapSaveNewsButton();

        panelActions.removeNewsByTitle(newsHeading);
        panelActions.verifyManagementPanelContentIsComplete();
        panelActions.verifyNewsDoesNotExistWithTitle(newsHeading);
    }
}