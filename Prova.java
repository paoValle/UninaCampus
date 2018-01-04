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

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isChecked;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNot.not;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class Prova {

    @Rule
    public ActivityTestRule<MunchLifeActivity> mActivityTestRule = new ActivityTestRule<>(MunchLifeActivity.class);

    @Test
    public void prova() {

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


        try{
            Thread.sleep(10000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }


        ViewInteraction textView = onView(
                allOf(withId(android.R.id.message), withText("You have won!"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.ScrollView.class),
                                        0),
                                0),
                        isDisplayed()));
        textView.check(matches(withText("You have won!")));



        ViewInteraction button10 = onView(
                allOf(withId(android.R.id.button3), withText("Okay"),
                        childAtPosition(
                                allOf(withClassName(is("android.widget.LinearLayout")),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                3)),
                                0),
                        isDisplayed()));
        button10.perform(click());

        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());

        ViewInteraction textView2 = onView(
                allOf(withId(android.R.id.title), withText("Change Settings"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("com.android.internal.view.menu.ListMenuItemView")),
                                        0),
                                0),
                        isDisplayed()));
        textView2.perform(click());

        DataInteraction linearLayout = onData(anything())
                .inAdapterView(allOf(withId(android.R.id.list),
                        childAtPosition(
                                withClassName(is("android.widget.LinearLayout")),
                                0)))
                .atPosition(2);
        linearLayout.perform(click());


        DataInteraction linearLayout1 = onData(anything())
                .inAdapterView(allOf(withId(android.R.id.list),
                        childAtPosition(
                                withClassName(is("android.widget.LinearLayout")),
                                0)))
                .atPosition(3);
        linearLayout1.perform(click());

        ViewInteraction editText = onView(
                allOf(withId(android.R.id.edit), withText("10"),
                        childAtPosition(
                                allOf(withClassName(is("android.widget.LinearLayout")),
                                        childAtPosition(
                                                withClassName(is("android.widget.ScrollView")),
                                                0)),
                                1)));
        editText.perform(scrollTo(), click());

        ViewInteraction editText2 = onView(
                allOf(withId(android.R.id.edit), withText("10"),
                        childAtPosition(
                                allOf(withClassName(is("android.widget.LinearLayout")),
                                        childAtPosition(
                                                withClassName(is("android.widget.ScrollView")),
                                                0)),
                                1)));
        editText2.perform(scrollTo(), replaceText("1"));

        ViewInteraction editText3 = onView(
                allOf(withId(android.R.id.edit), withText("1"),
                        childAtPosition(
                                allOf(withClassName(is("android.widget.LinearLayout")),
                                        childAtPosition(
                                                withClassName(is("android.widget.ScrollView")),
                                                0)),
                                1),
                        isDisplayed()));
        editText3.perform(closeSoftKeyboard());

        ViewInteraction editText4 = onView(
                allOf(withId(android.R.id.edit), withText("1"),
                        childAtPosition(
                                allOf(withClassName(is("android.widget.LinearLayout")),
                                        childAtPosition(
                                                withClassName(is("android.widget.ScrollView")),
                                                0)),
                                1)));
        editText4.perform(scrollTo(), click());

        ViewInteraction editText5 = onView(
                allOf(withId(android.R.id.edit), withText("1"),
                        childAtPosition(
                                allOf(withClassName(is("android.widget.LinearLayout")),
                                        childAtPosition(
                                                withClassName(is("android.widget.ScrollView")),
                                                0)),
                                1)));
        editText5.perform(scrollTo(), replaceText("5"));

        ViewInteraction editText6 = onView(
                allOf(withId(android.R.id.edit), withText("5"),
                        childAtPosition(
                                allOf(withClassName(is("android.widget.LinearLayout")),
                                        childAtPosition(
                                                withClassName(is("android.widget.ScrollView")),
                                                0)),
                                1),
                        isDisplayed()));
        editText6.perform(closeSoftKeyboard());

        ViewInteraction button15 = onView(
                allOf(withId(android.R.id.button1), withText("OK"),
                        childAtPosition(
                                allOf(withClassName(is("android.widget.LinearLayout")),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                3)),
                                3),
                        isDisplayed()));
        button15.perform(click());



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
