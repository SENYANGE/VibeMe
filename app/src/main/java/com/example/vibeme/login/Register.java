package com.example.vibeme.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vibeme.R;
import com.example.vibeme.ui.main.MainActivity;
import com.example.vibeme.login.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {

    private EditText txt_email,txt_pass;
    private Button btn_login;
    private TextView txt_register;
    private FirebaseAuth auth;
    private ProgressDialog progressDialog;
    private DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        txt_email=(EditText)findViewById(R.id.email_register);
        txt_pass=(EditText)findViewById(R.id.pass_register);
        btn_login=(Button)findViewById(R.id.creatregister);
        txt_register=(TextView)findViewById(R.id.haveaaccount);
        auth= FirebaseAuth.getInstance();
        reference= FirebaseDatabase.getInstance().getReference();
        progressDialog=new ProgressDialog(this);
        txt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendusertologin();
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeaccount();
            }
        });

    }
    private void sendusertologin() {
        Intent intent=new Intent(Register.this, LoginActivity.class);
        startActivity(intent);
    }

    private void sendusertoMain(){
        Intent intent=new Intent(Register.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();

    }

    private void makeaccount() {
        final String email=txt_email.getText().toString();
        String pass=txt_pass.getText().toString();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "enter your email", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(pass)){
            Toast.makeText(this, "enter your password", Toast.LENGTH_SHORT).show();
        }
        else {
            progressDialog.setTitle("Creating new Account" );
            progressDialog.setMessage("wait...while creating new account");
            progressDialog.setCanceledOnTouchOutside(true);
            progressDialog.show();

            auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful())
                    {
                        String Tokendevice= FirebaseDatabase.getInstance().getReference().push().getKey();

                        String currentid=auth.getCurrentUser().getUid();
                        reference.child("vibeme").child("Users").child(currentid).setValue(email);

                        reference.child("vibeme").child("Users").child(currentid).child("Token_device").setValue(Tokendevice);
                        sendusertoMain();
                        Toast.makeText(Register.this, "Account creating Successfully!!", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                    else
                    {
                        String message=task.getException().toString();
                        Toast.makeText(Register.this, "Error .. in "+message, Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }
            });
        }
    }
}
