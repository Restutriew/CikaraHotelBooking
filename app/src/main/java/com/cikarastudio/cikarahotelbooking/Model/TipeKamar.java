package com.cikarastudio.cikarahotelbooking.Model;

public class TipeKamar {

    private String nama_tipe;
    private String harga;
    private String kapasitas;
    private String deskripsi;
    private String link_gambar;



    public TipeKamar(String nama_tipe, String harga, String kapasitas, String deskripsi, String link_gambar) {
        this.nama_tipe = nama_tipe;
        this.harga = harga;
        this.kapasitas = kapasitas;
        this.deskripsi = deskripsi;
        this.link_gambar = link_gambar;
    }

    public String getNama_tipe() {
        return nama_tipe;
    }

    public void setNama_tipe(String nama_tipe) {
        this.nama_tipe = nama_tipe;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getKapasitas() {
        return kapasitas;
    }

    public void setKapasitas(String kapasitas) {
        this.kapasitas = kapasitas;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getLink_gambar() {
        return link_gambar;
    }

    public void setLink_gambar(String link_gambar) {
        this.link_gambar = link_gambar;
    }
}
