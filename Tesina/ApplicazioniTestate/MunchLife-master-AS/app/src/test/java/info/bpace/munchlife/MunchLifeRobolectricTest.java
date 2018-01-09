package info.bpace.munchlife;

import android.app.AlertDialog;
import android.content.Intent;
import android.preference.Preference;
import android.preference.PreferenceCategory;
import android.preference.PreferenceScreen;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowAlertDialog;
import org.robolectric.shadows.ShadowApplication;


import static junit.framework.Assert.assertNull;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.robolectric.Shadows.shadowOf;


@RunWith(RobolectricTestRunner.class)
@Config(manifest=Config.NONE)
public class MunchLifeRobolectricTest {



    @Before
    public void setup(){

    }

    @Test
    public void testMunchLifeActivity() throws Exception{
        MunchLifeActivity activity;
        assertNotNull(shadowOf(RuntimeEnvironment.application));
        assertTrue(Robolectric.setupActivity(MunchLifeActivity.class) != null);
        activity = Robolectric.setupActivity(MunchLifeActivity.class);

        // Click on Down a Level
        ( activity.findViewById(R.id.down_button)).performClick();
        ( activity.findViewById(R.id.down_gear_button)).performClick();
        ( activity.findViewById(R.id.up_button)).performClick();
        ( activity.findViewById(R.id.up_button)).performClick();
        ( activity.findViewById(R.id.up_button)).performClick();
        ( activity.findViewById(R.id.up_button)).performClick();
        ( activity.findViewById(R.id.up_button)).performClick();
        ( activity.findViewById(R.id.up_button)).performClick();
        ( activity.findViewById(R.id.up_button)).performClick();
        ( activity.findViewById(R.id.up_button)).performClick();
        ( activity.findViewById(R.id.up_button)).performClick();
        shadowOf(ShadowAlertDialog.getLatestAlertDialog()).clickOn(android.R.id.button3);

        ( activity.findViewById(R.id.down_button)).performClick();
        ( activity.findViewById(R.id.up_gear_button)).performClick();
        ( activity.findViewById(R.id.up_gear_button)).performClick();
        ( activity.findViewById(R.id.up_gear_button)).performClick();
        ( activity.findViewById(R.id.up_gear_button)).performClick();
        ( activity.findViewById(R.id.down_button)).performClick();
        ( activity.findViewById(R.id.down_button)).performClick();
        ( activity.findViewById(R.id.up_button)).performClick();
        ( activity.findViewById(R.id.up_button)).performClick();
        ( activity.findViewById(R.id.up_button)).performClick();
        shadowOf(ShadowAlertDialog.getLatestAlertDialog()).clickOn(android.R.id.button3);
        ( activity.findViewById(R.id.up_gear_button)).performClick();
        ( activity.findViewById(R.id.up_gear_button)).performClick();
        ( activity.findViewById(R.id.up_gear_button)).performClick();
        ( activity.findViewById(R.id.up_gear_button)).performClick();
        ( activity.findViewById(R.id.up_gear_button)).performClick();
        ( activity.findViewById(R.id.up_gear_button)).performClick();
        ( activity.findViewById(R.id.gender)).performClick();
        ( activity.findViewById(R.id.gender)).performClick();

        // Assert that: '20' is shown
        assertEquals("20",((TextView)activity.findViewById(R.id.total_level)).getText());
        shadowOf(activity).clickMenuItem(R.id.reset);
        shadowOf(activity).clickMenuItem(R.id.diceroller);
        shadowOf(ShadowAlertDialog.getLatestAlertDialog()).clickOn(android.R.id.button3);
        shadowOf(activity).clickMenuItem(R.id.diceroller);
        shadowOf(ShadowAlertDialog.getLatestAlertDialog()).clickOn(android.R.id.button3);
        shadowOf(activity).clickMenuItem(R.id.diceroller);
        shadowOf(ShadowAlertDialog.getLatestAlertDialog()).clickOn(android.R.id.button3);
        shadowOf(activity).clickMenuItem(R.id.settings);

        assertEquals((new Intent(activity, SettingsActivity.class)).getComponent(),
                ShadowApplication.getInstance().getNextStartedActivity().getComponent());



    }



    @Test
    public void testSettingsActivity() throws Exception {

        //ApplicationPreferencesActivity activity2 = new ApplicationPreferencesActivity();
        //ShadowPreferenceActivity shadowActivity = Robolectric.shadowOf(activity2);
        // So far, the activity itself doesn't load a preference resource
        // itself, but only a header.
        //assertThat(shadowActivity.getPreferencesResId(), is(-1));

        SettingsActivity activity2;
        assertNotNull(shadowOf(RuntimeEnvironment.application));
        assertTrue(Robolectric.setupActivity(SettingsActivity.class) != null);
        activity2 = Robolectric.setupActivity(SettingsActivity.class);

        assertEquals(((ListView) activity2.findViewById(android.R.id.list)).getCount(),4);
        shadowOf((ListView) activity2.findViewById(android.R.id.list)).performItemClick(2);
        assertTrue(((CheckBox) activity2.findViewById(android.R.id.checkbox)).isChecked());


        ((CheckBox) activity2.findViewById(android.R.id.checkbox)).performClick();
        assertTrue(!((CheckBox) activity2.findViewById(android.R.id.checkbox)).isChecked());
        ((CheckBox) activity2.findViewById(android.R.id.checkbox)).performClick();
        assertTrue(((CheckBox) activity2.findViewById(android.R.id.checkbox)).isChecked());

        shadowOf((ListView) activity2.findViewById(android.R.id.list)).performItemClick(3);
        shadowOf((ListView) activity2.findViewById(android.R.id.list)).performItemClick(4);

//    assertNotNull((EditText) activity2.findViewById(android.R.id.edit));
//        ((EditText) activity2.findViewById(android.R.id.edit)).clearComposingText();
//        ((EditText) activity2.findViewById(android.R.id.edit)).setText("100000");
//        activity2.findViewById(android.R.id.button2).performClick();

/*
        // Click on Cancel
        // Click on LinearLayout Max Level The level at which you win. LinearLayout
        solo.clickInList(4, 0);
        // Enter the text: '10'
        solo.clearEditText((android.widget.EditText) solo.getView(android.R.id.edit));
        solo.enterText((android.widget.EditText) solo.getView(android.R.id.edit), "10");
        // Wait for dialog
        solo.waitForDialogToOpen(5000);
        // Sleep for 7167 milliseconds
        solo.sleep(2000);//7167);
        // Click on 10
        solo.clickOnView(solo.getView(android.R.id.edit));
        // Sleep for 4829 milliseconds
        solo.sleep(2000);//4829);
        // Enter the text: '5'
        solo.clearEditText((android.widget.EditText) solo.getView(android.R.id.edit));
        solo.enterText((android.widget.EditText) solo.getView(android.R.id.edit), "5");
        // Sleep for 1930 milliseconds
        solo.sleep(2000);//1930);
        // Click on OK
        solo.clickOnView(solo.getView(android.R.id.button1));
        // Sleep for 4300 milliseconds
        solo.sleep(2000);//4300);
        // Press menu back key
        solo.goBack();
*/
    }

}
