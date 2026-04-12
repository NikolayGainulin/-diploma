package ru.iteco.fmhandroid.ui.page;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.espresso.ViewInteraction;

import ru.iteco.fmhandroid.R;

public class NewsFilterScreen {
    
    public ViewInteraction pageTitle;
    public ViewInteraction categoryDropdown;
    public ViewInteraction startDateField;
    public ViewInteraction endDateField;
    public ViewInteraction activeStatusCheckbox;
    public ViewInteraction inactiveStatusCheckbox;
    public ViewInteraction applyFilterButton;
    public ViewInteraction discardFilterButton;
    
    public ViewInteraction confirmDialogBtn;
    public ViewInteraction cancelDialogBtn;
    
    public NewsFilterScreen() {
        pageTitle = onView(withId(R.id.filter_news_title_text_view));
        categoryDropdown =
                onView(withId(R.id.news_item_category_text_auto_complete_text_view));
        startDateField =
                onView(withId(R.id.news_item_publish_date_start_text_input_edit_text));
        endDateField =
                onView(withId(R.id.news_item_publish_date_end_text_input_edit_text));
        activeStatusCheckbox =
                onView(withId(R.id.filter_news_active_material_check_box));
        inactiveStatusCheckbox =
                onView(withId(R.id.filter_news_inactive_material_check_box));
        applyFilterButton = onView(withId(R.id.filter_button));
        discardFilterButton = onView(withId(R.id.cancel_button));
        
        confirmDialogBtn = onView(withText("OK"));
        cancelDialogBtn = onView(withText("Отмена"));
    }
}