package info.bpace.munchlife;

import android.widget.CheckBox;
import android.widget.EditText;
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

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static org.robolectric.Shadows.shadowOf;

/**
 * Created by antonio on 02/01/2018.
 */

@RunWith(RobolectricTestRunner.class)
@Config(manifest=Config.NONE)
public class ProvaRobolectric {
    @Before
    public void setup(){

    }

    @Test
    public void testProva(){
        MunchLifeActivity activity;
        assertNotNull(shadowOf(RuntimeEnvironment.application));
        assertTrue(Robolectric.setupActivity(MunchLifeActivity.class) != null);
        activity = Robolectric.setupActivity(MunchLifeActivity.class);

        // Clicco 10 volte per vincere
        ( activity.findViewById(R.id.up_button)).performClick();
        ( activity.findViewById(R.id.up_button)).performClick();
        ( activity.findViewById(R.id.up_button)).performClick();
        ( activity.findViewById(R.id.up_button)).performClick();
        ( activity.findViewById(R.id.up_button)).performClick();
        ( activity.findViewById(R.id.up_button)).performClick();
        ( activity.findViewById(R.id.up_button)).performClick();
        ( activity.findViewById(R.id.up_button)).performClick();
        ( activity.findViewById(R.id.up_button)).performClick();

        //Verifico se si è aperto il dialog della vittoria

        assertTrue(ShadowAlertDialog.getLatestAlertDialog().isShowing());

        assertEquals("You have won!",((TextView)ShadowAlertDialog.getLatestAlertDialog().findViewById(android.R.id.message)).getText());

        assertNotNull(ShadowAlertDialog.getLatestAlertDialog().findViewById(android.R.id.button3));

        shadowOf(ShadowAlertDialog.getLatestAlertDialog()).clickOn(android.R.id.button3);

        assertNotNull(activity.getActionBar());

        shadowOf(activity).clickMenuItem(R.id.settings);

        SettingsActivity activity2;
        assertNotNull(shadowOf(RuntimeEnvironment.application));
        assertTrue(Robolectric.setupActivity(SettingsActivity.class) != null);
        activity2 = Robolectric.setupActivity(SettingsActivity.class);

        shadowOf((ListView) activity2.findViewById(android.R.id.list)).performItemClick(2);


        shadowOf((ListView) activity2.findViewById(android.R.id.list)).performItemClick(3);
        ((EditText)ShadowAlertDialog.getLatestAlertDialog().findViewById(android.R.id.edit)).setText("1");
        ((EditText)ShadowAlertDialog.getLatestAlertDialog().findViewById(android.R.id.edit)).setText("5");

        shadowOf(ShadowAlertDialog.getLatestAlertDialog()).clickOn(android.R.id.button1);

    }
}
