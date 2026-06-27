package com.uth.capturaevidencias.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME =
            "RegistroDB";

    private static final int DATABASE_VERSION =
            1;

    public DatabaseHelper(@Nullable Context context) {
        super(
                context,
                DATABASE_NAME,
                null,
                DATABASE_VERSION
        );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql =
                "CREATE TABLE registros (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "imagenBase64 TEXT," +
                        "descripcionTexto TEXT," +
                        "fechaRegistro TEXT" +
                        ")";

        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db,
                          int oldVersion,
                          int newVersion) {

        db.execSQL(
                "DROP TABLE IF EXISTS registros"
        );

        onCreate(db);

    }

}