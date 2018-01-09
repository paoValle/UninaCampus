package com.example.mikel_000.testapplicazione;

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

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */


@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class ProvaTest3Tradotto {
	    @Test
    public void addition_isCorrect() throws Exception {
Prova activity;
assertNotNull(shadowOf(RuntimeEnvironment.application));
assertTrue(Robolectric.setupActivity(Prova.class) != null);
activity = Robolectric.setupActivity(Prova.class);
assertNotNull(activity.findViewById(R.id.dialogoButton));
assertEquals("Esame",((TextView)activity.findViewById(R.id.idtextView)).getText().toString());
(activity.findViewById(R.id.idcheckBox)).performClick();
assertNotNull(activity.findViewById(R.id.idcheckBox));
(activity.findViewById(R.id.idspinner)).performClick();
((Spinner) activity.findViewById(R.id.idspinner)).setSelection(1);
(activity.findViewById(R.id.dialogoButton)).performLongClick();
(activity.findViewById(R.id.dialogoButton)).performClick();
(ShadowDialog.getLatestDialog().findViewById(R.id.idBottoneIndietro)).performClick();
shadowOf((ListView) activity.findViewById(R.id.idLista)).performItemClick(3);
(activity.findViewById(R.id.idLista)).performLongClick();
		
		    }
}
