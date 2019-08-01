package com.fg.mycashier.ViewHolder;

import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fg.mycashier.Interface.ItemClickListener;
import com.fg.mycashier.R;

public class BarangViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView nmBarang;
    public ImageView imgBarang;
    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }

    public BarangViewHolder(@NonNull View itemView) {
        super(itemView);
        nmBarang = (TextView)itemView.findViewById(R.id.barangNama);
        imgBarang = (ImageView)itemView.findViewById(R.id.barangImage);

        itemView.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition(),false);
    }
}
