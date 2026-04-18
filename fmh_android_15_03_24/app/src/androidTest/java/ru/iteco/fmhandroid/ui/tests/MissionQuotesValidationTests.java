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
import ru.iteco.fmhandroid.ui.steps.LoadingScreenSteps;
import ru.iteco.fmhandroid.ui.steps.HomeScreenActions;
import ru.iteco.fmhandroid.ui.steps.MissionQuotesActions;

@RunWith(AllureAndroidJUnit4.class)
@Epic("Функциональные тесты мобильного приложения")
@DisplayName("Тесты страницы с цитатами")
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

        // Проверяем, авторизованы ли мы
        if (!isOnHomeScreen()) {
            loginActions.performValidLogin();
            homeActions.waitForHomeScreenLoad();
        }

        homeActions.navigateToQuotesSection();
        // Подтверждаем, что мы действительно на странице цитат
        quotesActions.verifyQuotesScreenContentIsComplete();
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
     * Тест проверяет наличие всех элементов на странице с цитатами
     */
    @Test
    @Feature(value = "Тесты по странице с цитатами")
    @Story("Наличие всех элементов страницы")
    @DisplayName("Проверка отображения всех элементов на странице цитат")
    public void verifyAllComponentsArePresentOnQuotesPage() {
        quotesActions.verifyQuotesScreenContentIsComplete();
    }

    /**
     * Тест выполняет проверку функциональности раскрытия и сворачивания цитаты на странице
     * Проверяет, что:
     * 1. При клике на цитату появляется ее полное описание
     * 2. При повторном клике описание скрывается
     */
    @Test
    @Feature(value = "Тесты по странице с цитатами")
    @Story("Развернуть цитату и свернуть")
    @DisplayName("Проверка раскрытия и сворачивания цитаты")
    public void verifyQuoteExpansionAndCollapseFunctionality() {
        // Сохраняем позицию цитаты для теста (первая цитата)
        int quotePosition = 0;

        // Проверяем, что описание изначально не отображается (или отображается частично)
        // Раскрываем цитату
        quotesActions.expandCollapseQuoteItem(quotePosition);

        // Проверяем, что описание цитаты появилось
        quotesActions.verifyQuoteDescriptionIsDisplayed(quotePosition);

        // Сворачиваем цитату
        quotesActions.expandCollapseQuoteItem(quotePosition);

        // Проверяем, что описание цитаты скрылось
        quotesActions.verifyQuoteDescriptionIsNotDisplayed(quotePosition);
    }
}