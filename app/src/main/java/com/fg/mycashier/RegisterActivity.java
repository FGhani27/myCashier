package com.fg.mycashier;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.fg.mycashier.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

public class RegisterActivity extends AppCompatActivity {

    MaterialEditText edPhone, edName, edPass;
    Button signUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        edPhone = (MaterialEditText)findViewById(R.id.editPhone);
        edName =  (MaterialEditText)findViewById(R.id.editNama);
        edPass =  (MaterialEditText)findViewById(R.id.editPass);

        signUp = (Button) findViewById(R.id.bSignup);

        //Initial Firebase
        final FirebaseDatabase db = FirebaseDatabase.getInstance();
        final DatabaseReference tb = db.getReference("User");

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog mDialog = new ProgressDialog(RegisterActivity.this);
                mDialog.setMessage("Please Wait....");
                mDialog.show();

                tb.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        //Check User Phone
                        if(dataSnapshot.child(edPhone.getText().toString()).exists())
                        {
                            mDialog.dismiss();
                            Toast.makeText(RegisterActivity.this, "Phone Number Already Registered", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            mDialog.dismiss();
                            User user = new User(edName.getText().toString(),edPass.getText().toString(),edPhone.getText().toString());
                            tb.child(edPhone.getText().toString()).setValue(user);
                            Toast.makeText(RegisterActivity.this, edPhone.getText()+" Register Successful", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });
    }
}
