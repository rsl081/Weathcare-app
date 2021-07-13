package com.example.weathcare.signup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.weathcare.R;
import com.example.weathcare.login.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView imageViewSignup;
    private EditText etName, etEmail, etPassword, etConfirmPassword;
    private String name, email, password, confirmpassord;
    private Button btnSignup;

    private StorageReference fileStorage;
    private Uri localFileUri, serverFileUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Init();
    }
    private void Init()
    {
        imageViewSignup = findViewById(R.id.imageViewSignup);
        imageViewSignup.setOnClickListener(this);
        etName = findViewById(R.id.etNameSignup);
        etEmail = findViewById(R.id.etEmailSignup);
        etPassword = findViewById(R.id.etPasswordSignup);
        etConfirmPassword = findViewById(R.id.etConfimPassSignup);

        btnSignup = findViewById(R.id.signupBtn);
        btnSignup.setOnClickListener(this);

        fileStorage = FirebaseStorage.getInstance().getReference();
    }


    @Override
    public void onClick(View view) {
        name = etName.getText().toString().trim();
        email = etEmail.getText().toString().trim();
        password = etPassword.getText().toString().trim();
        confirmpassord = etConfirmPassword.getText().toString().trim();

        switch (view.getId())
        {
            case R.id.imageViewSignup:
                Toast.makeText(this, "Hello Picurre!!", Toast.LENGTH_SHORT).show();
            break;
            case R.id.signupBtn:
                if(name.equals(""))
                {
                    etName.setError("Name is empty");
                }else if(email.equals(""))
                {
                    etEmail.setError("Email is empty");
                }else if(password.equals(""))
                {
                    etPassword.setError("Password is empty");
                }else if(confirmpassord.equals(""))
                {
                    etConfirmPassword.setError("Confirm password is empty");
                }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
                {
                    etEmail.setError("Enter correct email");
                }else if(!password.equals(confirmpassord))
                {
                    etConfirmPassword.setError("Password mismatch");
                }else{
                    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                    firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(SignupActivity.this, "User created successfully", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                            }else{
                                Toast.makeText(SignupActivity.this, "Signup Failed : " + task.getException(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            break;
        }
    }
}