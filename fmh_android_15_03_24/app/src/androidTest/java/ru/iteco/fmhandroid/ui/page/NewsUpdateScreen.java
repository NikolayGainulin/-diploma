package ru.iteco.fmhandroid.ui.page;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.espresso.ViewInteraction;

import ru.iteco.fmhandroid.R;

public class NewsUpdateScreen {
    
    public ViewInteraction screenHeader;
    public ViewInteraction categorySelector;
    public ViewInteraction headingInput;
    public ViewInteraction contentInput;
    public ViewInteraction dateSelector;
    public ViewInteraction timeSelector;
    public ViewInteraction activeSwitch;
    public ViewInteraction updateButton;
    public ViewInteraction rejectButton;
    
    public ViewInteraction warningMessage;
    
    public ViewInteraction confirmAction;
    public ViewInteraction dismissAction;
    
    public NewsUpdateScreen() {
        screenHeader = onView(withId(R.id.custom_app_bar_title_text_view));
        categorySelector =
                onView(withId(R.id.news_item_category_text_auto_complete_text_view));
        headingInput = onView(withId(R.id.news_item_title_text_input_edit_text));
        contentInput =
                onView(withId(R.id.news_item_description_text_input_edit_text));
        
        dateSelector = onView(withId(R.id.news_item_publish_date_text_input_edit_text));
        timeSelector = onView(withId(R.id.news_item_publish_time_text_input_edit_text));
        activeSwitch = onView(withId(R.id.switcher));
        updateButton = onView(withId(R.id.save_button));
        rejectButton = onView(withId(R.id.cancel_button));
        warningMessage = onView(withId(R.id.message));
        confirmAction = onView(withText("OK"));
        dismissAction = onView(withText("Отмена"));
    }
}