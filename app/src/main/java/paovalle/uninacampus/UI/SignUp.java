package paovalle.uninacampus.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;

import paovalle.uninacampus.R;

public class SignUp extends AppCompatActivity {

    private Button confBtn;
    private Button indietroBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        confBtn=(Button)findViewById(R.id.confirmButton);

        confBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptSignUp();
            }
        });

        indietroBtn=(Button) findViewById(R.id.buttomBack);

        indietroBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptSignUp();
            }
        });


    }

    private void attemptSignUp(){
        //TODO: PAOLO DEVE FARE LA LOGICA!!!!
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);

    }

}
