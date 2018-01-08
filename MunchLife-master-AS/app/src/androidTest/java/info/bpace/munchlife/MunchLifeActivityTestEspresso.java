package info.bpace.munchlife;


import android.support.test.espresso.DataInteraction;
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

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MunchLifeActivityTestEspresso {

    @Rule
    public ActivityTestRule<MunchLifeActivity> mActivityTestRule = new ActivityTestRule<>(MunchLifeActivity.class);

    @Test
    public void munchLifeActivityTestEspresso() {
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
                allOf(withId(R.id.up_button), withText("Up a Level"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.level_table),
                                        0),
                                0),
                        isDisplayed()));
        button2.perform(click());

        ViewInteraction button3 = onView(
                allOf(withId(R.id.up_button), withText("Up a Level"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.level_table),
                                        0),
                                0),
                        isDisplayed()));
        button3.perform(click());

        ViewInteraction button4 = onView(
                allOf(withId(R.id.up_button), withText("Up a Level"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.level_table),
                                        0),
                                0),
                        isDisplayed()));
        button4.perform(click());

        ViewInteraction button5 = onView(
                allOf(withId(R.id.up_button), withText("Up a Level"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.level_table),
                                        0),
                                0),
                        isDisplayed()));
        button5.perform(click());

        ViewInteraction button6 = onView(
                allOf(withId(R.id.up_button), withText("Up a Level"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.level_table),
                                        0),
                                0),
                        isDisplayed()));
        button6.perform(click());

        ViewInteraction button7 = onView(
                allOf(withId(R.id.up_button), withText("Up a Level"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.level_table),
                                        0),
                                0),
                        isDisplayed()));
        button7.perform(click());

        ViewInteraction button8 = onView(
                allOf(withId(R.id.up_button), withText("Up a Level"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.level_table),
                                        0),
                                0),
                        isDisplayed()));
        button8.perform(click());

        ViewInteraction button9 = onView(
                allOf(withId(R.id.up_button), withText("Up a Level"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.level_table),
                                        0),
                                0),
                        isDisplayed()));
        button9.perform(click());

        ViewInteraction button10 = onView(
                allOf(withId(android.R.id.button3), withText("Okay"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0),
                                1),
                        isDisplayed()));
        button10.perform(click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.total_level), withText("10"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        textView.check(matches(withText("10")));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.current_level), withText("10"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.level_table),
                                        1),
                                0),
                        isDisplayed()));
        textView2.check(matches(withText("10")));

        ViewInteraction button11 = onView(
                allOf(withId(R.id.down_button), withText("Down a Level"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.level_table),
                                        2),
                                0),
                        isDisplayed()));
        button11.perform(click());

        ViewInteraction button12 = onView(
                allOf(withId(R.id.up_gear_button), withText("Add Gear"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.level_table),
                                        0),
                                1),
                        isDisplayed()));
        button12.perform(click());

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.total_level), withText("10"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        textView3.check(matches(withText("10")));

        ViewInteraction button13 = onView(
                allOf(withId(R.id.up_gear_button), withText("Add Gear"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.level_table),
                                        0),
                                1),
                        isDisplayed()));
        button13.perform(click());

        ViewInteraction button14 = onView(
                allOf(withId(R.id.up_gear_button), withText("Add Gear"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.level_table),
                                        0),
                                1),
                        isDisplayed()));
        button14.perform(click());

        ViewInteraction button15 = onView(
                allOf(withId(R.id.up_gear_button), withText("Add Gear"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.level_table),
                                        0),
                                1),
                        isDisplayed()));
        button15.perform(click());

        ViewInteraction button16 = onView(
                allOf(withId(R.id.down_button), withText("Down a Level"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.level_table),
                                        2),
                                0),
                        isDisplayed()));
        button16.perform(click());

        ViewInteraction button17 = onView(
                allOf(withId(R.id.down_button), withText("Down a Level"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.level_table),
                                        2),
                                0),
                        isDisplayed()));
        button17.perform(click());

        ViewInteraction button18 = onView(
                allOf(withId(R.id.up_button), withText("Up a Level"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.level_table),
                                        0),
                                0),
                        isDisplayed()));
        button18.perform(click());

        ViewInteraction button19 = onView(
                allOf(withId(R.id.up_button), withText("Up a Level"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.level_table),
                                        0),
                                0),
                        isDisplayed()));
        button19.perform(click());

        ViewInteraction textView4 = onView(
                allOf(withId(R.id.total_level), withText("13"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        textView4.check(matches(withText("13")));

        ViewInteraction button20 = onView(
                allOf(withId(R.id.up_button), withText("Up a Level"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.level_table),
                                        0),
                                0),
                        isDisplayed()));
        button20.perform(click());

        ViewInteraction textView5 = onView(
                allOf(withId(android.R.id.message), withText("You have won!"),
                        childAtPosition(
                                allOf(IsInstanceOf.<View>instanceOf(android.widget.ScrollView.class),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                                0)),
                                0),
                        isDisplayed()));
        textView5.check(matches(withText("You have won!")));

        ViewInteraction button21 = onView(
                allOf(withId(android.R.id.button3), withText("Okay"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0),
                                1),
                        isDisplayed()));
        button21.perform(click());

        ViewInteraction button22 = onView(
                allOf(withId(R.id.up_gear_button), withText("Add Gear"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.level_table),
                                        0),
                                1),
                        isDisplayed()));
        button22.perform(click());

        ViewInteraction button23 = onView(
                allOf(withId(R.id.up_gear_button), withText("Add Gear"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.level_table),
                                        0),
                                1),
                        isDisplayed()));
        button23.perform(click());

        ViewInteraction button24 = onView(
                allOf(withId(R.id.up_gear_button), withText("Add Gear"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.level_table),
                                        0),
                                1),
                        isDisplayed()));
        button24.perform(click());

        ViewInteraction button25 = onView(
                allOf(withId(R.id.up_gear_button), withText("Add Gear"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.level_table),
                                        0),
                                1),
                        isDisplayed()));
        button25.perform(click());

        ViewInteraction button26 = onView(
                allOf(withId(R.id.up_gear_button), withText("Add Gear"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.level_table),
                                        0),
                                1),
                        isDisplayed()));
        button26.perform(click());

        ViewInteraction button27 = onView(
                allOf(withId(R.id.up_gear_button), withText("Add Gear"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.level_table),
                                        0),
                                1),
                        isDisplayed()));
        button27.perform(click());

        ViewInteraction textView6 = onView(
                allOf(withId(R.id.total_level), withText("20"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        textView6.check(matches(withText("20")));

        ViewInteraction overflowMenuButton = onView(
                allOf(withContentDescription("More options"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("com.android.internal.widget.ActionBarView")),
                                        1),
                                0),
                        isDisplayed()));
        overflowMenuButton.perform(click());

        ViewInteraction textView7 = onView(
                allOf(withId(android.R.id.title), withText("Reset"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("com.android.internal.view.menu.ListMenuItemView")),
                                        0),
                                0),
                        isDisplayed()));
        textView7.perform(click());

        ViewInteraction textView8 = onView(
                allOf(withId(R.id.total_level), withText("1"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        textView8.check(matches(withText("1")));

        ViewInteraction overflowMenuButton2 = onView(
                allOf(withContentDescription("More options"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("com.android.internal.widget.ActionBarView")),
                                        1),
                                0),
                        isDisplayed()));
        overflowMenuButton2.perform(click());

        ViewInteraction textView9 = onView(
                allOf(withId(android.R.id.title), withText("Roll Dice"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("com.android.internal.view.menu.ListMenuItemView")),
                                        0),
                                0),
                        isDisplayed()));
        textView9.perform(click());

        ViewInteraction button28 = onView(
                allOf(withId(android.R.id.button3), withText("Okay"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0),
                                1),
                        isDisplayed()));
        button28.perform(click());

        ViewInteraction overflowMenuButton3 = onView(
                allOf(withContentDescription("More options"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("com.android.internal.widget.ActionBarView")),
                                        1),
                                0),
                        isDisplayed()));
        overflowMenuButton3.perform(click());

        ViewInteraction textView10 = onView(
                allOf(withId(android.R.id.title), withText("Roll Dice"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("com.android.internal.view.menu.ListMenuItemView")),
                                        0),
                                0),
                        isDisplayed()));
        textView10.perform(click());

        ViewInteraction button29 = onView(
                allOf(withId(android.R.id.button3), withText("Okay"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0),
                                1),
                        isDisplayed()));
        button29.perform(click());

        ViewInteraction overflowMenuButton4 = onView(
                allOf(withContentDescription("More options"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("com.android.internal.widget.ActionBarView")),
                                        1),
                                0),
                        isDisplayed()));
        overflowMenuButton4.perform(click());


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
