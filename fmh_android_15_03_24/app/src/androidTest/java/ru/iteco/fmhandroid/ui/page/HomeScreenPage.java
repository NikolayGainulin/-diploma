package ru.iteco.fmhandroid.ui.page;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.espresso.ViewInteraction;

import ru.iteco.fmhandroid.R;

public class HomeScreenPage {

    public ViewInteraction appLogo;

    public ViewInteraction userAvatarButton;
    public ViewInteraction signOutOption;

    public ViewInteraction navigationMenuButton;
    public ViewInteraction homeMenuItem;
    public ViewInteraction newsMenuItem;
    public ViewInteraction aboutMenuItem;

    public ViewInteraction companyMissionButton;

    public ViewInteraction newsSectionHeader;
    public ViewInteraction viewAllNewsLink;
    public ViewInteraction expandCollapseButton;

    public HomeScreenPage() {
        appLogo = onView(withId(R.id.trademark_image_view));
        userAvatarButton = onView(withId(R.id.authorization_image_button));
        signOutOption = onView(withText("Выйти"));
        navigationMenuButton = onView(withId(R.id.main_menu_image_button));
        homeMenuItem = onView(withText("Главная"));
        newsMenuItem = onView(withText("Новости"));
        aboutMenuItem = onView(withText("О приложении"));
        companyMissionButton = onView(withId(R.id.our_mission_image_button));
        newsSectionHeader = onView(withText("Новости"));
        viewAllNewsLink = onView(withId(R.id.all_news_text_view));
        expandCollapseButton = onView(withId(R.id.expand_material_button));
    }
}