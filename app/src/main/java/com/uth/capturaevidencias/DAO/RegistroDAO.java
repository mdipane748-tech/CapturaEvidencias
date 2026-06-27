package com.uth.capturaevidencias.DAO;

import android.content.Context;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import com.uth.capturaevidencias.models.Registro;
import com.uth.capturaevidencias.database.DatabaseHelper;
import android.database.Cursor;
import java.util.ArrayList;

public class RegistroDAO {

    DatabaseHelper dbHelper;

    public RegistroDAO(Context context){

        dbHelper = new DatabaseHelper(context);

    }

    public long insertar(Registro registro){

        SQLiteDatabase db =
                dbHelper.getWritableDatabase();

        ContentValues values =
                new ContentValues();

        values.put(
                "imagenBase64",
                registro.getImagenBase64()
        );

        values.put(
                "descripcionTexto",
                registro.getDescripcionTexto()
        );

        values.put(
                "fechaRegistro",
                registro.getFechaRegistro()
        );

        long resultado =
                db.insert(
                        "registros",
                        null,
                        values
                );

        db.close();

        return resultado;

    }

    public ArrayList<Registro> listarRegistros(){

        ArrayList<Registro> lista =
                new ArrayList<>();

        SQLiteDatabase db =
                dbHelper.getReadableDatabase();

        Cursor cursor =
                db.rawQuery(
                        "SELECT * FROM registros ORDER BY id DESC",
                        null
                );

        if(cursor.moveToFirst()){

            do{

                Registro registro =
                        new Registro();

                registro.setId(
                        cursor.getInt(0)
                );

                registro.setImagenBase64(
                        cursor.getString(1)
                );

                registro.setDescripcionTexto(
                        cursor.getString(2)
                );

                registro.setFechaRegistro(
                        cursor.getString(3)
                );

                lista.add(registro);

            }while(cursor.moveToNext());

        }

        cursor.close();
        db.close();

        return lista;

    }

}