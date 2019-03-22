package com.example.agcl;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private TextView userregistration;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private Button Login;
    private EditText Name;
    private EditText Pass;
    private TextView forgot_pass;
   // private Firebase mref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userregistration =(TextView)findViewById(R.id.tvregister);
        Login = (Button)findViewById(R.id.btnlogin);
        Name=(EditText)findViewById(R.id.name);
        Pass=(EditText)findViewById(R.id.pass);
        forgot_pass=(TextView)findViewById(R.id.tvforgot_password);



        firebaseAuth=FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(this);

        FirebaseUser user=firebaseAuth.getCurrentUser();

        if(user !=null)
        {

            finish();
            startActivity(new Intent(MainActivity.this,home_activity.class));
        }

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valiadate(Name.getText().toString(),Pass.getText().toString());
            }
        });

        userregistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RegistrationActivity.class));
            }
        });


        forgot_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Forgot_Password.class));

            }
        });


        // Write a message to the database
       /* FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");


        myRef.setValue("Patil, soham");
        myRef.child("branch, IT");*/

       // myRef.setValue("soham, patil!");

        // Read from the database


    }

    private void valiadate (String username, String userPassword) {
        progressDialog.setMessage("Logging in...!!!");
        progressDialog.show();


        if (username.isEmpty() || userPassword.isEmpty()) {
            Toast.makeText(MainActivity.this, "Email or Password field is empty...!!", Toast.LENGTH_SHORT).show();
            finish();
            //return ;
            startActivity(getIntent());


        }else
        {
            firebaseAuth.signInWithEmailAndPassword(username, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        progressDialog.dismiss();
                        // Toast.makeText(MainActivity.this, "Login successfull !!! ",Toast.LENGTH_SHORT).show();
                        //startActivity(new Intent(MainActivity.this,home_activity.class));
                        checkemailverification();
                    } else {

                        progressDialog.dismiss();
                        Toast.makeText(MainActivity.this, "Login failed !!! ", Toast.LENGTH_SHORT).show();

                    }
                }
            });

        }
    }


    private void checkemailverification()
    {
        FirebaseUser firebaseuser= firebaseAuth.getInstance().getCurrentUser();
        Boolean emialflag=firebaseuser.isEmailVerified();

        if(emialflag)
        {
            startActivity(new Intent(MainActivity.this, home_activity.class));

        }
        else
        {

            Toast.makeText(this,"verify your email", Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();
        }

    }

}
