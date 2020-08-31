package com.example.chatapp_;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.Selection;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class AuthActivity extends AppCompatActivity {

    private EditText editPhone;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;
    private boolean isCodeSent;
    private String verificationId;
    private String enteredCode;
    PhoneAuthCredential credential;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        editPhone = findViewById(R.id.editPhone);
        callbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                String code = phoneAuthCredential.getSmsCode();
                credential = phoneAuthCredential;
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {

            }

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                isCodeSent = true;
                verificationId = s;
                if (isCodeSent == true) {
                    showCodeView(s);
                    isCodeSent = false;
                }
            }
        };

        editPhone.setText("+996");
        Selection.setSelection(editPhone.getText(), editPhone.getText().length());
        editPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().startsWith("+996")) {
                    editPhone.setText("+996");
                    Selection.setSelection(editPhone.getText(), editPhone.getText().length());
                }
            }
        });
   }

    public void onClickStart(View v) {
        String phone = editPhone.getText().toString().trim();
        if(phone.isEmpty()){
            editPhone.setError("Phone number is required");
        }
        PhoneAuthProvider.getInstance().verifyPhoneNumber(phone,
                60,
                TimeUnit.SECONDS,
                this,
                callbacks);
    }

    private void signIn(PhoneAuthCredential phoneAuthCredential) {
        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            startActivity(new Intent(AuthActivity.this, ProfileActivity.class));
                            finish();
                        } else {
                            Toast.makeText(AuthActivity.this, "Authorization error" +
                                    task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void showCodeView(final String code) {
        AlertDialog.Builder builder = new AlertDialog.Builder(AuthActivity.this);
        builder.setTitle("Verification code");
        builder.setMessage("Enter code sent to the number you've entered");
        final EditText editText = new EditText(AuthActivity.this);
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        builder.setView(editText);

        builder.setPositiveButton("Enter", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                enteredCode = editText.getText().toString();
                Log.e("enteredCode",enteredCode);
//                if (enteredCode.compareTo("") == 0) {
                if (enteredCode.isEmpty()) {
                    Log.e("isEmpty check", "check");
                    if (code.equals(enteredCode)) {
                        //signIn(credential);
                    } else {
                        Toast.makeText(getApplicationContext(),
                                "Wrong Password!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        builder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        builder.show();
    }
    private void verifyCode(String code){
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signIn(credential);
    }
}