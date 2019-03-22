package com.example.agcl;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class home_activity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private Button logout;
    private Button add_btn_demo;
    private EditText demo_name, demo_mob, demo_prn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_activity);

        firebaseAuth=FirebaseAuth.getInstance();

        demo_name=(EditText)findViewById(R.id.et_demo_name);
        demo_mob=(EditText)findViewById(R.id.et_demo_mob);
        demo_prn=(EditText)findViewById(R.id.et_demo_prn);



        /*logout=(Button)findViewById(R.id.btn_log_out);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logout
                ();
            }
        });*/

        add_btn_demo=(Button)findViewById(R.id.btn_demo);


        add_btn_demo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name=demo_name.getText().toString();
                String mob=demo_mob.getText().toString();
                String prn=demo_prn.getText().toString();

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference(name);

                myRef.setValue(mob);
                myRef.setValue(prn);

                Toast.makeText(home_activity.this,"Data added successfully..!!",Toast.LENGTH_SHORT).show();


            }
        });


    }

    private void Logout(){
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(home_activity.this,MainActivity.class));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {

            case R.id.logoutmenu:{ Logout(); }

        }
        return super.onOptionsItemSelected(item);
    }
}
