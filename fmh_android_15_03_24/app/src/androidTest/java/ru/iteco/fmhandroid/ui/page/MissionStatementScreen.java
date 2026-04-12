package ru.iteco.fmhandroid.ui.page;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static ru.iteco.fmhandroid.ui.activity.TestUtilities.locateChildAtPosition;

import androidx.test.espresso.ViewInteraction;

import ru.iteco.fmhandroid.R;

public class MissionStatementScreen {
    
    public ViewInteraction brandLogo;
    public ViewInteraction pageHeader;
    public ViewInteraction quotesRecyclerView;
    public ViewInteraction quotesContainerLayout;

    public MissionStatementScreen() {
        brandLogo = onView(withId(R.id.trademark_image_view));
        pageHeader = onView(withId(R.id.our_mission_title_text_view));
        quotesRecyclerView = onView(withId(R.id.our_mission_item_list_recycler_view));
        quotesContainerLayout = onView(allOf(withId(R.id.our_mission_item_list_recycler_view),
                locateChildAtPosition(withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")), 0)));
    }
}