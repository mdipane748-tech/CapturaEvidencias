package com.uth.capturaevidencias;

import android.os.Bundle;
import java.text.SimpleDateFormat;
import java.util.Date;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import android.content.pm.PackageManager;
import android.widget.Toast;
import android.util.Base64;
import android.speech.RecognizerIntent;
import com.google.android.material.textfield.TextInputEditText;
import java.util.ArrayList;
import java.util.Locale;
import java.io.ByteArrayOutputStream;
import androidx.core.content.ContextCompat;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import com.uth.capturaevidencias.DAO.RegistroDAO;
import com.uth.capturaevidencias.models.Registro;

public class MainActivity extends AppCompatActivity {

    ImageView imgFoto;

    TextInputEditText txtDescripcion;

    Button btnFoto, btnVoz, btnGuardar, btnConsultar;

    Bitmap bitmapFoto;

    String imagenBase64;

    ActivityResultLauncher<Intent> camaraLauncher =
            registerForActivityResult(

                    new ActivityResultContracts.StartActivityForResult(),

                    result -> {

                        if(result.getResultCode() == RESULT_OK){

                            Intent data =
                                    result.getData();

                            if(data != null && data.getExtras() != null){

                                bitmapFoto =
                                        (Bitmap) data.getExtras().get("data");

                                imgFoto.setImageBitmap(bitmapFoto);

                                imagenBase64 =
                                        convertirBase64(
                                                bitmapFoto
                                        );

                            }

                        }

                    }

            );

    ActivityResultLauncher<String> permisoCamaraLauncher =
            registerForActivityResult(

                    new ActivityResultContracts.RequestPermission(),

                    isGranted -> {

                        if(isGranted){

                            abrirCamara();

                        }else{

                            Toast.makeText(
                                    this,
                                    "Debes permitir el acceso a la cámara",
                                    Toast.LENGTH_LONG
                            ).show();

                        }

                    }

            );

    ActivityResultLauncher<Intent> vozLauncher =
            registerForActivityResult(

                    new ActivityResultContracts.StartActivityForResult(),

                    result -> {

                        if(result.getResultCode() == RESULT_OK){

                            Intent data =
                                    result.getData();

                            if(data != null){

                                ArrayList<String> resultados =
                                        data.getStringArrayListExtra(
                                                RecognizerIntent.EXTRA_RESULTS
                                        );

                                if(resultados != null &&
                                        !resultados.isEmpty()){

                                    txtDescripcion.setText(
                                            resultados.get(0)
                                    );

                                }

                            }

                        }

                    }

            );

    private void abrirCamara(){

        Intent intent =
                new Intent(
                        MediaStore.ACTION_IMAGE_CAPTURE
                );

        camaraLauncher.launch(intent);

    }

    private void limpiarFormulario(){

        imgFoto.setImageResource(
                android.R.drawable.ic_menu_camera
        );

        txtDescripcion.setText("");

        bitmapFoto = null;

        imagenBase64 = null;

    }

    private String convertirBase64(Bitmap bitmap){

        ByteArrayOutputStream stream =
                new ByteArrayOutputStream();

        bitmap.compress(
                Bitmap.CompressFormat.JPEG,
                80,
                stream
        );

        byte[] imagenBytes =
                stream.toByteArray();

        return Base64.encodeToString(
                imagenBytes,
                Base64.DEFAULT
        );

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        imgFoto =
                findViewById(
                        R.id.imgFoto
                );

        btnFoto =
                findViewById(
                        R.id.btnFoto
                );

        btnVoz =
                findViewById(
                        R.id.btnVoz
                );

        txtDescripcion =
                findViewById(
                        R.id.txtDescripcionRegistro
                );

        btnGuardar =
                findViewById(
                        R.id.btnGuardar
                );

        btnConsultar =
                findViewById(
                        R.id.btnConsultar
                );

        btnFoto.setOnClickListener(v -> {

            if(ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED){

                abrirCamara();

            }else{

                permisoCamaraLauncher.launch(
                        Manifest.permission.CAMERA
                );

            }

        });

        btnVoz.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            RecognizerIntent.ACTION_RECOGNIZE_SPEECH
                    );

            intent.putExtra(
                    RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            );

            intent.putExtra(
                    RecognizerIntent.EXTRA_LANGUAGE,
                    Locale.getDefault()
            );

            intent.putExtra(
                    RecognizerIntent.EXTRA_PROMPT,
                    "Habla para registrar la descripción"
            );

            vozLauncher.launch(intent);

        });

        btnGuardar.setOnClickListener(v -> {

            if(imagenBase64 == null){

                Toast.makeText(
                        this,
                        "Primero capture una fotografía",
                        Toast.LENGTH_SHORT
                ).show();

                return;

            }

            if(txtDescripcion.getText().toString().trim().isEmpty()){

                Toast.makeText(
                        this,
                        "Ingrese una descripción",
                        Toast.LENGTH_SHORT
                ).show();

                return;

            }

            Registro registro =
                    new Registro();

            registro.setImagenBase64(
                    imagenBase64
            );

            registro.setDescripcionTexto(
                    txtDescripcion
                            .getText()
                            .toString()
            );

            String fecha =
                    new SimpleDateFormat(
                            "dd/MM/yyyy HH:mm"
                    ).format(
                            new Date()
                    );

            registro.setFechaRegistro(
                    fecha
            );

            RegistroDAO dao =
                    new RegistroDAO(this);

            long resultado =
                    dao.insertar(registro);

            if(resultado > 0){

                Toast.makeText(
                        this,
                        "Registro guardado correctamente",
                        Toast.LENGTH_SHORT
                ).show();

                limpiarFormulario();

            }else{

                Toast.makeText(
                        this,
                        "Error al guardar",
                        Toast.LENGTH_SHORT
                ).show();

            }

        });

        btnConsultar.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            MainActivity.this,
                            RegistrosActivity.class
                    );

            startActivity(intent);

        });
    }
}