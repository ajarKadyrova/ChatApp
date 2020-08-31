package com.example.chatapp_;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {

    private EditText profileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profileName = findViewById(R.id.profile_name);
    }

    public void onClickSave(View view) {
        String name = profileName.getText().toString().trim();
        if(TextUtils.isEmpty(name)){
            profileName.setError("Enter name");
            return;
        }
        Map<String, Object> map = new HashMap<>();
        String userId = FirebaseAuth.getInstance().getUid();
        map.put("name", name);
        FirebaseFirestore.getInstance().collection("users")
                .document(userId)
                .set(map);
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if(task.isSuccessful()){
//                            startActivity(new Intent(ProfileActivity.this, MainActivity.class));
//                            finish();
//                        }
//                    }
//                });
    }
}