package ru.iteco.fmhandroid.ui.page;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.espresso.ViewInteraction;

import ru.iteco.fmhandroid.R;

public class AboutScreenElements {
    
    // Элементы главного экрана
    public ViewInteraction appLogo;
    public ViewInteraction navigateBackButton;
    public ViewInteraction versionLabel;
    public ViewInteraction versionValue;
    public ViewInteraction privacyPolicyLabel;
    public ViewInteraction termsOfUseLabel;
    public ViewInteraction companyInfoText;
    public ViewInteraction privacyPolicyLink;
    public ViewInteraction termsOfUseLink;

    public AboutScreenElements() {
        appLogo = onView(withId(R.id.trademark_image_view));
        navigateBackButton = onView(withId(R.id.about_back_image_button));
        versionLabel = onView(withId(R.id.about_version_title_text_view));
        versionValue = onView(withId(R.id.about_version_value_text_view));
        privacyPolicyLabel = onView(withId(R.id.about_privacy_policy_label_text_view));
        termsOfUseLabel = onView(withId(R.id.about_terms_of_use_label_text_view));
        companyInfoText = onView(withId(R.id.about_company_info_label_text_view));
        privacyPolicyLink = onView(withId(R.id.about_privacy_policy_value_text_view));
        termsOfUseLink = onView(withId(R.id.about_terms_of_use_value_text_view));
    }
}