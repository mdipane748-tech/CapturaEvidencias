package com.uth.capturaevidencias.models;

public class Registro {

    private int id;
    private String imagenBase64;
    private String descripcionTexto;
    private String fechaRegistro;

    public Registro() {
    }

    public Registro(int id,
                    String imagenBase64,
                    String descripcionTexto,
                    String fechaRegistro) {

        this.id = id;
        this.imagenBase64 = imagenBase64;
        this.descripcionTexto = descripcionTexto;
        this.fechaRegistro = fechaRegistro;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImagenBase64() {
        return imagenBase64;
    }

    public void setImagenBase64(String imagenBase64) {
        this.imagenBase64 = imagenBase64;
    }

    public String getDescripcionTexto() {
        return descripcionTexto;
    }

    public void setDescripcionTexto(String descripcionTexto) {
        this.descripcionTexto = descripcionTexto;
    }

    public String getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
}