package ru.iteco.fmhandroid.ui.pages;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.doubleClick;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertEquals;
import static ru.iteco.fmhandroid.ui.data.DataHelper.getRecyclerViewItemCount;

import androidx.test.espresso.ViewInteraction;

import io.qameta.allure.kotlin.Allure;
import io.qameta.allure.kotlin.Step;
import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.ui.data.DataHelper;

public class NewsPage {

    private ViewInteraction editNewsButton = onView(withId(R.id.edit_news_material_button));
    private ViewInteraction filterNewsButton = onView(withId(R.id.filter_news_material_button));
    public int newsListId = R.id.news_list_recycler_view;

    @Step("Редактировать новость")
    public void goToNewsEditingPage() {
        Allure.step("Тап на кнопку \"Карандаш\"");
        editNewsButton.perform(click());
    }

    @Step("Проверяем видимость страницы \"Новости\"")
    public void checkNewsPage() {
        Allure.step("Проверяем, что открыт раздел \"Все новости\"");
        filterNewsButton.check(matches(isDisplayed()));
    }


    public int getItemCount() {      //Получаем количество элементов в списке новостей
        int itemCount = getRecyclerViewItemCount(newsListId);
        return itemCount;
    }

}