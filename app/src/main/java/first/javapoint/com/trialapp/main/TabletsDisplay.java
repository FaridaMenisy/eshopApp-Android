package first.javapoint.com.trialapp.main;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
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

public class TabletsDisplay extends Activity implements PhoneDisplayListener {

    RecyclerView rv;

    List<TabletsResponse> tabletsResponses;
    String filename;
    TabletsAdapter tabletsAdapter;
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
        setContentView(R.layout.tabletsdisplay);
        rv = findViewById(R.id.rv);
        Button tabletAddButton;
        //phones display bt3red l recycler view
        tabletAddButton = (Button) findViewById(R.id.tabletadd);
        tabletAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemAdded();
            }
        });


        int i = getIntent().getIntExtra("type", 1);

        //for displaying the tablets
        if (i == 1) {


            Toast.makeText(getApplicationContext(), "this should display tablets", Toast.LENGTH_LONG).show();


            Api.getClient().RetrieveTablets().enqueue(new retrofit2.Callback<List<TabletsResponse>>() {
                @Override
                public void onFailure(Call<List<TabletsResponse>> call, Throwable t) {


                }

                @Override
                public void onResponse(Call<List<TabletsResponse>> call, Response<List<TabletsResponse>> response) {


                    tabletsResponses = response.body();
                    String photo = tabletsResponses.get(0).getPhoto();

                    tabletsAdapter = new TabletsAdapter(tabletsResponses, TabletsDisplay.this, TabletsDisplay.this);
//

                    rv.setLayoutManager(new LinearLayoutManager(TabletsDisplay.this));
//
                    rv.addItemDecoration(new DividerItemDecoration(TabletsDisplay.this, LinearLayoutManager.VERTICAL));

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

            Api.getClient().TabletDeletion(id).enqueue(new retrofit2.Callback<TabletsResponse>() {
                @Override
                public void onResponse(Call<TabletsResponse> call, Response<TabletsResponse> response) {

                    if (response.isSuccessful() && response.body() != null) {
                        Log.i(TAG, "onResponse: success: theResponseFieldIWant1: " + response.body().getId());
                        //PhonesResponse  resp  = response.body();
                        // Toast.makeText(getApplicationContext(), response.body().getName() , Toast.LENGTH_LONG).show();
                        tabletsResponses.remove(position);
                        //phoneAdapter.notifyItem
                        tabletsAdapter.notifyDataSetChanged();
                        Toast.makeText(getApplicationContext(), "one Item deleted", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.i(TAG, "onResponse: something went wrong with the response object " + response.body());
                    }


                }

                @Override
                public void onFailure(Call<TabletsResponse> call, Throwable t) {

                    Log.i(TAG, "onFailure: to: " + call.request().url() + " req " + call.request());

                }
            });
        }


    //@Override
    public void onItemAdded() {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.addtablet);
        dialog.show();

        final EditText addname = (EditText) dialog.findViewById(R.id.addtabletname);
        final EditText addcolor = (EditText) dialog.findViewById(R.id.addtabletcolor);
        final EditText addprice = (EditText) dialog.findViewById(R.id.addtabletprice);
        final EditText addcapacity = (EditText) dialog.findViewById(R.id.addtabletCapacity);
        final EditText addduration = (EditText) dialog.findViewById(R.id.addduration);
        final EditText addplan = (EditText) dialog.findViewById(R.id.addplan);
        final ImageView  addphoto = (ImageView) dialog.findViewById(R.id.imgView);


        Button attachphoto = (Button) dialog.findViewById(R.id.attachphoto);

        attachphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Complete action using"), 1);
             //   Toast.makeText(getApplicationContext(),"New photo Selected", Toast.LENGTH_LONG).show();
            }
            //   String file = convertToBase64(filepath);

        });


//        PhonesRequest req = new PhonesRequest();
//        req.setBrandname(addbrandname.getText().toString().trim());
        Button submit = (Button) dialog.findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                submitted(addname, addcolor, addprice, addcapacity, addduration, addplan, addphoto);

            }
        });
    }

    //when user clicks on edit button
    @Override
    public void onItemUpdated(final PhonesResponse pr, final int poistion) {




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
        final EditText editText6 = (EditText) tabletEditDialog.findViewById(R.id.edttxtaddplan);
        editText6.setText(tb.getInstallmentplan());
        final EditText editText7 = (EditText) tabletEditDialog.findViewById(R.id.edttxtadddurat);
        editText7.setText(tb.getInstallmentduration());
       final ImageView imgtablet = (ImageView) tabletEditDialog.findViewById(R.id.imgtablet);


        Button attach  = (Button)tabletEditDialog.findViewById(R.id.attach) ;
        attach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Complete action using"), 1);

            }
        });
        Button submit = (Button) tabletEditDialog.findViewById(R.id.sumbitedit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TabletsRequest req = new TabletsRequest();
                req.setTabletname(editText.getText().toString().trim());
                req.setTabletprice(editText2.getText().toString().trim());
                req.setColor(editText4.getText().toString().trim());
                req.setCapacity(editText5.getText().toString().trim());
              //  req.setPhoto(editText3.getText().toString().trim());
                req.setInstallmentplan(editText6.getText().toString().trim());
                req.setInstallmentduration(editText.getText().toString().trim());
                req.setImagename(filename);
                req.setPhoto(decodedimage);




                Api.getClient().updateTablet(tb.getId(), req).enqueue(new retrofit2.Callback<TabletsResponse>() {
                    @Override
                    public void onResponse(Call<TabletsResponse> call, Response<TabletsResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {

                            Log.i(TAG, "onResponse: success: theResponseFieldIWant1: " + response.body().getTabletname().toString().trim());
                            Toast.makeText(getApplicationContext(), "One item updated", Toast.LENGTH_LONG).show();
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

        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Toast.makeText(getApplicationContext(),"New photo selected", Toast.LENGTH_LONG).show();
            uri = data.getData();

            try {

                //
                //

                filepath = getRealPath(uri, TabletsDisplay.this);
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


    public void submitted(EditText addname, EditText addcolor, EditText addprice, EditText addplan, EditText addcapacity, EditText addduration , ImageView photo) {
      //  String name = addname.getText().toString().trim();
        final TabletsRequest req = new TabletsRequest();
        req.setTabletname(addname.getText().toString().trim());
        req.setColor(addcolor.getText().toString().trim());
        req.setCapacity(addcapacity.getText().toString().trim());
        req.setTabletprice(addprice.getText().toString().trim());
        req.setInstallmentduration(addduration.getText().toString().trim());
        req.setInstallmentplan(addplan.getText().toString().trim());
        req.setPhoto(decodedimage);
        req.setImagename(filename);

        Api.getClient().addTablet(req).enqueue(new retrofit2.Callback<TabletsResponse>() {
            @Override
            public void onResponse(Call<TabletsResponse> call, Response<TabletsResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.i(TAG, "onResponse: success: theResponseFieldIWant1: " + response.body().getTabletname());
                    Toast.makeText(getApplicationContext(), "One Item added", Toast.LENGTH_LONG).show();
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










