package com.fg.mycashier;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fg.mycashier.Common.Common;
import com.fg.mycashier.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

public class LoginActivity extends AppCompatActivity {
    EditText edphone, edPassword;
    Button btnSignIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edphone = (MaterialEditText)findViewById(R.id.editPhone);
        edPassword = (MaterialEditText)findViewById(R.id.editPass);
        btnSignIn = (Button) findViewById(R.id.bLogin);

        //Initial Firebase
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        final DatabaseReference tb = db.getReference("User");

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog mDialog = new ProgressDialog(LoginActivity.this);
                mDialog.setMessage("Please Wait....");
                mDialog.show();

                tb.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        //Check user in database
                        if(dataSnapshot.child(edphone.getText().toString()).exists()){



                            mDialog.dismiss();
                            User user =dataSnapshot.child(edphone.getText().toString()).getValue(User.class);
                            if (user.getPassword().equals(edPassword.getText().toString())){
                                Toast.makeText(LoginActivity.this,"Sign in Successfull",Toast.LENGTH_SHORT).show();
                                Intent homeIntent = new Intent(LoginActivity.this,HomeActivity.class);
                                Common.currentUser = user;
                                startActivity(homeIntent);
                                finish();
                            }else
                            {
                                Toast.makeText(LoginActivity.this,"Sign in Failed",Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                        {
                            mDialog.dismiss();
                            Toast.makeText(LoginActivity.this,"User not Found",Toast.LENGTH_SHORT).show();
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
