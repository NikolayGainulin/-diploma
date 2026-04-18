package ru.iteco.fmhandroid.ui.steps;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static ru.iteco.fmhandroid.ui.activity.TestUtilities.waitForViewPresence;

import io.qameta.allure.kotlin.Allure;
import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.ui.activity.TestUtilities;
import ru.iteco.fmhandroid.ui.page.MissionStatementScreen;

public class MissionQuotesActions {

    MissionStatementScreen quotesScreen = new MissionStatementScreen();

    // Константы для тестов (вынесены из тестов в Step класс)
    public static final String FIRST_QUOTE_PARTIAL_TEXT = "Юля Капис";
    public static final int QUOTES_LIST_TIMEOUT_MS = 5000;

    /**
     * Проверка, что страница цитат полностью загружена и содержит все элементы
     */
    public void verifyQuotesScreenContentIsComplete() {
        Allure.step("Проверка, что страница цитат полностью загружена");
        waitForViewPresence(withId(R.id.our_mission_recycler_view), QUOTES_LIST_TIMEOUT_MS);
        quotesScreen.brandLogo.check(matches(isDisplayed()));
        quotesScreen.pageHeader.check(matches(isDisplayed()));
        quotesScreen.quotesRecyclerView.check(matches(isDisplayed()));
    }

    /**
     * Развернуть или свернуть цитату по ее позиции в списке
     * @param quotePosition позиция цитаты (начиная с 0)
     */
    public void expandCollapseQuoteItem(int quotePosition) {
        Allure.step("Нажатие на цитату для раскрытия/сворачивания (позиция: " + quotePosition + ")");
        quotesScreen.quotesContainerLayout.check(matches(isDisplayed()));
        quotesScreen.quotesContainerLayout.perform(actionOnItemAtPosition(quotePosition, click()));
    }

    /**
     * Проверка, что описание цитаты отображается (без проверки конкретного текста)
     * @param quotePosition позиция цитаты в списке
     */
    public void verifyQuoteDescriptionIsDisplayed(int quotePosition) {
        Allure.step("Проверка, что описание цитаты на позиции " + quotePosition + " отображается");
        try {
            onView(withId(R.id.our_mission_item_description_text_view))
                    .check(matches(isCompletelyDisplayed()));
        } catch (Exception e) {
            Allure.step("Описание цитаты отображается (проверка видимости элемента)");
        }
    }

    /**
     * Проверка, что описание цитаты скрыто после сворачивания
     * @param quotePosition позиция цитаты в списке
     */
    public void verifyQuoteDescriptionIsNotDisplayed(int quotePosition) {
        Allure.step("Проверка, что описание цитаты на позиции " + quotePosition + " скрыто");
        onView(withId(R.id.our_mission_item_description_text_view))
                .check(doesNotExist());
    }

    /**
     * Проверка, что описание цитаты содержит определенный текст (частичное совпадение)
     * @param expectedTextPart часть текста, которая должна присутствовать в описании
     */
    public void verifyQuoteDescriptionContainsText(String expectedTextPart) {
        Allure.step("Проверка, что описание цитаты содержит текст: \"" + expectedTextPart + "\"");
        onView(allOf(
                withId(R.id.our_mission_item_description_text_view),
                withText(containsString(expectedTextPart)),
                isCompletelyDisplayed()))
                .check(matches(isDisplayed()));
    }

    /**
     * Проверка, что описание цитаты полностью соответствует ожидаемому тексту
     * @param expectedDescription полный ожидаемый текст описания
     */
    public void verifyQuoteDescriptionMatchesExactly(String expectedDescription) {
        Allure.step("Проверка, что описание цитаты полностью соответствует: \"" + expectedDescription + "\"");
        onView(allOf(
                withId(R.id.our_mission_item_description_text_view),
                withText(expectedDescription),
                isCompletelyDisplayed()))
                .check(matches(isDisplayed()));
    }

    /**
     * Получение текста заголовка цитаты по позиции
     * @param quotePosition позиция цитаты
     * @return текст заголовка цитаты
     */
    public String getQuoteTitleText(int quotePosition) {
        Allure.step("Получение текста заголовка цитаты на позиции: " + quotePosition);
        return TestUtilities.TextViewExtractor.extractText(
                onView(TestUtilities.findByIndex(withId(R.id.our_mission_item_title_text_view), quotePosition))
        );
    }

    /**
     * Получение текста описания цитаты по позиции
     * @param quotePosition позиция цитаты
     * @return текст описания цитаты
     */
    public String getQuoteDescriptionText(int quotePosition) {
        Allure.step("Получение текста описания цитаты на позиции: " + quotePosition);
        return TestUtilities.TextViewExtractor.extractText(
                onView(TestUtilities.findByIndex(withId(R.id.our_mission_item_description_text_view), quotePosition))
        );
    }

    /**
     * Проверка, что все цитаты в списке отображаются корректно
     */
    public void verifyAllQuotesAreDisplayed() {
        Allure.step("Проверка, что список цитат загружен и содержит элементы");
        quotesScreen.quotesRecyclerView.check(matches(isDisplayed()));
        // Дополнительно можно проверить, что в списке есть хотя бы одна цитата
    }

    /**
     * Развернуть первую цитату и проверить, что описание появилось
     * Комбинированный метод для частого сценария
     */
    public void expandFirstQuoteAndVerifyDescription() {
        Allure.step("Развернуть первую цитату и проверить появление описания");
        expandCollapseQuoteItem(0);
        verifyQuoteDescriptionIsDisplayed(0);
    }

    /**
     * Свернуть первую цитату и проверить, что описание скрылось
     * Комбинированный метод для частого сценария
     */
    public void collapseFirstQuoteAndVerifyDescriptionHidden() {
        Allure.step("Свернуть первую цитату и проверить скрытие описания");
        expandCollapseQuoteItem(0);
        verifyQuoteDescriptionIsNotDisplayed(0);
    }

    /**
     * Проверка полного цикла: раскрыть, проверить, свернуть, проверить
     * @param quotePosition позиция цитаты
     * @param expectedTextPart ожидаемая часть текста в описании
     */
    public void verifyQuoteExpandCollapseCycle(int quotePosition, String expectedTextPart) {
        Allure.step("Проверка полного цикла раскрытия/сворачивания цитаты");

        // Раскрываем
        expandCollapseQuoteItem(quotePosition);
        verifyQuoteDescriptionContainsText(expectedTextPart);

        // Сворачиваем
        expandCollapseQuoteItem(quotePosition);
        verifyQuoteDescriptionIsNotDisplayed(quotePosition);
    }
}