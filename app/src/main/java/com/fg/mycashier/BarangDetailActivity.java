package com.fg.mycashier;

import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.fg.mycashier.Model.Barang;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class BarangDetailActivity extends AppCompatActivity {

    TextView namaBrg,hargaBrg, descBrg;
    ImageView imgBrg;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton btnCart;
    ElegantNumberButton numberButton;

    String BarangId="";

    FirebaseDatabase db;
    DatabaseReference detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barang_detail);

        db = FirebaseDatabase.getInstance();
        detail = db.getReference("Foods");

        numberButton = (ElegantNumberButton) findViewById(R.id.number_counter);
        btnCart = (FloatingActionButton) findViewById(R.id.btnCart);
        namaBrg = (TextView)findViewById(R.id.namaBarang);
        hargaBrg = (TextView)findViewById(R.id.hargaBarang);
        imgBrg = (ImageView)findViewById(R.id.img_brg);
        descBrg=(TextView)findViewById(R.id.descBrg);

        collapsingToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.collapsing);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppbar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapseAppbar);

        if(getIntent()!=null)
            BarangId= getIntent().getStringExtra("BarangId");
        if(!BarangId.isEmpty())
        {
            getDetailBarang(BarangId);
        }
    }

    private void getDetailBarang(String barangId) {
        detail.child(BarangId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Barang brg = dataSnapshot.getValue(Barang.class);
                Picasso.with(getBaseContext()).load(brg.getImage()).into(imgBrg);
                namaBrg.setText(brg.getNama());
                hargaBrg.setText(brg.getPrice());
                descBrg.setText(brg.getDescription());
                collapsingToolbarLayout.setTitle(brg.getNama());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
