package com.fg.mycashier;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.fg.mycashier.Interface.ItemClickListener;
import com.fg.mycashier.Model.Barang;
import com.fg.mycashier.ViewHolder.BarangViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

public class BarangActivity extends AppCompatActivity {

    RecyclerView rv;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase db;
    DatabaseReference barang;

    String categoryId = "";
    FirebaseRecyclerAdapter<Barang, BarangViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barang);

        db = FirebaseDatabase.getInstance();
        barang = db.getReference("Foods");
        rv = (RecyclerView) findViewById(R.id.recycler_barang);
        rv.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(layoutManager);



        if (getIntent() != null)
           categoryId = getIntent().getStringExtra("CategoryId");
        if (!categoryId.isEmpty() && categoryId != null) {
         loadListBarang(categoryId);
         }


    }

    private void loadListBarang(String categoryId) {
        adapter = new FirebaseRecyclerAdapter<Barang, BarangViewHolder>(Barang.class,R.layout.barang_item,
                BarangViewHolder.class,barang.orderByChild("menuId").equalTo(categoryId))
        {
            @Override
            protected void populateViewHolder(BarangViewHolder viewHolder, Barang model, int position) {
                viewHolder.namaBarang.setText(model.getNama());
                Picasso.with(getBaseContext()).load(model.getImage()).into(viewHolder.imgBarang);
                final Barang local = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent barangDetail = new Intent(BarangActivity.this,BarangDetailActivity.class);
                         barangDetail.putExtra("BarangId", adapter.getRef(position).getKey());
                         startActivity(barangDetail);
                    }
                });

            }

       };
        Log.d("TAG",""+adapter.getItemCount());
        rv.setAdapter(adapter);
    }
}
