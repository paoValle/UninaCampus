package robotium;
import com.example.mikel_000.testapplicazione.Prova;
import com.robotium.solo.*;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.mikel_000.testapplicazione.R;


public class RobotiumTest extends ActivityInstrumentationTestCase2<Prova> {
    private Solo solo;

    public RobotiumTest() {
        super(Prova.class);
    }

    public void setUp() throws Exception {
        super.setUp();
        solo = new Solo(getInstrumentation());
        getActivity();
    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
        super.tearDown();
    }

    public void testRun() {
        // Wait for activity: 'info.bpace.munchlife.MunchLifeActivity'
        solo.waitForActivity(Prova.class, 500);
        solo.sleep(500);

        solo.clickOnView(solo.getView(R.id.dialogoButton));
        solo.sleep(500);

        //click pulsante in dialog
        solo.clickOnView(solo.getView(R.id.idBottoneIndietro));
        solo.sleep(500);

        //click su spinner
        solo.clickOnView(solo.getView(R.id.idspinner));
        solo.goBack();
        solo.sleep(500);

        //selezione elemento spinner
        Spinner s = (Spinner)solo.getView(R.id.idspinner);
        solo.clickOnView(s);
        solo.sleep(500);

        solo.scrollToTop(); // I put this in here so that it always keeps the list at start
        // select the 3th item in the spinner
        solo.clickOnView(solo.getView(TextView.class, 3));
        //click elemento lista
        solo.sleep(500);

        ListView lv = (ListView)solo.getView(R.id.idLista);
        solo.clickLongOnView(lv.getChildAt(2));
        solo.sleep(500);

        //click lungo su bottone
        solo.clickLongOnView(solo.getView(R.id.dialogoButton));
        solo.sleep(500);

        //long click elemento lista
        lv = (ListView)solo.getView(R.id.idLista);
        solo.clickLongOnView(lv.getChildAt(3));
        solo.sleep(500);

        //checkbox cliccata e controllo se checkata correttamente
        CheckBox cb =(CheckBox)solo.getView(R.id.idcheckBox);
        solo.clickOnView(cb);
        solo.sleep(500);

        assertTrue("Error! Not checked",  solo.isCheckBoxChecked(0));

        //verifica testo in elemento spinner sleezionato
        solo.sleep(500);

        assertTrue(s.getSelectedItem().toString().contains("Android"));

        //verifica esistenza elemento
        assertNotNull(solo.getView(R.id.dialogoButton));

        //scrittura campi tempo
        solo.enterText((EditText) solo.getView(R.id.testoInput), "idinputText!");

        solo.sleep(1000);



    }
}
