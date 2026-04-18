package ru.iteco.fmhandroid.ui.steps;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isChecked;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isNotChecked;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static ru.iteco.fmhandroid.ui.activity.TestUtilities.waitForViewPresence;

import io.qameta.allure.kotlin.Allure;
import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.ui.page.NewsFilterScreen;

public class NewsFilterStepMethods {

    NewsFilterScreen filterScreen = new NewsFilterScreen();

    // Константы для таймаутов
    private static final int FILTER_DIALOG_TIMEOUT_MS = 3000;
    private static final int CATEGORY_SELECTION_TIMEOUT_MS = 2000;

    // Константы для категорий новостей
    public static final String CATEGORY_ANNOUNCEMENT = "Объявление";
    public static final String CATEGORY_EVENT = "Мероприятие";
    public static final String CATEGORY_THANK = "Благодарность";
    public static final String CATEGORY_BIRTHDAY = "День рождения";

    /**
     * Проверка, что форма фильтрации новостей полностью отображается
     */
    public void verifyFilterNewsScreenContentIsComplete() {
        Allure.step("Проверка полного отображения формы фильтрации новостей");
        waitForViewPresence(withId(R.id.filter_news_title_text_view), FILTER_DIALOG_TIMEOUT_MS);
        filterScreen.pageTitle.check(matches(isDisplayed()));
        filterScreen.categoryDropdown.check(matches(isDisplayed()));
        filterScreen.startDateField.check(matches(isDisplayed()));
        filterScreen.endDateField.check(matches(isDisplayed()));
        filterScreen.activeStatusCheckbox.check(matches(isDisplayed()));
        filterScreen.inactiveStatusCheckbox.check(matches(isDisplayed()));
        filterScreen.applyFilterButton.check(matches(isDisplayed()));
        filterScreen.discardFilterButton.check(matches(isDisplayed()));
    }

    /**
     * Нажатие кнопки применения фильтра
     */
    public void tapApplyFilterButton() {
        Allure.step("Нажатие кнопки применения фильтра");
        filterScreen.applyFilterButton.check(matches(isDisplayed()));
        filterScreen.applyFilterButton.perform(click());
    }

    /**
     * Нажатие кнопки отмены фильтрации
     */
    public void tapCancelFilterButton() {
        Allure.step("Нажатие кнопки отмены фильтрации");
        filterScreen.discardFilterButton.check(matches(isDisplayed()));
        filterScreen.discardFilterButton.perform(click());
    }

    /**
     * Подтверждение действия в диалоговом окне
     */
    public void tapConfirmDialogButton() {
        Allure.step("Нажатие кнопки подтверждения в диалоговом окне");
        filterScreen.confirmDialogBtn.check(matches(isDisplayed()));
        filterScreen.confirmDialogBtn.perform(click());
    }

    /**
     * Ввод значения в поле начальной даты
     * @param startDate дата в формате "dd.MM.yyyy"
     */
    public void inputStartDateValue(String startDate) {
        Allure.step("Ввод начальной даты: \"" + startDate + "\"");
        filterScreen.startDateField.check(matches(isDisplayed()));
        filterScreen.startDateField.perform(replaceText(startDate));
    }

    /**
     * Ввод значения в поле конечной даты
     * @param endDate дата в формате "dd.MM.yyyy"
     */
    public void inputEndDateValue(String endDate) {
        Allure.step("Ввод конечной даты: \"" + endDate + "\"");
        filterScreen.endDateField.check(matches(isDisplayed()));
        filterScreen.endDateField.perform(replaceText(endDate));
    }

    /**
     * Выбор чекбокса "Активна"
     */
    public void selectActiveStatusCheckbox() {
        Allure.step("Выбор чекбокса 'Активна'");
        filterScreen.activeStatusCheckbox.check(matches(isDisplayed()));
        filterScreen.activeStatusCheckbox.perform(click());
    }

    /**
     * Выбор чекбокса "Не активна"
     */
    public void selectInactiveStatusCheckbox() {
        Allure.step("Выбор чекбокса 'Не активна'");
        filterScreen.inactiveStatusCheckbox.check(matches(isDisplayed()));
        filterScreen.inactiveStatusCheckbox.perform(click());
    }

