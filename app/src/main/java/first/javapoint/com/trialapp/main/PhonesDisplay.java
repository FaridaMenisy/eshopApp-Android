package first.javapoint.com.trialapp.main;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

import first.javapoint.com.trialapp.Adapters.PhoneAdapter;
import first.javapoint.com.trialapp.Adapters.TabletsAdapter;
import first.javapoint.com.trialapp.R;
import first.javapoint.com.trialapp.requestDTO.PhonesRequest;
import first.javapoint.com.trialapp.requestDTO.TabletsRequest;
import first.javapoint.com.trialapp.responseDTO.PhonesResponse;
import first.javapoint.com.trialapp.responseDTO.TabletsResponse;
import first.javapoint.com.trialapp.retrofitConfig.Api;
import retrofit2.Call;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class PhonesDisplay extends Activity implements PhoneDisplayListener {

    RecyclerView rv;
    List<PhonesResponse> phonesResponse;
    List<TabletsResponse> tabletsResponses;
    List<PhonesRequest> phonesRequest;
    PhoneAdapter phoneAdapter;
    String filename;
    TabletsAdapter tabletsAdapter;
    private String upLoadServerUri = null;
    String filepath;
    // String file;
    String decodedimage;
    Button phoneAdd;
    Dialog dialog;
    Dialog d;
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phonesdisplay);
        rv = findViewById(R.id.rv);
        Button phoneAddBtn;
        upLoadServerUri = "10.100.255.136/firstapps/";
        //phones display bt3red l recycler view
        phoneAddBtn = (Button) findViewById(R.id.phoneadd);
        phoneAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemAdded();
            }
        });

        int i = getIntent().getIntExtra("type", 0);
        int x = getIntent().getIntExtra("type", 1);
        if (i == 0) {
            Toast.makeText(getApplicationContext(), "this should display phones", Toast.LENGTH_LONG).show();


            Api.getClient().RetrievePhones().enqueue(new retrofit2.Callback<List<PhonesResponse>>() {
                @Override
                public void onFailure(Call<List<PhonesResponse>> call, Throwable t) {


                }

                @Override
                public void onResponse(Call<List<PhonesResponse>> call, Response<List<PhonesResponse>> response) {


                    phonesResponse = response.body();
                    String photo = phonesResponse.get(0).getPhoto();

                    phoneAdapter = new PhoneAdapter(phonesResponse, PhonesDisplay.this, PhonesDisplay.this);
//

                    rv.setLayoutManager(new LinearLayoutManager(PhonesDisplay.this));
//
                    rv.addItemDecoration(new DividerItemDecoration(PhonesDisplay.this, LinearLayoutManager.VERTICAL));

                    rv.setAdapter(phoneAdapter);

                }


            });
        }
        //for displaying the tablets
        else if (x == 1) {


            Toast.makeText(getApplicationContext(), "this should display tablets", Toast.LENGTH_LONG).show();


            Api.getClient().RetrieveTablets().enqueue(new retrofit2.Callback<List<TabletsResponse>>() {
                @Override
                public void onFailure(Call<List<TabletsResponse>> call, Throwable t) {


                }

                @Override
                public void onResponse(Call<List<TabletsResponse>> call, Response<List<TabletsResponse>> response) {


                    tabletsResponses = response.body();
                    String photo = tabletsResponses.get(0).getPhoto();

                    tabletsAdapter = new TabletsAdapter(tabletsResponses, PhonesDisplay.this, PhonesDisplay.this);
//

                    rv.setLayoutManager(new LinearLayoutManager(PhonesDisplay.this));
//
                    rv.addItemDecoration(new DividerItemDecoration(PhonesDisplay.this, LinearLayoutManager.VERTICAL));

                    rv.setAdapter(tabletsAdapter);

                }


//      List<PhonesResponse> list = new ArrayList<PhonesResponse>();
//      list = (List<PhonesResponse>) i.getSerializableExtra("list");

                // List<PhonesResponse>   phones =  (List<PhonesResponse> ) getIntent().getExtras().getSerializable("list");


            });


        }
    }

    @Override
    public void onItemDeleted(final int id, final int position) {

            Api.getClient().PhoneDeletion(id).enqueue(new retrofit2.Callback<PhonesResponse>() {
                @Override
                public void onResponse(Call<PhonesResponse> call, Response<PhonesResponse> response) {

                    if (response.isSuccessful() && response.body() != null) {
                        Log.i(TAG, "onResponse: success: theResponseFieldIWant1: " + response.body().getId());
                        //PhonesResponse  resp  = response.body();
                        // Toast.makeText(getApplicationContext(), response.body().getName() , Toast.LENGTH_LONG).show();
                        phonesResponse.remove(position);
                        //phoneAdapter.notifyItem
                        phoneAdapter.notifyDataSetChanged();
                        Toast.makeText(getApplicationContext(), "one Item deleted", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.i(TAG, "onResponse: something went wrong with the response object " + response.body());
                    }


                }

                @Override
                public void onFailure(Call<PhonesResponse> call, Throwable t) {

                    Log.i(TAG, "onFailure: to: " + call.request().url() + " req " + call.request());

                }
            });

        }

    //@Override
    public void onItemAdded() {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.addphone);
        dialog.show();

        final EditText addname = (EditText) dialog.findViewById(R.id.addphonename);
        final EditText addcolor = (EditText) dialog.findViewById(R.id.addphonecolor);
        final EditText addprice = (EditText) dialog.findViewById(R.id.addphoneprice);
        final EditText addbrandname = (EditText) dialog.findViewById(R.id.addphonebrand);
      //  final EditText addphoto = (EditText) dialog.findViewById(R.id.addphonephoto);


        Button attachphoto = (Button) dialog.findViewById(R.id.attachphoto);

        attachphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Complete action using"), 1);

            }
            //   String file = convertToBase64(filepath);

        });


