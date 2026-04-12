package ru.iteco.fmhandroid.ui.steps;

import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static ru.iteco.fmhandroid.ui.activity.TestUtilities.waitForViewPresence;

import io.qameta.allure.kotlin.Allure;
import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.ui.page.LoadingScreenPage;

public class LoadingScreenSteps {

    LoadingScreenPage loadingScreen = new LoadingScreenPage();

    public void verifyLoadingScreenElementsArePresent() {
        Allure.step("Наличие всех элементов экрана загрузки");
        loadingScreen.logoImage.check(matches(isDisplayed()));
        loadingScreen.circularProgressBar.check(matches(isDisplayed()));
        loadingScreen.statusText.check(matches(isDisplayed()));
    }

    public void waitForApplicationLoad() {
        Allure.step("Загрузка приложения");
        waitForViewPresence(withId(R.id.splashscreen_image_view), 10000);
    }
}