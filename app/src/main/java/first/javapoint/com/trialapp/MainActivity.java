package first.javapoint.com.trialapp;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import first.javapoint.com.trialapp.R;
import first.javapoint.com.trialapp.main.Welcome;
import first.javapoint.com.trialapp.requestDTO.LoginRequest;
import first.javapoint.com.trialapp.responseDTO.PhonesResponse;
import first.javapoint.com.trialapp.responseDTO.SignUpResponse;
import first.javapoint.com.trialapp.retrofitConfig.Api;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class MainActivity extends Activity {
    EditText username, password;
    Button login, register;
    Button check;


    boolean clicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = (EditText) findViewById(R.id.edittext);
        password = (EditText) findViewById(R.id.edittext2);
        login = (Button) findViewById(R.id.button);
        register = (Button) findViewById(R.id.button3);


        //check=(Button) findViewById(R.id.button2);

//        check.setOnClickListener(new View.OnClickListener() {
//                                     @Override
//                                     public void onClick(View view) {
//                                         Api.getClient().getUsers(1).enqueue(new Callback<SignUpResponse>() {
//                                             @Override
//                                             public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
//                                                 if (response.isSuccessful() && response.body() != null) {
//                                                     Toast.makeText(getApplicationContext(), response.body().getUsername(), Toast.LENGTH_LONG).show();
//
//                                                 }
//                                             }
//
//                                             @Override
//                                             public void onFailure(Call<SignUpResponse> call, Throwable t) {
//                                                 Log.i(TAG, "onFailure: to: " + call.request().url() + " req " + call.request());
//                                             }
//                                         });
//
//                                     };
//
//                                 });

     //   progressBar = (ProgressBar) findViewById(R.id.simpleProgressBar);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

             //   progressBar.setVisibility(View.VISIBLE);
                if ( validate(password)  && validate(username)) {
                    Login();
                }


            }
        });


    }

    public boolean validate(EditText editText) {


        if (editText.getText().toString().trim().length() > 0) {

            return true;



        }
        else
            editText.setError("please fill the form");
            editText.requestFocus();
        return false;

    }

    public void Login() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.username  =    username.getText().toString().trim();
        loginRequest.password  =   password.getText().toString().trim();

        Api.getClient().Login(loginRequest).enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {


                SignUpResponse res = response.body();
                String u =  res.getUsername();
                Intent intent = new Intent(MainActivity.this  , Welcome.class);

                intent.putExtra("username" , u);
                startActivity(intent);




            }

            @Override
            public void onFailure(Call<SignUpResponse> call, Throwable t) {

            }
        });
    }


    public void dialog(View view) {
        //Apply Final to the variable means it cannot be changed after initialized.
            final Dialog dialog = new Dialog(this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.register_dialog);
            dialog.show();
            final EditText edt  = (EditText)dialog.findViewById(R.id.register_txt3);
          //  final EditText  edit   = dialog.findViewById(R.id.register_txt);

          final Button register_btn = (Button) dialog.findViewById(R.id.register_btn);
          register_btn.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  Signup(edt);
              }
          });

            TextView d =  dialog.findViewById(R.id.register_txt4);

            d.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   // Toast.makeText(getApplicationContext(),  edit.getText().toString(), Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                }
            });





    }

    public void Signup(EditText  txt){


        Toast.makeText(getApplicationContext(), txt.getText().toString() , Toast.LENGTH_LONG).show();
    }

}

