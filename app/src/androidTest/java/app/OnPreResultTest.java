package app;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;
import io.victoralbertos.app.R;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class OnPreResultTest {
    @Rule public ActivityTestRule<OnPreResultActivity> activityRule = new ActivityTestRule<>(OnPreResultActivity.class);

    @Test public void CheckHasBothResults() {
        onView(withId(R.id.start_pre_for_result)).perform(click());
        onView(withId(R.id.pre_result)).check(matches(withText("Do whatever you want with the data, but not with the UI")));
        onView(withId(R.id.result)).check(matches(withText("Well done first")));
    }

}