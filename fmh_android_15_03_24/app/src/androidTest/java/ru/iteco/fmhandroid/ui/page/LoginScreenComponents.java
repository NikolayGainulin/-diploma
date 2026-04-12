package ru.iteco.fmhandroid.ui.page;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.espresso.ViewInteraction;

import ru.iteco.fmhandroid.R;

public class AuthorizationPage {
    public ViewInteraction title;
    public ViewInteraction loginField;
    public ViewInteraction passwordField;
    public ViewInteraction loginButton;

    public AuthorizationPage() {
        title = onView(withText("Авторизация"));
        loginField = onView(withHint("Логин"));
        passwordField = onView(withHint("Пароль"));
        loginButton = onView(withId(R.id.enter_button));
    }package ru.iteco.fmhandroid.ui.page;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.espresso.ViewInteraction;

import ru.iteco.fmhandroid.R;

    public class AuthPageElements {

        private ViewInteraction pageTitle;
        private ViewInteraction userLoginField;
        private ViewInteraction userPasswordField;
        private ViewInteraction signInButton;

        public AuthPageElements() {
            initializeAuthElements();
        }

        private void initializeAuthElements() {
            pageTitle = onView(withText("Авторизация"));
            userLoginField = onView(withHint("Логин"));
            userPasswordField = onView(withHint("Пароль"));
            signInButton = onView(withId(R.id.enter_button));
        }

        // Геттеры для доступа к элементам
        public ViewInteraction getPageTitle() {
            return pageTitle;
        }

        public ViewInteraction getUserLoginField() {
            return userLoginField;
        }

        public ViewInteraction getUserPasswordField() {
            return userPasswordField;
        }

        public ViewInteraction getSignInButton() {
            return signInButton;
        }
    }

}