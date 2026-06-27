package com.uth.capturaevidencias.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.uth.capturaevidencias.FotoActivity;
import com.uth.capturaevidencias.R;
import com.uth.capturaevidencias.models.Registro;

import java.util.ArrayList;

public class RegistroAdapter
        extends RecyclerView.Adapter<RegistroAdapter.ViewHolder> {

    ArrayList<Registro> lista;

    public RegistroAdapter(ArrayList<Registro> lista) {

        this.lista = lista;

    }

    public static class ViewHolder
            extends RecyclerView.ViewHolder {

        ImageView imgRegistro;

        TextView txtDescripcionRegistro;

        TextView txtFechaRegistro;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);

            imgRegistro =
                    itemView.findViewById(
                            R.id.imgRegistro
                    );

            txtDescripcionRegistro =
                    itemView.findViewById(
                            R.id.txtDescripcionRegistro
                    );

            txtFechaRegistro =
                    itemView.findViewById(
                            R.id.txtFechaRegistro
                    );

        }

    }

    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(
                        R.layout.item_registro,
                        parent,
                        false
                );

        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Registro registro =
                lista.get(position);

        holder.txtDescripcionRegistro.setText(
                registro.getDescripcionTexto()
        );

        holder.txtFechaRegistro.setText(
                registro.getFechaRegistro()
        );

        byte[] bytes =
                Base64.decode(
                        registro.getImagenBase64(),
                        Base64.DEFAULT
                );

        Bitmap bitmap =
                BitmapFactory.decodeByteArray(
                        bytes,
                        0,
                        bytes.length
                );

        holder.imgRegistro.setImageBitmap(bitmap);

        holder.imgRegistro.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            v.getContext(),
                            FotoActivity.class
                    );

            intent.putExtra(
                    "imagen",
                    registro.getImagenBase64()
            );

            v.getContext().startActivity(intent);

        });

    }

    @Override
    public int getItemCount() {

        return lista.size();

    }

}
