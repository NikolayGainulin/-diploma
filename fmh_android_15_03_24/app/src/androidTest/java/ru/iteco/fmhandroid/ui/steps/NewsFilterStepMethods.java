package ru.iteco.fmhandroid.ui.steps;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isChecked;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isNotChecked;

import io.qameta.allure.kotlin.Allure;
import ru.iteco.fmhandroid.ui.page.NewsFilterScreen;

public class NewsFilterStepMethods {

    NewsFilterScreen filterScreen = new NewsFilterScreen();

    public void verifyFilterNewsScreenContentIsComplete() {
        Allure.step("Проверка, что в блоке Фильтрации новостей полный контент");
        filterScreen.pageTitle.check(matches(isDisplayed()));
        filterScreen.categoryDropdown.check(matches(isDisplayed()));
        filterScreen.startDateField.check(matches(isDisplayed()));
        filterScreen.endDateField.check(matches(isDisplayed()));
        filterScreen.activeStatusCheckbox.check(matches(isDisplayed()));
        filterScreen.inactiveStatusCheckbox.check(matches(isDisplayed()));
        filterScreen.applyFilterButton.check(matches(isDisplayed()));
        filterScreen.discardFilterButton.check(matches(isDisplayed()));
    }

    public void tapApplyFilterButton() {
        Allure.step("Нажатие кнопки Фильтровать");
        filterScreen.applyFilterButton.perform(click());
    }

    public void tapCancelFilterButton() {
        Allure.step("Нажатие кнопки Отмена");
        filterScreen.discardFilterButton.perform(click());
    }

    public void tapConfirmDialogButton() {
        Allure.step("Нажатие кнопки ОК в сообщении");
        filterScreen.confirmDialogBtn.perform(click());
    }

    public void inputStartDateValue(String startDate) {
        Allure.step("Поле начальная дата - ввод данных");
        filterScreen.startDateField.perform(replaceText(startDate));
    }

    public void inputEndDateValue(String endDate) {
        Allure.step("Поле конечная дата - ввод данных");
        filterScreen.endDateField.perform(replaceText(endDate));
    }

    public void selectActiveStatusCheckbox() {
        Allure.step("Нажать чекбокс - Активна");
        filterScreen.activeStatusCheckbox.perform(click());
    }

    public void selectInactiveStatusCheckbox() {
        Allure.step("Нажать чекбокс - НЕ активна");
        filterScreen.inactiveStatusCheckbox.perform(click());
    }

    public void verifyActiveCheckboxState(boolean isSelected) {
        Allure.step("Проверка нажатия - Активна");
        if (isSelected) {
            filterScreen.activeStatusCheckbox.check(matches(isChecked()));
        } else {
            filterScreen.activeStatusCheckbox.check(matches(isNotChecked()));
        }
    }

    public void verifyInactiveCheckboxState(boolean isSelected) {
        Allure.step("Проверка нажатия - НЕ активна");
        if (isSelected) {
            filterScreen.inactiveStatusCheckbox.check(matches(isChecked()));
        } else {
            filterScreen.inactiveStatusCheckbox.check(matches(isNotChecked()));
        }
    }
}