package app;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;
import app.multi_start.MultiStartActivity;
import io.victoralbertos.app.R;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class MultiStartTest {
    @Rule public ActivityTestRule<MultiStartActivity> activityRule = new ActivityTestRule<>(MultiStartActivity.class);

    @Test public void CheckHasBothResults() {
        onView(withId(R.id.start_two_for_result)).perform(click());
        onView(withId(R.id.first_result)).check(matches(withText("Well done first")));
        onView(withId(R.id.second_result)).check(matches(withText("Well done second")));
    }

}