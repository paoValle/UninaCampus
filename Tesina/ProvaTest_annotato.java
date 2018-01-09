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
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class ProvaTest {

    @Rule
    public ActivityTestRule<MunchLifeActivity> mActivityTestRule = new ActivityTestRule<>(MunchLifeActivity.class);

    @Test
    public void provaTest() {
//Activity
        ViewInteraction button = onView(
                allOf(withId(R.id.up_button), withText("Up a Level"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.level_table),
                                        0),
                                0),
                        isDisplayed()));
        button.perform(click());

//Activity
        ViewInteraction button2 = onView(
                allOf(withId(R.id.up_button),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.level_table),
                                        0),
                                0),
                        isDisplayed()));
        button2.check(matches(isDisplayed()));

//Activity
        ViewInteraction textView = onView(
                allOf(withId(R.id.current_level), withText("2"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.level_table),
                                        1),
                                0),
                        isDisplayed()));
        textView.check(matches(withText("2")));

//Activity
        ViewInteraction button3 = onView(
                allOf(withId(R.id.down_button),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.level_table),
                                        2),
                                0),
                        isDisplayed()));
        button3.check(matches(isDisplayed()));

//Activity
        ViewInteraction button4 = onView(
                allOf(withId(R.id.up_gear_button), withText("Add Gear"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.level_table),
                                        0),
                                1),
                        isDisplayed()));
        button4.perform(click());

//Activity
        ViewInteraction button5 = onView(
                allOf(withId(R.id.down_gear_button), withText("Remove Gear"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.level_table),
                                        2),
                                1),
                        isDisplayed()));
        button5.perform(click());

//Activity
        ViewInteraction button6 = onView(
                allOf(withId(R.id.down_button), withText("Down a Level"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.level_table),
                                        2),
                                0),
                        isDisplayed()));
        button6.perform(click());

//Activity
        ViewInteraction button7 = onView(
                allOf(withId(R.id.up_button), withText("Up a Level"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.level_table),
                                        0),
                                0),
                        isDisplayed()));
        button7.perform(click());

//Activity
        ViewInteraction button8 = onView(
                allOf(withId(R.id.up_button), withText("Up a Level"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.level_table),
                                        0),
                                0),
                        isDisplayed()));
        button8.perform(click());

//Activity
        ViewInteraction button9 = onView(
                allOf(withId(R.id.up_button), withText("Up a Level"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.level_table),
                                        0),
                                0),
                        isDisplayed()));
        button9.perform(click());

//Activity
        ViewInteraction button10 = onView(
                allOf(withId(R.id.up_button), withText("Up a Level"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.level_table),
                                        0),
                                0),
                        isDisplayed()));
        button10.perform(click());

//Activity
        ViewInteraction button11 = onView(
                allOf(withId(R.id.up_button), withText("Up a Level"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.level_table),
                                        0),
                                0),
                        isDisplayed()));
        button11.perform(click());

//Activity
        ViewInteraction button12 = onView(
                allOf(withId(R.id.up_button), withText("Up a Level"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.level_table),
                                        0),
                                0),
                        isDisplayed()));
        button12.perform(click());

//Activity
        ViewInteraction button13 = onView(
                allOf(withId(R.id.up_button), withText("Up a Level"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.level_table),
                                        0),
                                0),
                        isDisplayed()));
        button13.perform(click());

//Activity
        ViewInteraction button14 = onView(
                allOf(withId(R.id.up_button), withText("Up a Level"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.level_table),
                                        0),
                                0),
                        isDisplayed()));
        button14.perform(click());

//Activity
        ViewInteraction button15 = onView(
                allOf(withId(R.id.up_button), withText("Up a Level"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.level_table),
                                        0),
                                0),
                        isDisplayed()));
        button15.perform(click());

//AlertDialog
        ViewInteraction button16 = onView(
                allOf(withId(android.R.id.button3),
                        childAtPosition(
                                allOf(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                                1)),
                                0),
                        isDisplayed()));
        button16.check(matches(isDisplayed()));

//AlertDialog
        ViewInteraction textView2 = onView(
                allOf(withId(android.R.id.message), withText("You have won!"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.ScrollView.class),
                                        0),
                                0),
                        isDisplayed()));
        textView2.check(matches(withText("You have won!")));

//AlertDialog
        ViewInteraction button17 = onView(
                allOf(withId(android.R.id.button3), withText("Okay"),
                        childAtPosition(
                                allOf(withClassName(is("android.widget.LinearLayout")),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                3)),
                                0),
                        isDisplayed()));
        button17.perform(click());

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
