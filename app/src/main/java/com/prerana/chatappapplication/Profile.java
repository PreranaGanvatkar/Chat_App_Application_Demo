package com.prerana.chatappapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;

import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;

public class Profile extends AppCompatActivity {


    private Button btnLogout;
    private ImageView imgProfile;
    private Uri imagePath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        btnLogout=findViewById(R.id.btnLogout);
        imgProfile=findViewById(R.id.profile_img);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(Profile.this,MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP));
                finish();
            }
        });

        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoIntent = new Intent(Intent.ACTION_PICK);
                photoIntent .setType("image/*");
                startActivityForResult(photoIntent,1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1 && requestCode == RESULT_OK && data!=null){
            imagePath=data.getData();
            getImageInImageView();
        }
    }

    private void getImageInImageView() {

        Bitmap bitmap = null;

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imagePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
            imgProfile.setImageBitmap(bitmap);
        
    }
}