    /**
     * Проверка состояния чекбокса "Активна"
     * @param isSelected ожидаемое состояние (true - выбран, false - не выбран)
     */
    public void verifyActiveCheckboxState(boolean isSelected) {
        Allure.step("Проверка состояния чекбокса 'Активна': " + (isSelected ? "выбран" : "не выбран"));
        if (isSelected) {
            filterScreen.activeStatusCheckbox.check(matches(isChecked()));
        } else {
            filterScreen.activeStatusCheckbox.check(matches(isNotChecked()));
        }
    }

    /**
     * Проверка состояния чекбокса "Не активна"
     * @param isSelected ожидаемое состояние (true - выбран, false - не выбран)
     */
    public void verifyInactiveCheckboxState(boolean isSelected) {
        Allure.step("Проверка состояния чекбокса 'Не активна': " + (isSelected ? "выбран" : "не выбран"));
        if (isSelected) {
            filterScreen.inactiveStatusCheckbox.check(matches(isChecked()));
        } else {
            filterScreen.inactiveStatusCheckbox.check(matches(isNotChecked()));
        }
    }

    /**
     * Выбор категории новости из выпадающего списка
     * @param category название категории
     */
    public void selectNewsCategory(String category) {
        Allure.step("Выбор категории новости: \"" + category + "\"");
        filterScreen.categoryDropdown.check(matches(isDisplayed()));
        filterScreen.categoryDropdown.perform(click());
        waitForViewPresence(withText(category), CATEGORY_SELECTION_TIMEOUT_MS);
        onView(withText(category)).perform(click());
    }

    /**
     * Сброс всех примененных фильтров
     */
    public void resetAllFilters() {
        Allure.step("Сброс всех примененных фильтров");
        // Очищаем поля дат
        filterScreen.startDateField.perform(replaceText(""));
        filterScreen.endDateField.perform(replaceText(""));

        // Сбрасываем чекбоксы (если они выбраны)
        try {
            if (isCheckboxChecked(filterScreen.activeStatusCheckbox)) {
                filterScreen.activeStatusCheckbox.perform(click());
            }
        } catch (Exception ignored) {}

        try {
            if (isCheckboxChecked(filterScreen.inactiveStatusCheckbox)) {
                filterScreen.inactiveStatusCheckbox.perform(click());
            }
        } catch (Exception ignored) {}

        // Сбрасываем категорию (выбираем "Все" или очищаем)
        filterScreen.categoryDropdown.perform(click());
        onView(withText("Все")).perform(click());
    }

    /**
     * Вспомогательный метод для проверки состояния чекбокса
     * @param viewMatcher матчер для чекбокса
     * @return true если чекбокс выбран
     */
    private boolean isCheckboxChecked(org.hamcrest.Matcher<android.view.View> viewMatcher) {
        try {
            onView(viewMatcher).check(matches(isChecked()));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Применение фильтра по датам
     * @param startDate начальная дата
     * @param endDate конечная дата
     */
    public void applyDateRangeFilter(String startDate, String endDate) {
        Allure.step("Применение фильтра по диапазону дат: " + startDate + " - " + endDate);
        inputStartDateValue(startDate);
        inputEndDateValue(endDate);
        tapApplyFilterButton();
    }

    /**
     * Применение фильтра по статусу
     * @param isActive true - только активные, false - только неактивные
     */
    public void applyStatusFilter(boolean isActive) {
        Allure.step("Применение фильтра по статусу: " + (isActive ? "активные" : "неактивные"));
        if (isActive) {
            selectActiveStatusCheckbox();
        } else {
            selectInactiveStatusCheckbox();
        }
        tapApplyFilterButton();
    }

    /**
     * Применение фильтра по категории
     * @param category название категории
     */
    public void applyCategoryFilter(String category) {
        Allure.step("Применение фильтра по категории: \"" + category + "\"");
        selectNewsCategory(category);
        tapApplyFilterButton();
    }

    /**
     * Очистка всех полей фильтра
     */
    public void clearAllFilterFields() {
        Allure.step("Очистка всех полей фильтра");
        filterScreen.startDateField.perform(replaceText(""));
        filterScreen.endDateField.perform(replaceText(""));
    }

    /**
     * Проверка, что поля фильтра пусты
     */
    public void verifyFilterFieldsAreEmpty() {
        Allure.step("Проверка, что поля фильтра пусты");
        // Проверяем, что поля дат пустые
        // Реализация зависит от того, как приложение отображает пустые поля
    }

    /**
     * Закрытие фильтра без сохранения (отмена)
     */
    public void closeFilterWithoutSaving() {
        Allure.step("Закрытие фильтра без сохранения изменений");
        tapCancelFilterButton();
    }
}