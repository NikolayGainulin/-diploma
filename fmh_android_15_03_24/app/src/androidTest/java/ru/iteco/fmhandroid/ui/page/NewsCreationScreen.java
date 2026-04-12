package ru.iteco.fmhandroid.ui.page;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.espresso.ViewInteraction;

import ru.iteco.fmhandroid.R;

public class NewsCreationScreen {

    public ViewInteraction headerTitle;

    public ViewInteraction categoryField;
    public ViewInteraction headingField;
    public ViewInteraction contentField;

    public ViewInteraction publishDateField;
    public ViewInteraction publishTimeField;
    public ViewInteraction activeToggle;
    public ViewInteraction submitButton;
    public ViewInteraction discardButton;

    public ViewInteraction validationError;

    public ViewInteraction confirmDialogButton;
    public ViewInteraction cancelDialogButton;

    public NewsCreationScreen() {

        headerTitle = onView(withId(R.id.custom_app_bar_title_text_view));

        categoryField =
                onView(withId(R.id.news_item_category_text_auto_complete_text_view));
        headingField = onView(withId(R.id.news_item_title_text_input_edit_text));
        contentField =
                onView(withId(R.id.news_item_description_text_input_edit_text));

        publishDateField = onView(withId(R.id.news_item_publish_date_text_input_edit_text));
        publishTimeField = onView(withId(R.id.news_item_publish_time_text_input_edit_text));
        activeToggle = onView(withId(R.id.switcher));
        submitButton = onView(withId(R.id.save_button));
        discardButton = onView(withId(R.id.cancel_button));
        validationError = onView(withId(R.id.message));
        confirmDialogButton = onView(withText("OK"));
        cancelDialogButton = onView(withText("Отмена"));
    }
}