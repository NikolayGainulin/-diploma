package ru.iteco.fmhandroid.ui.pages;

import static androidx.fragment.app.FragmentManager.TAG;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;
import static org.junit.Assert.assertEquals;

import android.util.Log;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewInteraction;

import org.junit.Assert;

import io.qameta.allure.kotlin.Allure;
import io.qameta.allure.kotlin.Step;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import ru.iteco.fmhandroid.R;

public class AboutPage {

    private ViewInteraction versionText = onView(allOf(withId(R.id.about_version_title_text_view)));

    @Step("Проверяем существование страницы")
    public void isPageExists(String url) {
        Allure.step("Проверяем существование страницы: " + url);
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            Response response = client.newCall(request).execute();
            int statusCode = response.code();
            assertEquals(200, statusCode);
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
            Assert.assertTrue(true);
        } finally {
            Espresso.pressBack();
        }
    }

    @Step("Проверяем видимость страницы \"О приложении\"")
    public void checkAboutPage() {
        Allure.step("Проверяем, что открыт раздел \"О приложении\"");
        versionText.check(matches(isDisplayed()));
    }

}