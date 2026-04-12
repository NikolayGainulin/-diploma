package ru.iteco.fmhandroid.ui.page;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.espresso.ViewInteraction;

import ru.iteco.fmhandroid.R;

public class LoadingScreenPage {

    public ViewInteraction logoImage;
    public ViewInteraction statusText;
    public ViewInteraction circularProgressBar;

    public LoadingScreenPage() {
        logoImage = onView(withId(R.id.splashscreen_image_view));
        statusText = onView(withId(R.id.splashscreen_text_view));
        circularProgressBar =
                onView(withId(R.id.splash_screen_circular_progress_indicator));
    }
}