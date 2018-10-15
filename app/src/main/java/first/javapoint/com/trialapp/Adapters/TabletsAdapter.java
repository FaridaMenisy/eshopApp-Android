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
import first.javapoint.com.trialapp.responseDTO.PhonesResponse;
import first.javapoint.com.trialapp.responseDTO.TabletsResponse;

public class TabletsAdapter extends RecyclerView.Adapter<TabletsAdapter.ViewHolder> {

    List<TabletsResponse> Specs;

    PhoneDisplayListener pdl ;

    Context context;


    //pass the list you want to repeat/review to the adapter
    public  TabletsAdapter (List<TabletsResponse>Specs , PhoneDisplayListener pdl, Context context){
        this.Specs = Specs;
        this.pdl = pdl ;
        this.context = context;

    }

    @NonNull
    @Override
    //b7ot view (each row of the list) list gwa l recycler view
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tabletsviewlayout , parent, false);
        return new ViewHolder(view);
    }

    @Override
    //bind the data i have bl list eli 3ndy
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.i("DEBUG MENU ","onBindViewHolder");
        final TabletsResponse currentItem = Specs.get(position);


        holder.tabletname.setText(currentItem.getTabletname());
        holder.tabletprice.setText(currentItem.getTabletprice());
        holder.tabletcolor.setText(currentItem.getColor());
        holder.tabletcapacity.setText(currentItem.getCapacity());
        holder.tabletInstallmentduration.setText(currentItem.getInstallmentduration());
        holder.tabletInstallationPlan.setText(currentItem.getInstallmentplan());
        if(currentItem.getPhoto()== ""){

            holder.tabletimage.setImageURI(null);
        }
        else {
            Picasso.with(context).load(currentItem.getPhoto())
                    .into(holder.tabletimage);
        }


        holder.tabletDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Specs.remove(position);
//                notifyDataSetChanged();

                pdl.onItemDeleted(currentItem.getId(), position);



            }
        });



        holder.TabletEdit.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                pdl.onTabletItemUpdated(currentItem,position);
            }
        });

    }

    @Override
    //get the size of the list
    public int getItemCount() {

        return Specs.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder{

        TextView tabletname;
        TextView tabletprice;
        TextView  tabletcolor;
        TextView tabletcapacity;
        TextView tabletInstallationPlan;
        TextView tabletInstallmentduration;
        ImageView tabletimage;
        Button tabletDelete;
        //  Button phoneAddBtn;
        Button TabletEdit;

        // TextView phoneBrandname;
        //ImageView phoneImage;


        public ViewHolder(View itemView) {
            super(itemView);
            // phoneBrandname = itemView.findViewById(R.id.ph);
            tabletcolor = itemView.findViewById(R.id.tabletcolor);
            tabletname = itemView.findViewById(R.id.tabletname);
            tabletprice  =  itemView.findViewById(R.id.tabletprice);
            tabletimage  = itemView.findViewById(R.id.tabletimage);
            tabletInstallationPlan  = itemView.findViewById(R.id.tabletinstallationplan);
            tabletInstallmentduration  = itemView.findViewById(R.id.tabletinstallationduration);
            tabletcapacity  = itemView.findViewById(R.id.tabletcapacity);

            TabletEdit = itemView.findViewById(R.id.tabletedit);
            //    phoneAddBtn  =  itemView.findViewById(R.id.phoneadd);
            tabletDelete  = itemView.findViewById(R.id.tabletdelete);




        }
    }

}
