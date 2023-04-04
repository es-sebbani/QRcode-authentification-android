package com.example.reefectoire;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.reefectoire.InformationActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class LoginActivity extends AppCompatActivity {

    private Button scanQRCodeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        scanQRCodeButton = findViewById(R.id.scanButton);

        scanQRCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open the camera and scan the QR code
                IntentIntegrator integrator = new IntentIntegrator(LoginActivity.this);
                integrator.setOrientationLocked(false);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
                integrator.setCameraId(0);
                integrator.initiateScan();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Scan cancelled", Toast.LENGTH_SHORT).show();
            } else {
                // Decode the information in the QR code
                String qrCodeContent = result.getContents();
                String[] userInfoArray = qrCodeContent.split(" ");
                String userToken = userInfoArray[0];
                Toast.makeText(LoginActivity.this, userToken, Toast.LENGTH_SHORT).show();

                DatabaseReference userRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://refectoire-ac82a-default-rtdb.firebaseio.com/").child(userToken);

                userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                        // Get the user information from the database
                        String nom = dataSnapshot.child("nom").getValue(String.class);
                        String prenom = dataSnapshot.child("prenom").getValue(String.class);
                        String grade = dataSnapshot.child("grade").getValue(String.class);
                        String fonction = dataSnapshot.child("fonction").getValue(String.class);
                        Log.d("LoginActivity", "Data retrieved from database: " + dataSnapshot.toString());
                        // Verify the user's information
                        if (nom.equals(userInfoArray[1]) && prenom.equals(userInfoArray[2]) && grade.equals(userInfoArray[3]) && fonction.equals(userInfoArray[4])) {

                            Intent intent = new Intent(LoginActivity.this, InformationActivity.class);
                            intent.putExtra("userToken", userToken);
                            intent.putExtra("nom", nom);
                            intent.putExtra("prenom", prenom);
                            intent.putExtra("grade", grade);
                            intent.putExtra("fonction", fonction);
                            startActivity(intent);
                        } else {
                            // The user information is incorrect
                            Toast.makeText(LoginActivity.this, "les informations sont incorrects", Toast.LENGTH_SHORT).show();
                        }}
                        else {
                            Toast.makeText(LoginActivity.this,"les informations sont incorrects", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Handle the error
                    }
                });


            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

}
