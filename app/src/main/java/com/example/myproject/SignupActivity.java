package com.example.myproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class SignupActivity extends AppCompatActivity {
    EditText t1,t2;
    Button button;
    ProgressBar bar;
    TextView tvLoginHere;
    FirebaseAuth mAuth;
    EditText mobileNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        t1=(EditText) findViewById(R.id.editTextTextPersonName);
        t2=(EditText) findViewById(R.id.editTextTextPassword);
        bar=(ProgressBar) findViewById(R.id.progressBar);
        button=(Button) findViewById(R.id.button);
        tvLoginHere= findViewById(R.id.textView7);
        mobileNumber=findViewById(R.id.editTextTextPersonName4);

        mAuth = FirebaseAuth.getInstance();

        button.setOnClickListener(view ->{
            createUser();
            //getVerification();

        });

        tvLoginHere.setOnClickListener(view ->{
            startActivity(new Intent(SignupActivity.this, LoginActivity.class));
        });
    }

    private void getVerification() {

        if (!mobileNumber.getText().toString().trim().isEmpty()) {
            if ((mobileNumber.getText().toString().trim()).length() == 10) {

                PhoneAuthProvider.getInstance().verifyPhoneNumber
                        ("+92"+ mobileNumber.getText().toString(), 60, TimeUnit.SECONDS,SignupActivity.this,
                                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                    @Override
                                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                    }

                                    @Override
                                    public void onVerificationFailed(@NonNull FirebaseException e) {
                                        Toast.makeText(SignupActivity.this, e.getMessage(), Toast.LENGTH_SHORT);


                                    }

                                    @Override
                                    public void onCodeSent(@NonNull String backendotp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                        super.onCodeSent(backendotp, forceResendingToken);
                                        Intent intent = new Intent(SignupActivity.this, OtpVerification.class);
                                        intent.putExtra("mobile", mobileNumber.getText().toString());
                                        intent.putExtra("backendotp",backendotp);
                                        startActivity(intent);
                                    }
                                }
                        );

            } else {
                Toast.makeText(SignupActivity.this, "Please enter correct number", Toast.LENGTH_SHORT);
            }
        }else{
            Toast.makeText(SignupActivity.this,"Please enter mobile number", Toast.LENGTH_SHORT);
        }

    }

    private void createUser(){
        String email = t1.getText().toString();
        String password = t2.getText().toString();

        if (TextUtils.isEmpty(email)){
            t1.setError("Email cannot be empty");
            t1.requestFocus();
        }else{
            if (TextUtils.isEmpty(password)){
            t2.setError("Password cannot be empty");
            t2.requestFocus();
            }else{
             mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(SignupActivity.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                        //startActivity(new Intent(SignupActivity.this, LoginActivity.class));



                        if (!mobileNumber.getText().toString().trim().isEmpty()) {
                            if ((mobileNumber.getText().toString().trim()).length() == 10) {

                                PhoneAuthProvider.getInstance().verifyPhoneNumber
                                        ("+92"+ mobileNumber.getText().toString(), 60, TimeUnit.SECONDS,SignupActivity.this,
                                                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                                    @Override
                                                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                                    }

                                                    @Override
                                                    public void onVerificationFailed(@NonNull FirebaseException e) {
                                                        Toast.makeText(SignupActivity.this, e.getMessage(), Toast.LENGTH_SHORT);


                                                    }

                                                    @Override
                                                    public void onCodeSent(@NonNull String backendotp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                                        super.onCodeSent(backendotp, forceResendingToken);
                                                        Intent intent = new Intent(SignupActivity.this, OtpVerification.class);
                                                        intent.putExtra("mobile", mobileNumber.getText().toString());
                                                        intent.putExtra("backendotp",backendotp);
                                                        startActivity(intent);
                                                    }
                                                }
                                        );

                            } else {
                                Toast.makeText(SignupActivity.this, "Please enter correct number", Toast.LENGTH_SHORT);
                            }
                        }else{
                            Toast.makeText(SignupActivity.this,"Please enter mobile number", Toast.LENGTH_SHORT);
                        }


                    }else{
                        Toast.makeText(SignupActivity.this, "Registration Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }}

}