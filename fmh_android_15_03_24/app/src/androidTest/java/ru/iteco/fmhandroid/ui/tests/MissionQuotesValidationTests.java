package ru.iteco.fmhandroid.ui.tests;

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
import ru.iteco.fmhandroid.ui.steps.LoadingScreenSteps;
import ru.iteco.fmhandroid.ui.steps.HomeScreenActions;
import ru.iteco.fmhandroid.ui.steps.MissionQuotesActions;

@RunWith(AllureAndroidJUnit4.class)
public class MissionQuotesValidationTests {

    LoadingScreenSteps loadingSteps = new LoadingScreenSteps();
    LoginStepActions loginActions = new LoginStepActions();
    HomeScreenActions homeActions = new HomeScreenActions();
    MissionQuotesActions quotesActions = new MissionQuotesActions();
    
    @Rule
    public ActivityScenarioRule<AppActivity> activityScenarioRule =
            new ActivityScenarioRule<>(AppActivity.class);
    
    @Rule
    public ScreenshotRule screenshotRule = new ScreenshotRule(ScreenshotRule.Mode.FAILURE,
            String.valueOf(System.currentTimeMillis()));

    @Before
    public void initializeTestEnvironment() {
        loadingSteps.waitForApplicationLoad();
        try {
            homeActions.waitForHomeScreenLoad();
        } catch (Exception e) {
            loginActions.performValidLogin();
            homeActions.waitForHomeScreenLoad();
        }
        homeActions.navigateToQuotesSection();
    }

    /* Тест проверяет наличие всех элементов на странице с цитатами */
    @Test
    @Feature(value = "Тесты по странице с цитатами")
    @Story("Наличие всех элементов страницы")
    public void verifyAllComponentsArePresentOnQuotesPage() {
        quotesActions.verifyQuotesScreenContentIsComplete();
    }

    /* Тест выполняет проверку функциональности раскрытия и сворачивания цитаты на странице */
    @Test
    @Feature(value = "Тесты по странице с цитатами")
    @Story("Развернуть цитату и свернуть")
    public void verifyQuoteExpansionAndCollapseFunctionality() {
        String expectedQuoteText = "\"Ну, идеальное устройство мира в моих глазах. Где никто не оценивает, никто не осудит, где говоришь, и тебя слышат, где, если страшно, тебя обнимут и возьмут за руку, а если холодно тебя согреют.” Юля Капис, волонтер";

        quotesActions.expandCollapseQuoteItem(0);
        quotesActions.verifyQuoteDescriptionIsVisible(expectedQuoteText);
        quotesActions.expandCollapseQuoteItem(0);
    }
}