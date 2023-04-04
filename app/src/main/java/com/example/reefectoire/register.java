package com.example.reefectoire;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.widget.ImageView;

import com.google.zxing.MultiFormatWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import com.google.zxing.common.BitMatrix;
import android.graphics.Bitmap;
import android.graphics.Color;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.firebase.FirebaseApp;
public class register extends AppCompatActivity {

    private FirebaseDatabase database;
    private EditText signup_nom, signup_prenom,signup_grade,signup_fonction;

    private TextView loginRedirectText;
    private Button signup_button;
    private ImageView qrCodeImageView;
    private String userToken;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseApp.initializeApp(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        signup_nom = findViewById(R.id.signup_nom);
        signup_prenom = findViewById(R.id.signup_prenom);
        signup_grade = findViewById(R.id.signup_grade);
        signup_fonction = findViewById(R.id.signup_fonction);
        signup_button = findViewById(R.id.signup_button);
        loginRedirectText = findViewById(R.id.loginRedirectText);
        qrCodeImageView = findViewById(R.id.qrCodeImageView);
        // Initialize Firebase database reference
        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://refectoire-ac82a-default-rtdb.firebaseio.com/");

        signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String signupNom = signup_nom.getText().toString().trim();
                String signupPrenom = signup_prenom.getText().toString().trim();
                String signupGrade = signup_grade.getText().toString().trim();
                String signupFonction = signup_fonction.getText().toString().trim();



                if (signupNom.isEmpty()){
                    signup_nom.setError("veillez saisir le nom");
                    signup_nom.requestFocus();
                    return;
                }
                if (signupPrenom.isEmpty()){
                    signup_prenom.setError("veillez saisir le prénom");
                }
                if (signupGrade.isEmpty()){
                    signup_grade.setError("veillez saisir le grade");
                }
                if (signupFonction.isEmpty()){
                    signup_fonction.setError("veillez saisir la fonction");
                } else{
                    // Generate a unique identifier for the user
                    userToken = UUID.randomUUID().toString();
                    String userId = userToken;

                    databaseReference.child(userId).child("nom").setValue(signupNom);
                    databaseReference.child(userId).child("prenom").setValue(signupPrenom);
                    databaseReference.child(userId).child("grade").setValue(signupGrade);
                    databaseReference.child(userId).child("fonction").setValue(signupFonction);
                    // Generate a QR code for the user that includes their unique identifier and their information
                    MultiFormatWriter writer = new MultiFormatWriter();
                    try {
                        BitMatrix bitMatrix = writer.encode(userToken + " " +
                                        signupNom + " " +
                                        signupPrenom+ " " +
                                        signupGrade + " " +
                                        signupFonction,
                                BarcodeFormat.QR_CODE, 512, 512);
                        Bitmap bitmap = Bitmap.createBitmap(512, 512, Bitmap.Config.RGB_565);
                        for (int x = 0; x < 512; x++) {
                            for (int y = 0; y < 512; y++) {
                                bitmap.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                            }
                        }
                        qrCodeImageView.setImageBitmap(bitmap);
                    } catch (WriterException e) {
                        e.printStackTrace();
                    }
                    qrCodeImageView.setVisibility(View.VISIBLE);

                }
            }
        });
        loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(register.this, LoginActivity.class));
            }
        });

    }


}