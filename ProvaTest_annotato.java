package info.bpace.munchlife;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class ProvaTest {

    @Rule
    public ActivityTestRule<MunchLifeActivity> mActivityTestRule = new ActivityTestRule<>(MunchLifeActivity.class);

    @Test
    public void provaTest() {
//Dialog
        ViewInteraction button = onView(
                allOf(withId(R.id.up_button), withText("Up a Level"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.level_table),
                                        0),
                                0),
        button.perform(click());

//Activity
        ViewInteraction button2 = onView(
                allOf(withId(R.id.up_button), withText("Up a Level"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.level_table),
                                        0),
                                0),
        button2.perform(click());

//AlertDialog
        ViewInteraction button3 = onView(
                allOf(withId(R.id.up_button), withText("Up a Level"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.level_table),
                                        0),
                                0),
        button3.perform(click());

//Dialog
        ViewInteraction button4 = onView(
                allOf(withId(R.id.up_gear_button), withText("Add Gear"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.level_table),
                                        0),
                                1),
        button4.perform(click());

//Activity
        ViewInteraction button5 = onView(
                allOf(withId(R.id.up_gear_button), withText("Add Gear"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.level_table),
                                        0),
                                1),
        button5.perform(click());

//Dialog
        ViewInteraction button6 = onView(
                allOf(withId(R.id.up_gear_button), withText("Add Gear"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.level_table),
                                        0),
                                1),
        button6.perform(click());

//AlertDialog
        ViewInteraction button7 = onView(
                allOf(withId(R.id.down_gear_button), withText("Remove Gear"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.level_table),
                                        2),
                                1),
        button7.perform(click());

//Activity
        ViewInteraction textView = onView(
                allOf(withId(R.id.total_level), withText("6"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
        textView.check(matches(withText("6")));

//AlertDialog
        ViewInteraction textView2 = onView(
                allOf(withId(R.id.current_level), withText("4"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.level_table),
                                        1),
                                0),
        textView2.check(matches(withText("4")));

//Dialog
        ViewInteraction imageView = onView(
                allOf(withId(R.id.gender),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
        imageView.perform(click());

    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
