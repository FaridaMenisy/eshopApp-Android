package first.javapoint.com.trialapp.main;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.Serializable;
import java.util.List;

import first.javapoint.com.trialapp.R;
import first.javapoint.com.trialapp.responseDTO.PhonesResponse;
import first.javapoint.com.trialapp.responseDTO.TabletsResponse;
import first.javapoint.com.trialapp.retrofitConfig.Api;
import retrofit2.Call;
import retrofit2.Response;


public class Welcome  extends Activity {




    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
        ProgressBar progressBar;
        String newString;
        TextView textView;
        TextView phonesView;
        ImageView imageView;
        Button arg = null;
        ImageView img1;

//        img1 = (ImageView)findViewById(R.id.img1);
//        img1.setBackgroundColor(Color.rgb(255, 255, 255));

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            newString = null;
        } else {

            newString = extras.getString("username");
            textView = (TextView) findViewById(R.id.user);
            textView.append(newString);

        }

        phonesView = (TextView) findViewById(R.id.phones);
        imageView =(ImageView)findViewById(R.id.imgs) ;







    }

    public void click(View view) {


        if (view.getId() == R.id.phones || view.getId() == R.id.img1) {
//
//            Api.getClient().RetrievePhones().enqueue(new retrofit2.Callback<List<PhonesResponse>>() {
//                @Override
//                public void onResponse(Call<List<PhonesResponse>> call, Response<List<PhonesResponse>> response) {
//
//
//                    List<PhonesResponse> phonesResponse = response.body();
                    Intent intent = new Intent(Welcome.this, PhonesDisplay.class);
                    intent.putExtra("type", 0);
                    startActivity(intent);

//                        String name = phonesResponse.get(0).getPrice();
//                        Toast.makeText(getApplicationContext(), name, Toast.LENGTH_SHORT).show();

                }

//                @Override
//                public void onFailure(Call<List<PhonesResponse>> call, Throwable t) {
//
//                }
//            });



        else if(view.getId() == R.id.tabletimg2  || view.getId() == R.id.tablets){



//            Api.getClient().RetrieveTablets().enqueue(new retrofit2.Callback<List<TabletsResponse>>() {
//                @Override
//                public void onResponse(Call<List<TabletsResponse>> call, Response<List<TabletsResponse>> response) {


                 //   List<TabletsResponse> tabletsResponse = response.body();
                    Intent intent = new Intent(Welcome.this, TabletsDisplay.class);
                    intent.putExtra("type", 1);
                    startActivity(intent);

//                        String name = phonesResponse.get(0).getPrice();
//                        Toast.makeText(getApplicationContext(), name, Toast.LENGTH_SHORT).show();

                }

//                @Override
//                public void onFailure(Call<List<TabletsResponse>> call, Throwable t) {
//
//                }
//            });





        }
    }

