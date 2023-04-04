package com.example.reefectoire;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

public class InformationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        EditText nomEditText = findViewById(R.id.nom);
        EditText prenomEditText = findViewById(R.id.prenom);
        EditText gradeEditText = findViewById(R.id.grade);
        EditText fonctionEditText = findViewById(R.id.fonction);

        Intent intent = getIntent();
        String nom = intent.getStringExtra("nom");
        String prenom = intent.getStringExtra("prenom");
        String grade = intent.getStringExtra("grade");
        String fonction = intent.getStringExtra("fonction");
        nomEditText.setText(nom);
        prenomEditText.setText(prenom);
        gradeEditText.setText(grade);
        fonctionEditText.setText(fonction);
    }
}