//        PhonesRequest req = new PhonesRequest();
//        req.setBrandname(addbrandname.getText().toString().trim());
        Button submit = (Button) dialog.findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                submitted(addname, addcolor, addprice, addbrandname);

            }
        });
    }

    //when user clicks on edit button
    @Override
    public void onItemUpdated(final PhonesResponse pr, final int poistion) {

        d = new Dialog(this);
        d.requestWindowFeature(Window.FEATURE_NO_TITLE);
        d.setContentView(R.layout.editphone);
        d.show();
        // Toast.makeText(getApplicationContext(),pr.getName(),Toast.LENGTH_LONG).show();
        final EditText editText = (EditText) d.findViewById(R.id.edittxtphonename);
        editText.setText(pr.getName());
        final EditText editText2 = (EditText) d.findViewById(R.id.edittxtphonebrand);
        editText2.setText(pr.getBrandname());
        final EditText editText4 = (EditText) d.findViewById(R.id.edittxtphoneprice);
        editText4.setText(pr.getPrice());
        final EditText editText5 = (EditText) d.findViewById(R.id.edittxtphonecolor);
        editText5.setText(pr.getColor());
//        if(pr.getPhoto() == null){
//            Toast.makeText(getApplicationContext(),"null", Toast.LENGTH_LONG).show();
//        }
//        else{
//        final ImageView photoview = (ImageView)d.findViewById(R.id.imgView);
//        Picasso.with(this).load(pr.getPhoto())
//                .into(photoview);}
//        final EditText editText3 = (EditText) d.findViewById(R.id.edittxtphonephoto);
//        editText3.setText(pr.getPhoto());

        Button attach = (Button) d.findViewById(R.id.editattachphoto);

        attach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Complete action using"), 1);
            }
            //   String file = convertToBase64(filepath);

        });

        Button submit = (Button) d.findViewById(R.id.sumbitedit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PhonesRequest req = new PhonesRequest();
                req.setName(editText.getText().toString().trim());
                req.setBrandname(editText2.getText().toString().trim());
                req.setPrice(editText4.getText().toString().trim());
                req.setColor(editText5.getText().toString().trim());
              //  req.setPhoto(editText3.getText().toString().trim());
              //  req.setImageName(filename);
                req.setPhoto(decodedimage);
                req.setImageName(filename);


                Api.getClient().updatePhone(pr.getId(), req).enqueue(new retrofit2.Callback<PhonesResponse>() {
                    @Override
                    public void onResponse(Call<PhonesResponse> call, Response<PhonesResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {

                            Log.i(TAG, "onResponse: success: theResponseFieldIWant1: " + response.body().getName().toString().trim());
                            Toast.makeText(getApplicationContext(), response.body().getName().toString().trim(), Toast.LENGTH_LONG).show();
                            phonesResponse.remove(poistion);
                            phonesResponse.add(response.body());
                            phoneAdapter.notifyDataSetChanged();

                        }
                    }

                    @Override
                    public void onFailure(Call<PhonesResponse> call, Throwable t) {
                        Log.i(TAG, "onFailure: to: " + call.request().url() + " req " + call.request());
                    }
                });

            }
        });


    }

    @Override
    public void onTabletItemUpdated(final TabletsResponse tb, final int poistion) {
        Dialog tabletEditDialog = new Dialog(this);
        tabletEditDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        tabletEditDialog.setContentView(R.layout.tableteditdialog);
        tabletEditDialog.show();
        // Toast.makeText(getApplicationContext(),pr.getName(),Toast.LENGTH_LONG).show();
        final EditText editText = (EditText) tabletEditDialog.findViewById(R.id.edittxttabletnme);
        editText.setText(tb.getTabletname());
        final EditText editText2 = (EditText) tabletEditDialog.findViewById(R.id.edittxttabletprice);
        editText2.setText(tb.getTabletprice());
        final EditText editText4 = (EditText) tabletEditDialog.findViewById(R.id.edittexttabletcolor);
        editText4.setText(tb.getColor());
        final EditText editText5 = (EditText) tabletEditDialog.findViewById(R.id.edittxttabletcapacity);
        editText5.setText(tb.getCapacity());
//        final EditText editText3 = (EditText) tabletEditDialog.findViewById(R.id.edittexttabletphoto);
//        editText3.setText(tb.getPhoto());

        Button submit = (Button) tabletEditDialog.findViewById(R.id.sumbitedit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TabletsRequest req = new TabletsRequest();
                req.setTabletname(editText.getText().toString().trim());
                req.setTabletprice(editText2.getText().toString().trim());
                req.setColor(editText4.getText().toString().trim());
                req.setCapacity(editText5.getText().toString().trim());
           //     req.setPhoto(editText3.getText().toString().trim());


                Api.getClient().updateTablet(tb.getId(), req).enqueue(new retrofit2.Callback<TabletsResponse>() {
                    @Override
                    public void onResponse(Call<TabletsResponse> call, Response<TabletsResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {

                            Log.i(TAG, "onResponse: success: theResponseFieldIWant1: " + response.body().getTabletname().toString().trim());
                            Toast.makeText(getApplicationContext(), "One Item Added", Toast.LENGTH_LONG).show();
                            tabletsResponses.remove(poistion);
                            tabletsResponses.add(response.body());
                            tabletsAdapter.notifyDataSetChanged();

                        }
                    }


                    @Override
                    public void onFailure(Call<TabletsResponse> call, Throwable t) {
                        Log.i(TAG, "onFailure: to: " + call.request().url() + " req " + call.request());
                    }
                });

            }
        });


    }


