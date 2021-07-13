package com.example.weathcare.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.weathcare.R;
import com.example.weathcare.signup.SignupActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etEmail, etPassword;
    private String email, password;
    private Button btnLogin, btnSignUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.weathcare.R.layout.activity_login);
        Init();
    }

    private void Init()
    {
        etEmail = findViewById(R.id.editTextEmail);
        etPassword = findViewById(R.id.editTextPassword);

        btnLogin = findViewById(R.id.loginBtn);
        btnSignUp = findViewById(R.id.signupBtnLogin);

        btnLogin.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        email = etEmail.getText().toString().trim();
        password = etPassword.getText().toString().trim();

        switch (view.getId())
        {
            case R.id.loginBtn:
                if(email.equals(""))
                {
                    etEmail.setError("Email is empty");
                }else if(password.equals(""))
                {
                    etPassword.setError("Password is empty");
                }else{
                    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                    firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {

                            }else{
                                Toast.makeText(LoginActivity.this, "Login Failed : " + task.getException(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            break;
            case R.id.signupBtnLogin:
                startActivity(new Intent(this, SignupActivity.class));
            break;
        }
    }
}