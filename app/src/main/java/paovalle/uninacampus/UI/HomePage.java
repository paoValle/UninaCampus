package paovalle.uninacampus.UI;

import android.net.Uri;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import paovalle.uninacampus.R;

public class HomePage extends AppCompatActivity implements MenuGraf.OnFragmentInteractionListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        Button menuBtn = (Button) findViewById(R.id.menuBtn);
        menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayout ll = (LinearLayout) findViewById(R.id.linearfrag);
                ll.setVisibility(View.VISIBLE);
                Log.w("INFO", "CLICCATO");

            }
        });
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
