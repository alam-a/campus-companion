package in.campanion.campuscompanion;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
/**
 * Created by aftab on 16-12-2016.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder>{
    String[] name, brief_detail,locality;


    public RecyclerAdapter (String[] name, String[] brief_detail,String[] locality){
        this.name = name;
        this.brief_detail = brief_detail;
        this.locality = locality;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout,parent,false);
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.Tx_name.setText(name[position]);
        holder.Tx_brief_detail.setText(brief_detail[position]);
        //holder.Tx_locaity.setText(locality[position]);
    }

    @Override
    public int getItemCount() {
        return name.length;
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder{
        TextView Tx_name, Tx_brief_detail,Tx_locaity;
        public RecyclerViewHolder(View view){
            super(view);
            Tx_name = (TextView) view.findViewById(R.id.tx_name);
            Tx_brief_detail = (TextView) view.findViewById(R.id.tx_brief_detail);
           // Tx_locaity = (TextView) view.findViewById(R.id.tx_locality);
        }
    }
}

