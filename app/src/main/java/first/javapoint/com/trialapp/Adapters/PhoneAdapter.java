package first.javapoint.com.trialapp.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import first.javapoint.com.trialapp.R;
import first.javapoint.com.trialapp.main.PhoneDisplayListener;
import first.javapoint.com.trialapp.main.PhonesDisplay;
import first.javapoint.com.trialapp.main.Welcome;
import first.javapoint.com.trialapp.responseDTO.PhonesResponse;

public class PhoneAdapter extends RecyclerView.Adapter<PhoneAdapter.ViewHolder> {

 List<PhonesResponse> Specs;

 PhoneDisplayListener pdl ;

 Context context;


 //pass the list you want to repeat/review to the adapter
 public  PhoneAdapter (List<PhonesResponse>Specs , PhoneDisplayListener pdl, Context context){
     this.Specs = Specs;
     this.pdl = pdl ;
     this.context = context;

 }

    @NonNull
    @Override
    //b7ot view (each row of the list) list gwa l recycler view
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

     View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.phonesviewlayout , parent, false);
        return new ViewHolder(view);
    }

    @Override
    //bind the data i have bl list eli 3ndy
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
     Log.i("DEBUG MENU ","onBindViewHolder");
       final PhonesResponse currentItem = Specs.get(position);


        holder.phoneName.setText(currentItem.getName());
        holder.phonePrice.setText(currentItem.getPrice());
        holder.phoneColor.setText(currentItem.getColor());
        if(currentItem.getPhoto()== ""){

            holder.phoneImg.setImageURI(null);
        }
        else {
            Picasso.with(context).load(currentItem.getPhoto())
                    .into(holder.phoneImg);
        }


        holder.phoneDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Specs.remove(position);
//                notifyDataSetChanged();

                pdl.onItemDeleted(currentItem.getId(), position);



            }
        });



        holder.phoneEditBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                pdl.onItemUpdated(currentItem,position);
            }
        });

    }

    @Override
    //get the size of the list
    public int getItemCount() {

        return Specs.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder{

     TextView  phonePrice;
     TextView phoneColor;
     TextView  phoneName;
     ImageView phoneImg;
     Button phoneEditBtn;
   //  Button phoneAddBtn;
     Button phoneDeleteBtn;

   // TextView phoneBrandname;
     //ImageView phoneImage;


        public ViewHolder(View itemView) {
            super(itemView);
           // phoneBrandname = itemView.findViewById(R.id.ph);
            phoneColor = itemView.findViewById(R.id.phonecolor);
            phoneName = itemView.findViewById(R.id.phonename);
            phonePrice  =  itemView.findViewById(R.id.phoneprice);
            phoneImg  = itemView.findViewById(R.id.phoneimage);
            phoneEditBtn = itemView.findViewById(R.id.phoneedit);
        //    phoneAddBtn  =  itemView.findViewById(R.id.phoneadd);
            phoneDeleteBtn  = itemView.findViewById(R.id.phonedelete);




        }
    }

}
