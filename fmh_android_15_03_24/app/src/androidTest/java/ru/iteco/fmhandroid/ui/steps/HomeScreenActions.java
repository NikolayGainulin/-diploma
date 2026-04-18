package ru.iteco.fmhandroid.ui.steps;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static ru.iteco.fmhandroid.ui.activity.TestUtilities.waitForViewPresence;

import io.qameta.allure.kotlin.Allure;
import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.ui.page.HomeScreenPage;

public class HomeScreenActions {

    HomeScreenPage homeScreen = new HomeScreenPage();

    public void waitForHomeScreenLoad() {
        Allure.step("Загрузка страницы");
        waitForViewPresence(withId(R.id.all_news_text_view), 5000);
    }

    public void verifyHomeScreenContentIsComplete() {
        Allure.step("Проверка, что в блоке \"Главная\" полный контент");
        homeScreen.userAvatarButton.check(matches(isDisplayed()));
        homeScreen.navigationMenuButton.check(matches(isDisplayed()));
        homeScreen.companyMissionButton.check(matches(isDisplayed()));
        homeScreen.newsSectionHeader.check(matches(isDisplayed()));
        homeScreen.viewAllNewsLink.check(matches(isDisplayed()));
    }

    public void navigateToNewsSection() {
        Allure.step("Открытие раздела \"Новости\"");
        homeScreen.navigationMenuButton.perform(click());
        homeScreen.newsMenuItem.perform(click());
    }

    public void navigateToAboutSection() {
        Allure.step("Открытие раздела \"О приложении\"");
        homeScreen.navigationMenuButton.perform(click());
        homeScreen.aboutMenuItem.perform(click());
    }

    public void navigateToQuotesSection() {
        Allure.step("Открытие раздела \"Цитаты\"");
        homeScreen.companyMissionButton.perform(click());
    }

    public void performLogout() {
        Allure.step("Выход из профиля");
        homeScreen.userAvatarButton.perform(click());
        homeScreen.signOutOption.perform(click());
    }

    public void viewAllNewsItems() {
        Allure.step("Переход ко всем новостям");
        homeScreen.viewAllNewsLink.perform(click());
    }
    public void navigateToHomeScreen() {
        Allure.step("Возврат на главный экран");
        try {
            navigationMenuButton.perform(click());
            homeMenuItem.perform(click());
        } catch (Exception e) {
            // Already on home screen or menu not available
        }
}