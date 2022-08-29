package com.courses.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.courses.myapplication.databinding.ActivityMainBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements ValueEventListener {

    private static final String PATH_START = "start";
    private static final String PATH_MESSAGE = "message";
    private ActivityMainBinding binding;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference(PATH_START).child(PATH_MESSAGE);
        databaseReference.addValueEventListener(this);

        binding.btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference.setValue(binding.etMessage.getText().toString().trim());
                binding.etMessage.setText("");
            }
        });
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        binding.tvMessage.setText(snapshot.getValue(String.class));
    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {
        Toast.makeText(this, "Error at request Firebase", Toast.LENGTH_SHORT).show();
    }
}