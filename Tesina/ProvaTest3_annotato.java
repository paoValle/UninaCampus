package com.example.mikel_000.testapplicazione;


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
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.longClick;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class ProvaTest3 {

    @Rule
    public ActivityTestRule<Prova> mActivityTestRule = new ActivityTestRule<>(Prova.class);

    @Test
    public void provaTest3() {
//Activity
        ViewInteraction button = onView(
                allOf(withId(R.id.dialogoButton),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.drawer_layout),
                                        0),
                                4),
                        isDisplayed()));
        button.check(matches(isDisplayed()));

//Activity
        ViewInteraction textView = onView(
                allOf(withId(R.id.idtextView), withText("Esame"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.drawer_layout),
                                        0),
                                2),
                        isDisplayed()));
        textView.check(matches(withText("Esame")));

//Activity
        ViewInteraction appCompatCheckBox = onView(
                allOf(withId(R.id.idcheckBox), withText("Prova Check"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.drawer_layout),
                                        0),
                                0),
                        isDisplayed()));
        appCompatCheckBox.perform(click());

//Activity
        ViewInteraction checkBox = onView(
                allOf(withId(R.id.idcheckBox),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.drawer_layout),
                                        0),
                                0),
                        isDisplayed()));
        checkBox.check(matches(isDisplayed()));

//Activity
        ViewInteraction appCompatSpinner = onView(
                allOf(withId(R.id.idspinner),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.drawer_layout),
                                        0),
                                1),
                        isDisplayed()));
        appCompatSpinner.perform(click());

        DataInteraction appCompatTextView = onData(anything())
                .inAdapterView(withClassName(is("android.support.v7.widget.DropDownListView")))
                .atPosition(1);
        appCompatTextView.perform(click());

//Activity
        ViewInteraction appCompatButton0 = onView(
                allOf(withId(R.id.dialogoButton), withText("Apri Dialog"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.drawer_layout),
                                        0),
                                4),
                        isDisplayed()));
        appCompatButton0.perform(longClick());

//Activity
        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.dialogoButton), withText("Apri Dialog"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.drawer_layout),
                                        0),
                                4),
                        isDisplayed()));
        appCompatButton.perform(click());


//Dialog
        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.idBottoneIndietro), withText("Ciao"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.support.constraint.ConstraintLayout")),
                                        0),
                                1),
                        isDisplayed()));
        appCompatButton2.perform(click());

//Activity
        DataInteraction appCompatTextView2 = onData(anything())
                .inAdapterView(allOf(withId(R.id.idLista),
                        childAtPosition(
                                withClassName(is("android.widget.LinearLayout")),
                                6)))
                .atPosition(3);
        appCompatTextView2.perform(click());

//Activity
        DataInteraction appCompatTextView3 = onData(anything())
                .inAdapterView(allOf(withId(R.id.idLista),
                        childAtPosition(
                                withClassName(is("android.widget.LinearLayout")),
                                6)))
                .atPosition(3);
        appCompatTextView3.perform(longClick());


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
