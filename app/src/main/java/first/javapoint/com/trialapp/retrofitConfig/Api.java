package first.javapoint.com.trialapp.retrofitConfig;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//To send out network requests to an API, we need to use the Retrofit builder class and specify the base URL for the service.

//create instance of retrofit then pass the base url to it and then create connection
// between the new instance and the api interface  w return object of it
//a2dr a call it any where ineed
public class Api {

    private static Retrofit retrofit  =null;


    public static ApiInterface getClient(){

        if(retrofit == null ){

            // Retrofit relied on the Gson library to serialize and deserialize JSON data

            retrofit  = new Retrofit.Builder().baseUrl("http://10.100.255.136:8080/").addConverterFactory(GsonConverterFactory.create()).build();
          //  retrofit  = new Retrofit.Builder().baseUrl("http://10.100.252.13:4500/").addConverterFactory(GsonConverterFactory.create()).build();

        }

        //Creating object for our interface
        ApiInterface api = retrofit.create(ApiInterface.class);
        return api; // return the APIInterface object
    }
}
