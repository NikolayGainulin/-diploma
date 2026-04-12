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
import ru.iteco.fmhandroid.ui.steps.ManagementPanelActions;
import ru.iteco.fmhandroid.ui.steps.LoadingScreenSteps;
import ru.iteco.fmhandroid.ui.steps.NewsFilterStepMethods;
import ru.iteco.fmhandroid.ui.steps.HomeScreenActions;
import ru.iteco.fmhandroid.ui.steps.NewsListingActions;

@RunWith(AllureAndroidJUnit4.class)
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
        try {
            homeActions.waitForHomeScreenLoad();
        } catch (Exception e) {
            loginActions.performValidLogin();
            homeActions.waitForHomeScreenLoad();
        }
    }
    
    /* Тест проверяет возможность перехода ко всем новостям с главной страницы */
    @Test
    @Feature(value = "Тесты по разделу Новостей")
    @Story("Переход ко всем новостям с главной страницы")
    public void verifyAllNewsAccessFromHomeScreen() {
        homeActions.viewAllNewsItems();
    }

    /* Тест проверяет функциональность разворачивания (раскрытия) отдельной новости на главной странице */
    @Test
    @Feature(value = "Тесты по разделу Новостей")
    @Story("Развернуть новость на главной странице")
    public void verifySingleNewsExpansionOnHomeScreen() {
        newsActions.expandCollapseNewsItem(0);
    }

    /* Тест проверяет, что все элементы на странице новостей присутствуют и корректно отображаются */
    @Test
    @Feature(value = "Тесты по разделу Новостей")
    @Story("Наличие всех элементов на странице Новости")
    public void verifyCompleteContentDisplayOnNewsPage() {
        homeActions.navigateToNewsSection();
        newsActions.verifyNewsScreenContentIsComplete();
    }

    /* Тест проверяет возможность перехода на страницу панели управления и наличие всех элементов на этой странице */
    @Test
    @Feature(value = "Тесты по разделу Новостей")
    @Story("Переход на страницу Панели управления и Наличие всех элементов")
    public void verifyAccessToManagementPanelWithAllComponents() {
        homeActions.navigateToNewsSection();
        panelActions.navigateToManagementPanel();
        panelActions.verifyManagementPanelContentIsComplete();
    }

    /* Тест проверяет возврат на главную страницу со страницы новостей */
    @Test
    @Feature(value = "Тесты по разделу Новостей")
    @Story("Возврат на Главную страницу со страницы Новости")
    public void verifyReturnToHomeScreenFromNewsPageWithFullContentCheck() {
        homeActions.navigateToNewsSection();
        newsActions.verifyNewsScreenContentIsComplete();
        newsActions.navigateBackToHomeScreen();
        homeActions.verifyHomeScreenContentIsComplete();
    }

    /* Тест проверяет функциональность отмены фильтрации новостей */
    @Test
    @Feature(value = "Тесты по разделу Новостей")
    @Story("Выход из фильтра без фильтрации новостей")
    public void verifyCancelNewsFilterWithoutApplyingChanges() {
        homeActions.navigateToNewsSection();
        panelActions.navigateToManagementPanel();
        panelActions.openNewsFilterDialog();
        filterActions.tapCancelFilterButton();
        panelActions.verifyManagementPanelContentIsComplete();
    }
}