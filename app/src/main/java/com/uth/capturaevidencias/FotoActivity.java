package com.uth.capturaevidencias;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class FotoActivity extends AppCompatActivity {

    ImageView imgPantallaCompleta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_foto);

        imgPantallaCompleta =
                findViewById(R.id.imgPantallaCompleta);

        String imagenBase64 =
                getIntent().getStringExtra("imagen");

        if(imagenBase64 != null){

            byte[] bytes =
                    Base64.decode(
                            imagenBase64,
                            Base64.DEFAULT
                    );

            Bitmap bitmap =
                    BitmapFactory.decodeByteArray(
                            bytes,
                            0,
                            bytes.length
                    );

            imgPantallaCompleta.setImageBitmap(bitmap);
            imgPantallaCompleta.setOnClickListener(v -> finish());

        }

    }

}