package first.javapoint.com.trialapp.main;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Window;

import first.javapoint.com.trialapp.R;

public class Register_user extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.register_dialog);
        dialog.show();


        /*if(dialog.isShowing()) {

            TextView txt = (TextView) findViewById(R.id.register_txt4);
            txt.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    Toast.makeText(getApplicationContext(), "sds", Toast.LENGTH_LONG).show();

                }
            });

        }*/

    }






}
