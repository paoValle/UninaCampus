package info.bpace.munchlife;

import org.hamcrest.core.IsInstanceOf;
import org.junit.Test;
import android.app.AlertDialog;
import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.CheckBox;

import static android.view.HapticFeedbackConstants.LONG_PRESS;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.robolectric.Shadows.shadowOf;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.fakes.RoboMenuItem;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowAlertDialog;
import org.robolectric.shadows.ShadowApplication;
import org.robolectric.shadows.ShadowDialog;
import org.robolectric.shadows.ShadowToast;

import info.bpace.munchlife.BuildConfig;
import info.bpace.munchlife.MunchLifeActivity;
import info.bpace.munchlife.R;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */


@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class ProvaTestTradotto {
	    @Test
    public void addition_isCorrect() throws Exception {
            MunchLifeActivity activity;
            assertNotNull(shadowOf(RuntimeEnvironment.application));
            assertTrue(Robolectric.setupActivity(MunchLifeActivity.class) != null);
            activity = Robolectric.setupActivity(MunchLifeActivity.class);
            (activity.findViewById(R.id.up_button)).performClick();
            assertNotNull(activity.findViewById(R.id.up_button));
            assertEquals("2",((TextView)activity.findViewById(R.id.current_level)).getText().toString());
            assertNotNull(activity.findViewById(R.id.down_button));
            (activity.findViewById(R.id.up_gear_button)).performClick();
            (activity.findViewById(R.id.down_gear_button)).performClick();
            (activity.findViewById(R.id.down_button)).performClick();
            (activity.findViewById(R.id.up_button)).performClick();
            (activity.findViewById(R.id.up_button)).performClick();
            (activity.findViewById(R.id.up_button)).performClick();
            (activity.findViewById(R.id.up_button)).performClick();
            (activity.findViewById(R.id.up_button)).performClick();
            (activity.findViewById(R.id.up_button)).performClick();
            (activity.findViewById(R.id.up_button)).performClick();
            (activity.findViewById(R.id.up_button)).performClick();
            (activity.findViewById(R.id.up_button)).performClick();
            assertNotNull(ShadowAlertDialog.getLatestAlertDialog().findViewById(android.R.id.button3));
            assertEquals("You have won!",((TextView)ShadowAlertDialog.getLatestAlertDialog().findViewById(android.R.id.message)).getText());
            (ShadowAlertDialog.getLatestAlertDialog().findViewById(android.R.id.button3)).performClick();
		
		    }
}
