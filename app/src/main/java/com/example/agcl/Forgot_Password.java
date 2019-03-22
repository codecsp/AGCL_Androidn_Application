package com.example.agcl;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Forgot_Password extends AppCompatActivity {

    private EditText passswordEmail;
    private Button resetPassword;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot__password);

        passswordEmail=(EditText)findViewById(R.id.et_password_email);
        resetPassword=(Button)findViewById(R.id.btn_password_reset);
        firebaseAuth=FirebaseAuth.getInstance();

        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String euseremail = passswordEmail.getText().toString().trim();

                if(euseremail.equals("")){

                    Toast.makeText(Forgot_Password.this,"please enter user registered email ID",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    firebaseAuth.sendPasswordResetEmail(euseremail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){

                                Toast.makeText(Forgot_Password.this,"reset email sent ..! ",Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(Forgot_Password.this,MainActivity.class));
                            }else
                            {
                                Toast.makeText(Forgot_Password.this,"Error in sending password reset email...! ",Toast.LENGTH_SHORT).show();

                            }
                        }
                    });

                }
            }
        });


    }
}
