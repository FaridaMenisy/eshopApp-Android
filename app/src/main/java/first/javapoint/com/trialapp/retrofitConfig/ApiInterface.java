package first.javapoint.com.trialapp.retrofitConfig;

import java.util.List;

import first.javapoint.com.trialapp.requestDTO.LoginRequest;
import first.javapoint.com.trialapp.requestDTO.PhonesRequest;
import first.javapoint.com.trialapp.requestDTO.TabletsRequest;
import first.javapoint.com.trialapp.responseDTO.PhonesResponse;
import first.javapoint.com.trialapp.responseDTO.SignUpResponse;
import first.javapoint.com.trialapp.responseDTO.TabletsResponse;
import first.javapoint.com.trialapp.responseDTO.UploadObject;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

//In this step we create an Interface in which we have
// registration post request method to send the data using api to our server.
//bykon feh no3 el requests eli htkon 3ndi

public interface ApiInterface {




    @POST("firstapp/log/usercheck")
    Call<SignUpResponse> Login(@Body LoginRequest loginRequest);


//    @FormUrlEncoded
//    @POST("")
//
//    Call<SignUpResponse>  registeration(@Field("username") String ntacc,
//                                        @Field("password") String password);


    // In registration method @Field used to set the keys and String data type is representing its a string type value and callback is used to get the response from api and it will set it in our POJO class


    @GET("firstapp/items/phones")
    Call <List<PhonesResponse>> RetrievePhones();


    @DELETE("firstapp/items/deletephones/{id}")
    Call <PhonesResponse> PhoneDeletion(@Path("id") int id);

    @POST("firstapp/items/addphone/")
    Call<PhonesResponse> addPhone(@Body PhonesRequest phonesRequest);

    @PUT("firstapp/items/update/{id}")
    Call<PhonesResponse> updatePhone(@Path("id") int id, @Body PhonesRequest phonesRequest);

    @Multipart
    @POST
    Call<UploadObject> uploadFile(@Part MultipartBody.Part file , @Part("name") RequestBody name);


    @GET("firstapp/tablets/listtablets")
    Call<List<TabletsResponse>> RetrieveTablets();

    @DELETE("firstapp/tablets/deletetablet/{id}")
    Call <TabletsResponse> TabletDeletion(@Path("id") int id);

    @POST("firstapp/tablets/addtablet/")
    Call<TabletsResponse> addTablet(@Body TabletsRequest tabletsRequest);

    @PUT("firstapp/tablets/update/{id}")
    Call<TabletsResponse> updateTablet(@Path("id") int id, @Body TabletsRequest tabletsRequest);



//    @GET("test/{id}")
//    Call<SignUpResponse>getUsers(@Path("id") int id);




}



