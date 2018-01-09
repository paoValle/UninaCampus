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
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.longClick;
import static android.support.test.espresso.action.ViewActions.replaceText;
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
public class ProvaTest2 {

    @Rule
    public ActivityTestRule<Prova> mActivityTestRule = new ActivityTestRule<>(Prova.class);

    @Test
    public void provaTest2() {

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.idtextView), withText("Esame"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.drawer_layout),
                                        0),
                                2),
                        isDisplayed()));
        textView2.check(matches(withText("Esame")));

        ViewInteraction button = onView(
                allOf(withId(R.id.dialogoButton),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.drawer_layout),
                                        0),
                                4),
                        isDisplayed()));
        button.check(matches(isDisplayed()));

        ViewInteraction appCompatCheckBox = onView(
                allOf(withId(R.id.idcheckBox), withText("Prova Check"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.drawer_layout),
                                        0),
                                0),
                        isDisplayed()));
        appCompatCheckBox.perform(click());

        DataInteraction appCompatTextView = onData(anything())
                .inAdapterView(allOf(withId(R.id.idLista),
                        childAtPosition(
                                withClassName(is("android.widget.LinearLayout")),
                                6)))
                .atPosition(2);
        appCompatTextView.perform(click());

        ViewInteraction appCompatButton0 = onView(
                allOf(withId(R.id.dialogoButton), withText("Apri Dialog"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.drawer_layout),
                                        0),
                                4),
                        isDisplayed()));
        appCompatButton0.perform(longClick());

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.dialogoButton), withText("Apri Dialog"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.drawer_layout),
                                        0),
                                4),
                        isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction textInputEditText = onView(
                allOf(withId(R.id.idinputText),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.support.design.widget.TextInputLayout")),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText.perform(click());

        ViewInteraction textInputEditText2 = onView(
                allOf(withId(R.id.idinputText),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.support.design.widget.TextInputLayout")),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText2.perform(replaceText("prova"), closeSoftKeyboard());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.idBottoneIndietro), withText("Ciao"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.support.constraint.ConstraintLayout")),
                                        0),
                                1),
                        isDisplayed()));
        appCompatButton2.perform(click());

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
