package ru.iteco.fmhandroid.ui.steps;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

import io.qameta.allure.kotlin.Allure;
import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.ui.page.MissionStatementScreen;

public class MissionQuotesActions {

    MissionStatementScreen quotesScreen = new MissionStatementScreen();

    public void verifyQuotesScreenContentIsComplete() {
        Allure.step("Проверка, что в блоке с цитатами полный контент");
        quotesScreen.brandLogo.check(matches(isDisplayed()));
        quotesScreen.pageHeader.check(matches(isDisplayed()));
        quotesScreen.quotesRecyclerView.check(matches(isDisplayed()));
    }

    public void expandCollapseQuoteItem(int quotePosition) {
        Allure.step("Развернуть/свернуть цитату");
        quotesScreen.quotesContainerLayout.check(matches(isDisplayed()));
        quotesScreen.quotesContainerLayout.perform(actionOnItemAtPosition(quotePosition, click()));
    }

    public void verifyQuoteDescriptionIsVisible(String expectedDescription) {
        Allure.step("Отображение дополнительной цитаты");
        onView(allOf(
                withId(R.id.our_mission_item_description_text_view),
                withText(expectedDescription),
                isCompletelyDisplayed()))
                .check(matches(isDisplayed()));
    }
}