package ru.iteco.fmhandroid.ui.pages;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.allOf;

import androidx.test.espresso.ViewInteraction;

import io.qameta.allure.kotlin.Allure;
import io.qameta.allure.kotlin.Step;
import ru.iteco.fmhandroid.R;

public class QuotesPage {
    private ViewInteraction quoteText = onView(allOf(withId(R.id.our_mission_title_text_view)));

    @Step("Проверяем видимость страницы \"Тематические цитаты\"")
    public void checkQuotesPage() {
        Allure.step("Проверяем, что открыт раздел \"Тематические цитаты\"");
        quoteText.check(matches(isDisplayed()));
    }
}