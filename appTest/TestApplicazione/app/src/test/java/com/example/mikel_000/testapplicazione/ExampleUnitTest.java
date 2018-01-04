package com.example.mikel_000.testapplicazione;

import org.hamcrest.core.IsInstanceOf;
import org.junit.Test;
import android.app.AlertDialog;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

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
import org.robolectric.annotation.Config;
import org.robolectric.fakes.RoboMenuItem;
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
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        //mi serve l'activity principale

        Prova activity;
        assertNotNull(shadowOf(RuntimeEnvironment.application));
        assertTrue(Robolectric.setupActivity(Prova.class) != null);
        activity = Robolectric.setupActivity(Prova.class);

        MenuItem menuItem = new RoboMenuItem(R.id.nvView);
        activity.onOptionsItemSelected(menuItem);
        activity.findViewById(R.id.nav_first_fragment).performClick();
        System.out.println("text=>"+ShadowToast.getTextOfLatestToast());
        assertEquals("Cliccato su ItemMenu1", ShadowToast.getTextOfLatestToast());


        //checkbox
        activity.findViewById(R.id.idcheckBox).performClick();

        //verifica del toast.
        assertEquals("Box Checked", ShadowToast.getTextOfLatestToast());

       //ripeti il check

        activity.findViewById(R.id.idcheckBox).performClick();

        //Verifica nuovamente il check

        assertEquals("Box not checked", ShadowToast.getTextOfLatestToast());

        //Input text

        ((TextView) activity.findViewById(R.id.testoInput)).setText("ciao");

        //Selezione elemento in una lista
        ((ListView) activity.findViewById(R.id.idLista)).setSelection(0);

        //verifica elemento selezionato
        assertEquals("ciao", ((ListView) activity.findViewById(R.id.idLista)).getSelectedItem().toString());

        //click sulla lista

        shadowOf((ListView) activity.findViewById(R.id.idLista)).performItemClick(3);

        //verifica del toast
        assertEquals("Click su elemento " + 3, ShadowToast.getTextOfLatestToast());

        // seleziona elemento diverso
        ((ListView) activity.findViewById(R.id.idLista)).setSelection(3);

        //assert elemento selezionato
        assertEquals("universita", ((ListView) activity.findViewById(R.id.idLista)).getSelectedItem().toString());

      //long click sulla lista

        ListView lista= (ListView) activity.findViewById(R.id.idLista);
        assertTrue(lista.isLongClickable());
        AdapterView.OnItemLongClickListener iteml=lista.getOnItemLongClickListener();
        assertNotNull(iteml);
        ListAdapter adapter= lista.getAdapter();
        View view= adapter.getView(3,null,lista);
        iteml.onItemLongClick(lista,view,3,adapter.getItemId(3));
        lista.performHapticFeedback(LONG_PRESS);

        //verifica del toast.
        assertEquals("Long Click on List", ShadowToast.getTextOfLatestToast());

        //Long click su bottone

        ((Button)activity.findViewById(R.id.dialogoButton)).performLongClick();

        //Verifica del toast

        assertEquals("Long Click on Button", ShadowToast.getTextOfLatestToast());

        //click su spinner
        ((Spinner) activity.findViewById(R.id.idspinner)).performClick();

        //seleziona l'elemento di indice 1
        ((Spinner) activity.findViewById(R.id.idspinner)).setSelection(1);

        // verifica che il testo è ingegneria
        assertEquals("Ingegneria", ((Spinner) activity.findViewById(R.id.idspinner)).getSelectedItem().toString());

        //verifica che nel testo viene scritto ingegneria
        assertEquals("Ingegneria", ((TextView)activity.findViewById(R.id.idtextView)).getText().toString() );

        //ripeto il click su spinner
        ((Spinner) activity.findViewById(R.id.idspinner)).performClick();

        //seleziona testo di elemento 4
        ((Spinner) activity.findViewById(R.id.idspinner)).setSelection(4);

        //verifica che ho selezionato test
        assertEquals("Test", ((Spinner) activity.findViewById(R.id.idspinner)).getSelectedItem().toString());

        //verifica che il testo è test
        assertEquals("Test", ((TextView)activity.findViewById(R.id.idtextView)).getText().toString() );

        //premi sul pulsante Apri dialog
        ((Button)activity.findViewById(R.id.dialogoButton)).performClick();



        //dialog: verifica che non è nullo
        assertTrue(ShadowDialog.getLatestDialog().isShowing());


        //testa il titolo del dialog
        assertEquals("Dialog prova",shadowOf(ShadowDialog.getLatestDialog()).getTitle().toString() );


        //scrivi nel testo Michela
        ((TextInputEditText)ShadowDialog.getLatestDialog().findViewById(R.id.idinputText)).setText("Michela");


        //esci dal dialog
        ((Button)ShadowDialog.getLatestDialog().findViewById(R.id.idBottoneIndietro)).performClick();


      //cambia activity

        ((Button) activity.findViewById((R.id.changeActivity))).performClick();

         //verifica che siamo passati in una nuova activity.

        Intent expectedIntent = new Intent(activity, SecondActivity.class);
        Intent actual = ShadowApplication.getInstance().getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());


        //se siamo passati nell'activity giusta controlliamo il testo.

        SecondActivity activity1 = Robolectric.setupActivity(SecondActivity.class);

        assertEquals("ChangeActivityRobolectric",((TextView)activity1.findViewById(R.id.textView)).getText().toString());

    }
}