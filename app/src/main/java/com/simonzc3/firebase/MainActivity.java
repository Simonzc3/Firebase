package com.simonzc3.firebase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText eID, eName,eEmail, ePhone;
    Button bCreate, bUpdate, bRead, bDelete;
    int cont=0;
    Users user;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        eID = (EditText) findViewById(R.id.eId);
        eName = (EditText)findViewById(R.id.eName);
        eEmail = (EditText)findViewById(R.id.eEmail);
        ePhone = (EditText) findViewById(R.id.ePhone);
        bCreate = (Button) findViewById(R.id.bCreate);
        bUpdate = (Button) findViewById(R.id.bUpdate);
        bRead = (Button) findViewById(R.id.bRead);
        bDelete = ( Button) findViewById(R.id.bDelete);



       // Write a message to the database


      //  myRef.setValue("Hello, World!");
    }

    public void onClick(View view) {
        final int id = view.getId();
        final String uid = eID.getText().toString();
        String name = eName.getText().toString();
        String email = eEmail.getText().toString();
        String phone = ePhone.getText().toString();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("usuarios").child("user"+cont);

        switch (id){
            case R.id.bCreate:
                  user = new Users("user" + cont, name, email, phone);
                myRef.setValue(user);
                cont++;
                break;
            case R.id.bUpdate:
                myRef = database.getReference("usuarios").child("user"+uid);

                Map<String,Object> newData = new HashMap<>();
                newData.put("name",name);
                newData.put("email",email);
                newData.put("phone",phone);
                myRef.updateChildren(newData);

                break;
            case R.id.bRead:
                myRef = database.getReference("usuarios");
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child("user"+uid).exists()){
                            user = dataSnapshot.child("user"+uid).getValue(Users.class);
                            eName.setText(user.getName());
                            eEmail.setText(user.getEmail());
                            ePhone.setText(user.getPhone());
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                break;
            case R.id.bDelete:
                break;
        }
    }
}
