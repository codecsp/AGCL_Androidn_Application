package com.example.agcl;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegistrationActivity extends AppCompatActivity
{


    private EditText userName, userPassword,userMail;
    private Button regButton;
    private TextView userLogin;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        setupUIViews();

        firebaseAuth=FirebaseAuth.getInstance();

            regButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(validate())
                    {
                        //upload to database
                        String user_email=userMail.getText().toString().trim();
                        String user_pass=userPassword.getText().toString().trim();

                        //firebaseAuth.signInWithCredential()
                        firebaseAuth.createUserWithEmailAndPassword(user_email,user_pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                   /* Toast.makeText(RegistrationActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(RegistrationActivity.this,MainActivity.class));*/

                                   sendemailverification();

                                } else {
                                    Toast.makeText(RegistrationActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }

                }
            });

            userLogin.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(RegistrationActivity.this,MainActivity.class));

                }
            });

    }

    private void setupUIViews()
    {
        userName=(EditText)findViewById(R.id.etusername);
        userPassword=(EditText)findViewById(R.id.etuserpass);
        userMail=(EditText)findViewById(R.id.etusermail);
        regButton=(Button)findViewById(R.id.btnregister);
        userLogin=(TextView)findViewById(R.id.tvuserlogin);

    }

    private boolean validate()
    {
        Boolean result= false;

        String name=userName.getText().toString();
        String password=userPassword.getText().toString();
        String email=userMail.getText().toString();

        if(name.isEmpty() || password.isEmpty() || email.isEmpty())
        {
            Toast.makeText(this,"please enter all the details",Toast.LENGTH_SHORT).show();


        }
        else
        {
            result=true;


        }
        return result;




    }


    private void sendemailverification() {

        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null){

            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){

                    Toast.makeText(RegistrationActivity.this,"successfully registerd, verification mail has been sent ",Toast.LENGTH_SHORT).show();
                    firebaseAuth.signOut();
                    finish();
                    startActivity(new Intent(RegistrationActivity.this,MainActivity.class));

                }
                else
                {
                    Toast.makeText(RegistrationActivity.this,"verifification mail has not been sent", Toast.LENGTH_SHORT).show();

                }

                }
            });
        }

    }

}
