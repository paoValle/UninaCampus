package paovalle.TestTradotto;

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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
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

import caldwell.ben.trolly.BuildConfig;
import caldwell.ben.trolly.R;
import caldwell.ben.trolly.Trolly;


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */


@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class TestRobolectric {
	    @Test
    public void addition_isCorrect() throws Exception {

	    	Trolly activity;
			assertNotNull(shadowOf(RuntimeEnvironment.application));
			assertTrue(Robolectric.setupActivity(Trolly.class) != null);
			activity = Robolectric.setupActivity(Trolly.class);
			(activity.findViewById(R.id.textbox)).performClick();
			((TextView) activity.findViewById(R.id.textbox)).setText("Carne");
			(activity.findViewById(R.id.btn_add)).performClick();
			((TextView) activity.findViewById(R.id.textbox)).setText("Pasta");
			(activity.findViewById(R.id.btn_add)).performClick();
			assertNotNull(activity.findViewById(R.id.btn_add));
			shadowOf((ListView) activity.findViewById(android.R.id.list)).performItemClick(0);
			shadowOf((ListView) activity.findViewById(android.R.id.list)).performItemClick(1);
		
		    }
}
