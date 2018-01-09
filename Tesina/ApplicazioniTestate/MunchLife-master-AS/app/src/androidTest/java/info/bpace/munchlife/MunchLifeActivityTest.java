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
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MunchLifeActivityTest {

    @Rule
    public ActivityTestRule<MunchLifeActivity> mActivityTestRule = new ActivityTestRule<>(MunchLifeActivity.class);

    @Test
    public void munchLifeActivityTest() {
        ViewInteraction button = onView(
                allOf(withId(R.id.up_button), withText("Up a Level"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.level_table),
                                        0),
                                0),
                        isDisplayed()));
        button.perform(click());

        ViewInteraction button2 = onView(
                allOf(withId(R.id.down_button), withText("Down a Level"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.level_table),
                                        2),
                                0),
                        isDisplayed()));
        button2.perform(click());

        ViewInteraction button3 = onView(
                allOf(withId(R.id.down_button), withText("Down a Level"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.level_table),
                                        2),
                                0),
                        isDisplayed()));
        button3.perform(click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.current_level), withText("1"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.level_table),
                                        1),
                                0),
                        isDisplayed()));
        textView.check(matches(withText("1")));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.total_level), withText("1"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        textView2.check(matches(withText("1")));

        ViewInteraction button4 = onView(
                allOf(withId(R.id.up_gear_button), withText("Add Gear"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.level_table),
                                        0),
                                1),
                        isDisplayed()));
        button4.perform(click());

        ViewInteraction overflowMenuButton = onView(
                allOf(withContentDescription("More options"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("com.android.internal.widget.ActionBarView")),
                                        1),
                                0),
                        isDisplayed()));
        overflowMenuButton.perform(click());

        ViewInteraction textView3 = onView(
                allOf(withId(android.R.id.title), withText("Reset"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("com.android.internal.view.menu.ListMenuItemView")),
                                        0),
                                0),
                        isDisplayed()));
        textView3.perform(click());

        ViewInteraction textView4 = onView(
                allOf(withId(R.id.current_level), withText("1"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.level_table),
                                        1),
                                0),
                        isDisplayed()));
        textView4.check(matches(withText("1")));

        ViewInteraction textView5 = onView(
                allOf(withId(R.id.current_gear_level), withText("0"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.level_table),
                                        1),
                                1),
                        isDisplayed()));
        textView5.check(matches(withText("0")));

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
