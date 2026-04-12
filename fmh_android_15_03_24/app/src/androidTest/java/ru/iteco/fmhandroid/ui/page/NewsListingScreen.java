package ru.iteco.fmhandroid.ui.page;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static ru.iteco.fmhandroid.ui.activity.TestUtilities.locateChildAtPosition;

import androidx.test.espresso.ViewInteraction;

import ru.iteco.fmhandroid.R;

public class NewsListingScreen {

    public ViewInteraction brandLogo;
    public ViewInteraction pageHeader;
    public ViewInteraction sortButton;
    public ViewInteraction filterButton;
    public ViewInteraction adminPanelButton;
    public ViewInteraction newsCardsContainer;
    public ViewInteraction nestedNewsFeed;

    public NewsListingScreen() {
        brandLogo = onView(withId(R.id.trademark_image_view));
        pageHeader = onView(withText("Новости"));
        sortButton = onView(withId(R.id.sort_news_material_button));
        filterButton = onView(withId(R.id.filter_news_material_button));
        adminPanelButton = onView(withId(R.id.edit_news_material_button));
        newsCardsContainer = onView(withId(R.id.all_news_cards_block_constraint_layout));
        nestedNewsFeed = onView(allOf(withId(R.id.news_list_recycler_view),
                locateChildAtPosition(withClassName(is("android.widget.LinearLayout")),
                        withId(R.id.all_news_cards_block_constraint_layout), 0)));
    }
}