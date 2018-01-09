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
public class ProvaTest {

    @Rule
    public ActivityTestRule<Prova> mActivityTestRule = new ActivityTestRule<>(Prova.class);

    @Test
    public void provaTest() {
        try{
            Thread.sleep(5000);
        }
        catch (Exception e ){
            e.printStackTrace();
        }
        ViewInteraction appCompatCheckBox = onView(
                allOf(withId(R.id.idcheckBox), withText("Prova Check"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.drawer_layout),
                                        0),
                                0),
                        isDisplayed()));
        appCompatCheckBox.perform(click());

        try{
            Thread.sleep(2000);
        }
        catch (Exception e ){
            e.printStackTrace();
        }

        ViewInteraction appCompatCheckBox2 = onView(
                allOf(withId(R.id.idcheckBox), withText("Prova Check"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.drawer_layout),
                                        0),
                                0),
                        isDisplayed()));
        appCompatCheckBox2.perform(click());

        try{
            Thread.sleep(2000);
        }
        catch (Exception e ){
            e.printStackTrace();
        }

        ViewInteraction textInputEditText = onView(
                allOf(withId(R.id.testoInput),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.support.design.widget.TextInputLayout")),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText.perform(click());
        try{
            Thread.sleep(2000);
        }
        catch (Exception e ){
            e.printStackTrace();
        }

        ViewInteraction textInputEditText2 = onView(
                allOf(withId(R.id.testoInput),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.support.design.widget.TextInputLayout")),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText2.perform(replaceText("ciao"), closeSoftKeyboard());
        try{
            Thread.sleep(2000);
        }
        catch (Exception e ){
            e.printStackTrace();
        }

        DataInteraction appCompatTextView = onData(anything())
                .inAdapterView(allOf(withId(R.id.idLista),
                        childAtPosition(
                                withClassName(is("android.widget.LinearLayout")),
                                6)))
                .atPosition(0);
        appCompatTextView.perform(click());
        try{
            Thread.sleep(2000);
        }
        catch (Exception e ){
            e.printStackTrace();
        }

        DataInteraction appCompatTextView2 = onData(anything())
                .inAdapterView(allOf(withId(R.id.idLista),
                        childAtPosition(
                                withClassName(is("android.widget.LinearLayout")),
                                6)))
                .atPosition(3);
        appCompatTextView2.perform(longClick());
        try{
            Thread.sleep(2000);
        }
        catch (Exception e ){
            e.printStackTrace();
        }

        ViewInteraction appCompatSpinner = onView(
                allOf(withId(R.id.idspinner),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.drawer_layout),
                                        0),
                                1),
                        isDisplayed()));
        appCompatSpinner.perform(click());
        try{
            Thread.sleep(2000);
        }
        catch (Exception e ){
            e.printStackTrace();
        }

        DataInteraction appCompatTextView3 = onData(anything())
                .inAdapterView(withClassName(is("android.support.v7.widget.DropDownListView")))
                .atPosition(1);
        appCompatTextView3.perform(click());
        try{
            Thread.sleep(2000);
        }
        catch (Exception e ){
            e.printStackTrace();
        }

        ViewInteraction textView = onView(
                allOf(withText("Ingegneria"),
                        childAtPosition(
                                allOf(withId(R.id.idspinner),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                                1)),
                                0),
                        isDisplayed()));
        textView.check(matches(withText("Ingegneria")));
        try{
            Thread.sleep(2000);
        }
        catch (Exception e ){
            e.printStackTrace();
        }

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.idtextView), withText("Ingegneria"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.drawer_layout),
                                        0),
                                2),
                        isDisplayed()));
        textView2.check(matches(withText("Ingegneria")));
        try{
            Thread.sleep(2000);
        }
        catch (Exception e ){
            e.printStackTrace();
        }

        ViewInteraction appCompatSpinner2 = onView(
                allOf(withId(R.id.idspinner),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.drawer_layout),
                                        0),
                                1),
                        isDisplayed()));
        appCompatSpinner2.perform(click());
        try{
            Thread.sleep(2000);
        }
        catch (Exception e ){
            e.printStackTrace();
        }

        DataInteraction appCompatTextView4 = onData(anything())
                .inAdapterView(withClassName(is("android.support.v7.widget.DropDownListView")))
                .atPosition(4);
        appCompatTextView4.perform(click());
        try{
            Thread.sleep(2000);
        }
        catch (Exception e ){
            e.printStackTrace();
        }

        ViewInteraction textView3 = onView(
                allOf(withText("Test"),
                        childAtPosition(
                                allOf(withId(R.id.idspinner),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                                1)),
                                0),
                        isDisplayed()));
        textView3.check(matches(withText("Test")));
        try{
            Thread.sleep(2000);
        }
        catch (Exception e ){
            e.printStackTrace();
        }

        ViewInteraction textView4 = onView(
                allOf(withId(R.id.idtextView), withText("Test"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.drawer_layout),
                                        0),
                                2),
                        isDisplayed()));
        textView4.check(matches(withText("Test")));
        try{
            Thread.sleep(2000);
        }
        catch (Exception e ){
            e.printStackTrace();
        }

        ViewInteraction appCompatButton0 = onView(
                allOf(withId(R.id.dialogoButton), withText("Apri Dialog"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.drawer_layout),
                                        0),
                                4),
                        isDisplayed()));
        appCompatButton0.perform(longClick());

        try{
            Thread.sleep(2000);
        }
        catch (Exception e ){
            e.printStackTrace();
        }

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.dialogoButton), withText("Apri Dialog"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.drawer_layout),
                                        0),
                                4),
                        isDisplayed()));
        appCompatButton.perform(click());
        try{
            Thread.sleep(2000);
        }
        catch (Exception e ){
            e.printStackTrace();
        }


        ViewInteraction textInputEditText3 = onView(
                allOf(withId(R.id.idinputText),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.support.design.widget.TextInputLayout")),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText3.perform(click());
        try{
            Thread.sleep(2000);
        }
        catch (Exception e ){
            e.printStackTrace();
        }


        ViewInteraction textInputEditText4 = onView(
                allOf(withId(R.id.idinputText),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.support.design.widget.TextInputLayout")),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText4.perform(replaceText("michela"), closeSoftKeyboard());
        try{
            Thread.sleep(2000);
        }
        catch (Exception e ){
            e.printStackTrace();
        }


        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.idBottoneIndietro), withText("Ciao"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.support.constraint.ConstraintLayout")),
                                        0),
                                1),
                        isDisplayed()));
        appCompatButton2.perform(click());
        try{
            Thread.sleep(2000);
        }
        catch (Exception e ){
            e.printStackTrace();
        }


        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.changeActivity), withText("Cambia Activity"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.drawer_layout),
                                        0),
                                5),
                        isDisplayed()));
        appCompatButton3.perform(click());
        try{
            Thread.sleep(2000);
        }
        catch (Exception e ){
            e.printStackTrace();
        }


        ViewInteraction textView5 = onView(
                allOf(withId(R.id.textView), withText("ChangeActivityRobolectric"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.view.View.class),
                                        0),
                                0),
                        isDisplayed()));
        textView5.check(matches(withText("ChangeActivityRobolectric")));

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
