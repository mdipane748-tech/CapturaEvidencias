package com.uth.capturaevidencias;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.uth.capturaevidencias.adapter.RegistroAdapter;
import com.uth.capturaevidencias.DAO.RegistroDAO;
import com.uth.capturaevidencias.models.Registro;

import java.util.ArrayList;

public class RegistrosActivity extends AppCompatActivity {

    RecyclerView recyclerRegistros;

    RegistroDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registros);

        recyclerRegistros =
                findViewById(
                        R.id.recyclerRegistros
                );

        dao =
                new RegistroDAO(this);

        ArrayList<Registro> lista =
                dao.listarRegistros();

        RegistroAdapter adapter =
                new RegistroAdapter(lista);

        recyclerRegistros.setLayoutManager(
                new LinearLayoutManager(this)
        );

        recyclerRegistros.setAdapter(adapter);

    }
}