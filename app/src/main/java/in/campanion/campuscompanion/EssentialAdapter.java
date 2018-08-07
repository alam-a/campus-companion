package in.campanion.campuscompanion;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by aftab on 31-12-2016.
 */

public class EssentialAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder> {
    ArrayList<SetData> arrayList= new ArrayList<>();

    public EssentialAdapter (ArrayList<SetData> arrayList){
        this.arrayList = arrayList;
    }

    @Override
    public RecyclerAdapter.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout,parent,false);
        RecyclerAdapter.RecyclerViewHolder recyclerViewHolder = new RecyclerAdapter.RecyclerViewHolder(view);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.RecyclerViewHolder holder, int position) {
        SetData setData = arrayList.get(position);
        holder.Tx_name.setText(setData.getName());
        holder.Tx_brief_detail.setText(setData.getBrief());
        //holder.Tx_locaity.setText(setData.getLocality());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder{
        TextView Tx_name, Tx_brief_detail,Tx_locality;
        public RecyclerViewHolder(View view){
            super(view);
            Tx_name = (TextView) view.findViewById(R.id.tx_name);
            Tx_brief_detail = (TextView) view.findViewById(R.id.tx_brief_detail);
            //Tx_locality = (TextView) view.findViewById(R.id.tx_locality);
        }
    }
}
