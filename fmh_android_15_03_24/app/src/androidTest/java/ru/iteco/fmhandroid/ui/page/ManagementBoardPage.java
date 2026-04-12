package ru.iteco.fmhandroid.ui.page;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withChild;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static ru.iteco.fmhandroid.ui.activity.TestUtilities.locateChildAtPosition;
import static ru.iteco.fmhandroid.ui.activity.TestUtilities.findByIndex;

import androidx.test.espresso.ViewInteraction;

import ru.iteco.fmhandroid.R;

public class ManagementBoardPage {

    public ViewInteraction brandLogo;
    public ViewInteraction pageHeader;
    public ViewInteraction newsRecyclerList;

    public ViewInteraction orderToggleButton;
    public ViewInteraction filterToggleButton;
    public ViewInteraction createNewsButton;

    public ViewInteraction newsContainer;
    public ViewInteraction newsHeadline;
    public ViewInteraction newsBody;
    public ViewInteraction publishDateValue;
    public ViewInteraction createDateValue;
    public ViewInteraction newsAuthor;
    public ViewInteraction activeStatusBadge;
    public ViewInteraction inactiveStatusBadge;

    public ManagementBoardPage() {

        brandLogo = onView(withId(R.id.trademark_image_view));
        pageHeader = onView(withText("Панель управления"));
        newsRecyclerList = onView(withId(R.id.news_list_recycler_view));
        orderToggleButton = onView(withId(R.id.sort_news_material_button));
        filterToggleButton = onView(withId(R.id.filter_news_material_button));
        createNewsButton = onView(withId(R.id.add_news_image_view));
        newsContainer = onView(allOf(withId(R.id.news_list_recycler_view),
                locateChildAtPosition(withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")), 0)));
        newsHeadline =
                onView(findByIndex(withId(R.id.news_item_title_text_view), 0));
        newsBody = onView(findByIndex(withId(R.id.news_item_description_text_view), 0));
        publishDateValue = onView(findByIndex(withId(R.id.news_item_publication_text_view), 0));
        createDateValue = onView(withId(R.id.news_item_creation_text_view));
        newsAuthor = onView(withId(R.id.news_item_author_text_view));
        activeStatusBadge =
                onView(findByIndex(withId(R.id.news_item_published_text_view), 0));
        inactiveStatusBadge =
                onView(findByIndex(withId(R.id.news_item_published_text_view), 0));
    }

    public ViewInteraction removeNewsItem(String newsHeading) {
        return onView(allOf(withId(R.id.delete_news_item_image_view),
                withParent(withParent(allOf(withId(R.id.news_item_material_card_view),
                        withChild(withChild(withText(newsHeading))))))));
    }

    public ViewInteraction modifyNewsItem(String newsHeading) {
        return onView(allOf(withId(R.id.edit_news_item_image_view),
                withParent(withParent(allOf(withId(R.id.news_item_material_card_view),
                        withChild(withChild(withText(newsHeading))))))));
    }
}