// once the selection has been made, we’ll show up the image in the Activity/Fragment user interface,
// //using an ImageView. For this, we’ll have to override onActivityResult():

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        Toast.makeText(getApplicationContext(),"New photo selected", Toast.LENGTH_LONG).show();
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {

            uri = data.getData();

            try {

                //
                //

                filepath = getRealPath(uri, PhonesDisplay.this);
                File file = new File(filepath);
                filename = file.getName();
                //String f = file.getAbsolutePath();
                decodedimage = convertToBase64(uri);
//                Log.i(TAG,"filename" + file.getName());
//                Log.i(TAG,"filename" + file.getName());
//                Log.i(TAG,"filename" + file.getName());
//
//                RequestBody mfile = RequestBody.create(MediaType.parse("image/*"), file);
//                MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(),mfile);
//                RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());
//                Api.getClient().uploadFile(fileToUpload,filename).enqueue(new retrofit2.Callback<UploadObject>() {
//                    @Override
//                    public void onResponse(Call<UploadObject> call, Response<UploadObject> response) {
//
//                        Toast.makeText(PhonesDisplay.this ,"Response"+ response.raw().message(), Toast.LENGTH_LONG).show();
//                        Toast.makeText(PhonesDisplay.this ,"Response"+ response.body().getSuccess(), Toast.LENGTH_LONG).show();
//
//                    }
//
//                    @Override
//                    public void onFailure(Call<UploadObject> call, Throwable t) {
//
//                        Log.d(TAG, "Error"+ t.getMessage());
//                    }
//                });

//                Log.d(TAG, String.valueOf(bitmap));
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                ImageView imageView = (ImageView) dialog.findViewById(R.id.imgView);
                if(dialog == null){

                    ImageView imageView2 = (ImageView) d.findViewById(R.id.imgView);
                    //   imageView.setImageBitmap(bitmap);
                    imageView2.setImageBitmap(bitmap);
                }
                else{
                    imageView.setImageBitmap(bitmap);
                   }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

    public String getRealPath(Uri uri, Activity activity) {

        Cursor cursor = activity.getContentResolver().query(uri, null, null, null, null);
        if (cursor == null) {

            return uri.getPath();
        } else {

            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }

    }


    public void submitted(EditText addname, EditText addcolor, EditText addprice, EditText addbrandname) {
        String name = addname.getText().toString().trim();
        final PhonesRequest req = new PhonesRequest();
        req.setName(addname.getText().toString().trim());
        req.setColor(addcolor.getText().toString().trim());
        req.setBrandname(addbrandname.getText().toString().trim());
        req.setPrice(addprice.getText().toString().trim());
        req.setPhoto(decodedimage);
        req.setImageName(filename);

        Api.getClient().addPhone(req).enqueue(new retrofit2.Callback<PhonesResponse>() {
            @Override
            public void onResponse(Call<PhonesResponse> call, Response<PhonesResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.i(TAG, "onResponse: success: theResponseFieldIWant1: " + response.body().getName());
                    Toast.makeText(getApplicationContext(), "heyyy", Toast.LENGTH_LONG).show();
                    phonesResponse.add(response.body());
                    phoneAdapter.notifyDataSetChanged();


                }


            }

            @Override
            public void onFailure(Call<PhonesResponse> call, Throwable t) {
                Log.i(TAG, "onFailure: to: " + call.request().url() + " req " + call.request());

            }
        });

    }

    private String convertToBase64(Uri uri) {

        Bitmap bm = null;
        try {
            bm = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        if (bm != null)
            bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);

        byte[] byteArrayImage = baos.toByteArray();

        String encodedImage = Base64.encodeToString(byteArrayImage, Base64.DEFAULT);

        return encodedImage;

    }
}










