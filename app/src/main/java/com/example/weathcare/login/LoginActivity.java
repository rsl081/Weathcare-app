package com.example.weathcare.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.weathcare.InternetActivity;
import com.example.weathcare.MainActivity;
import com.example.weathcare.R;
import com.example.weathcare.common.Util;
import com.example.weathcare.password.ResetPasswordActivity;
import com.example.weathcare.signup.SignupActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private TextInputEditText etEmail, etPassword;
    private String email, password;
    private Button btnLogin, btnSignUp;

    private View progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.weathcare.R.layout.activity_login);
        Init();
    }

    private void Init()
    {
        progressBar = findViewById(R.id.login_progressbar);
        etEmail = findViewById(R.id.etEmailLogin);
        etPassword = findViewById(R.id.etPasswordLogin);

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
                    if(Util.connectionAvailable(this)) {

                        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
//                            progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    progressBar.setVisibility(View.VISIBLE);
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                    finish();
                                } else {
                                    Toast.makeText(LoginActivity.this, "Login Failed : " + task.getException(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }else{
                        startActivity(new Intent(LoginActivity.this, InternetActivity.class));
                    }
                }
            break;
            case R.id.signupBtnLogin:
                if(Util.connectionAvailable(this)) {
                    startActivity(new Intent(this, SignupActivity.class));
                }else{
                    startActivity(new Intent(LoginActivity.this, InternetActivity.class));
                }
            break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        if(firebaseUser != null)
        {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
    }

    public void TvResetPasswordClick(View view)
    {
        startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));
    }
}