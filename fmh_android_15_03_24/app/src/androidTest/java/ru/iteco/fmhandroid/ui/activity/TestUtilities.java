package ru.iteco.fmhandroid.ui.activity;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.TextView;

import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.PerformException;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.util.HumanReadables;
import androidx.test.espresso.util.TreeIterables;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.TimeoutException;

public class TestUtilities {

    // Метод для получения валидной учетной записи
    public AuthCredentials getValidCredentials() {
        return new AuthCredentials("login2", "password2");
    }

    // Метод для получения невалидной учетной записи
    public AuthCredentials getInvalidCredentials() {
        return new AuthCredentials("login", "password");
    }

    // Ожидание появления элемента на экране
    public static ViewAction waitUntilVisible(final Matcher<View> viewMatcher, final long timeoutMillis) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isRoot();
            }

            @Override
            public String getDescription() {
                return "Ожидание появления элемента <" + viewMatcher + "> в течение " + timeoutMillis + " мс.";
            }

            @Override
            public void perform(final UiController controller, final View rootView) {
                controller.loopMainThreadUntilIdle();
                final long startTime = System.currentTimeMillis();
                final long endTime = startTime + timeoutMillis;

                do {
                    for (View child : TreeIterables.breadthFirstViewTraversal(rootView)) {
                        try {
                            if (viewMatcher.matches(child)) {
                                return;
                            }
                        } catch (NoMatchingViewException ignored) {
                        }
                        controller.loopMainThreadForAtLeast(100);
                    }
                } while (System.currentTimeMillis() < endTime);

                throw new PerformException.Builder()
                        .withActionDescription(this.getDescription())
                        .withViewDescription(HumanReadables.describe(rootView))
                        .withCause(new TimeoutException())
                        .build();
            }
        };
    }

    // Получение текущей даты в формате дд.мм.гггг
    public static String fetchCurrentDate() {
        Date today = new Date();
        DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        return formatter.format(today);
    }

    // Получение текущего времени в формате ЧЧ:ММ
    public static String fetchCurrentTime() {
        Date now = new Date();
        DateFormat formatter = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return formatter.format(now);
    }

    // Поиск элемента по индексу
    public static Matcher<View> findByIndex(final Matcher<View> targetMatcher, final int targetIndex) {
        return new TypeSafeMatcher<View>() {
            int currentPosition = 0;

            @Override
            public void describeTo(Description description) {
                description.appendText("поиск по индексу: ");
                description.appendValue(targetIndex);
                targetMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                return targetMatcher.matches(view) && currentPosition++ == targetIndex;
            }
        };
    }

    // Поиск дочернего элемента по позиции
    public static Matcher<View> locateChildAtPosition(final Matcher<View> parentSelector, final int childPosition) {
        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Дочерний элемент на позиции " + childPosition + " в родителе ");
                parentSelector.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View targetView) {
                ViewParent parent = targetView.getParent();
                return parent instanceof ViewGroup && parentSelector.matches(parent)
                        && targetView.equals(((ViewGroup) parent).getChildAt(childPosition));
            }
        };
    }

    // Перегруженный метод поиска дочернего элемента
    public static Matcher<View> locateChildAtPosition(Matcher<View> matcher, final Matcher<View> parentSelector, final int childPosition) {
        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Дочерний элемент на позиции " + childPosition + " в родителе ");
                parentSelector.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View targetView) {
                ViewParent parent = targetView.getParent();
                return parent instanceof ViewGroup && parentSelector.matches(parent)
                        && targetView.equals(((ViewGroup) parent).getChildAt(childPosition));
            }
        };
    }

    // Генератор случайных данных
    public static class RandomDataGenerator {
        static final Random randomizer = new Random();

        public static String generateRandomEventType() {
            String[] eventTypes = {
                    "Объявление",
                    "День рождения",
                    "Зарплата",
                    "Профсоюз",
                    "Праздник",
                    "Массаж",
                    "Благодарность",
                    "Нужна помощь"
            };
            return eventTypes[randomizer.nextInt(eventTypes.length)];
        }
    }

    // Утилиты для работы с текстом
    public static class TextViewExtractor {
        public static String extractText(ViewInteraction targetView) {
            final String[] extractedValue = new String[1];
            ViewAction fetchTextAction = new ViewAction() {

                @Override
                public Matcher<View> getConstraints() {
                    return isAssignableFrom(TextView.class);
                }

                @Override
                public String getDescription() {
                    return "Извлечение текстового содержимого";
                }

                @Override
                public void perform(UiController controller, View view) {
                    TextView textWidget = (TextView) view;
                    extractedValue[0] = textWidget.getText().toString();
                }
            };

            targetView.perform(fetchTextAction);
            return extractedValue[0];
        }
    }

    // Ожидание элемента с заданной задержкой
    public static void waitForViewPresence(Matcher<View> elementMatcher, int delayMillis) {
        onView(isRoot()).perform(waitUntilVisible(elementMatcher, delayMillis));
    }

    // Класс для хранения учетных данных
    public static class AuthCredentials {
        private final String username;
        private final String secretKey;

        public AuthCredentials(String username, String secretKey) {
            this.username = username;
            this.secretKey = secretKey;
        }

        public String getUsername() {
            return username;
        }

        public String getSecretKey() {
            return secretKey;
        }
    }
}